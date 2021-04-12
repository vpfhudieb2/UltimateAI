package ai.ultimate.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import ai.ultimate.task.api.chat.MessageReplyRequest;
import ai.ultimate.task.api.chat.MessageReplyResponse;
import ai.ultimate.task.repositories.IntentRepository;
import ai.ultimate.task.repositories.model.IntentDocument;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate testRestTemplate;

    private static final String BOT_ID = "5f74865056d7bb000fcd39ff";

    @Autowired
    private IntentRepository intentRepository;

    @Before
    public void init(){

        final RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder().rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder, null, null, null);
    }

    @Test
    public void testWithValidMessage(){

        final Optional<IntentDocument> greetingIntent = intentRepository.findByName("Greeting");
        final MessageReplyRequest messageReplyRequest = MessageReplyRequest.builder().botId(BOT_ID).message("hi").build();
        final ResponseEntity<MessageReplyResponse> responseEntity = testRestTemplate.postForEntity("/chat/messageReply", messageReplyRequest, MessageReplyResponse.class);
        final MessageReplyResponse body = responseEntity.getBody();

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(body);
        assertNotNull(body.getMessageReply());
        assertTrue(greetingIntent.isPresent());
        assertEquals(greetingIntent.get().getReply().getText(), body.getMessageReply());
    }
}
