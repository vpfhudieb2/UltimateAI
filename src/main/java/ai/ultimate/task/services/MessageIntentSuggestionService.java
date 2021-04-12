package ai.ultimate.task.services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
/**
 * This service is responsible for connecting to the remote ultimate.ai service to get list of suggested intents for a given message.
 * I am using RestTemplate as http client.
 */
@Service
public class MessageIntentSuggestionService {

    @Value("${ultimateai.message_suggestions.api}")
    private String messageReplySuggestionsURL;

    @Value("${ultimateai.message_suggestions.security_token}")
    private String messageReplySuggestionsSecurityToken;

    public List<IntentResponse.Intent> getIntentSuggestions(String potId, String message){

        final IntentRequest intentRequest = IntentRequest.builder().botId(potId).message(message).build();

        final ResponseEntity<IntentResponse> responseEntity = new RestTemplateBuilder()
                .defaultHeader("authorization", messageReplySuggestionsSecurityToken)
                .build()
                .postForEntity(messageReplySuggestionsURL, intentRequest, IntentResponse.class);

        if(responseEntity.getStatusCodeValue() == 200 )
            return responseEntity.getBody().getIntents();
        else
            return Collections.emptyList();
    }
}
