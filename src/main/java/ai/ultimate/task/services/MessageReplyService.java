package ai.ultimate.task.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.ultimate.task.repositories.IntentRepository;
import ai.ultimate.task.repositories.model.IntentDocument;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageReplyService {

    private final MessageIntentSuggestionService messageIntentSuggestionService;
    private final IntentRepository intentRepository;

    public Optional<String> getReplyForMessage(String botId, String message){

        //Query intents for a given bot and message from ultimateai service
        final List<IntentResponse.Intent> intentSuggestions = messageIntentSuggestionService.getIntentSuggestions(botId, message);
        //Get from the list of received intents the most confident intent
        final Optional<IntentResponse.Intent> bestSuggestedIntent = getBestSuggestedIntent(intentSuggestions);
        //Query from the DB the reply of this intent and return it (if found)
        return bestSuggestedIntent.flatMap(intent -> getReplyMessageForIntent(intent));
    }

    private Optional<IntentResponse.Intent> getBestSuggestedIntent(List<IntentResponse.Intent> intentResponses){

        return intentResponses
                .stream()
                .sorted((o1, o2) -> o2.getConfidence().compareTo(o1.getConfidence()))
                .findFirst();
    }

    private Optional<String> getReplyMessageForIntent(IntentResponse.Intent intent){

        final Optional<IntentDocument> optionalIntentDocument = intentRepository.findByName(intent.getName());

        if(optionalIntentDocument.isPresent())
            return optionalIntentDocument.map(intentDocument -> intentDocument.getReply().getText());
        else
            return Optional.empty();
    }
}
