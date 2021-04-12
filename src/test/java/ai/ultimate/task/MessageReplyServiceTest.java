package ai.ultimate.task;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import ai.ultimate.task.repositories.IntentRepository;
import ai.ultimate.task.repositories.model.IntentDocument;
import ai.ultimate.task.repositories.model.Reply;
import ai.ultimate.task.services.IntentResponse;
import ai.ultimate.task.services.MessageIntentSuggestionService;
import ai.ultimate.task.services.MessageReplyService;

@RunWith(MockitoJUnitRunner.class)
public class MessageReplyServiceTest {

    @Mock
    private MessageIntentSuggestionService messageIntentSuggestionService;

    @Mock
    private IntentRepository intentRepository;

    private MessageReplyService messageReplyService;

    @Before
    public void init(){

        messageReplyService = new MessageReplyService(messageIntentSuggestionService, intentRepository);
    }

    @Test
    public void getReplyForMessageTest(){

        String botId = "anyBotId";
        String message = "anyMessage";
        final IntentDocument intentDocument = IntentDocument.builder().reply(Reply.builder().text("Hi, how can I help you?").build()).build();

        when(messageIntentSuggestionService.getIntentSuggestions(botId, message)).thenReturn(getSampleIntents());
        when(intentRepository.findByName("Greeting")).thenReturn(Optional.of(intentDocument));

        final Optional<String> replyForMessage = messageReplyService.getReplyForMessage(botId, message);

        assertTrue(replyForMessage.isPresent());
        assertTrue(replyForMessage.get().equals(intentDocument.getReply().getText()));
    }

    private List<IntentResponse.Intent> getSampleIntents(){

        final IntentResponse.Intent greeting = IntentResponse.Intent.builder().name("Greeting").confidence(BigDecimal.valueOf(1.058787)).build();
        final IntentResponse.Intent affirmative = IntentResponse.Intent.builder().name("Affirmative").confidence(BigDecimal.valueOf(0.002589)).build();

        return Arrays.asList(greeting, affirmative);
    }
}
