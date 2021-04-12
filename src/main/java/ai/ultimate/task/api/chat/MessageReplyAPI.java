package ai.ultimate.task.api.chat;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ai.ultimate.task.services.MessageReplyService;

@RestController
public class MessageReplyAPI {

    @Autowired
    private MessageReplyService messageReplyService;

    @PostMapping(
            path = "/messageReply",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<MessageReplyResponse> getReply(@RequestBody MessageReplyRequest messageReplyRequest){

        final Optional<String> replyForMessageOptional = messageReplyService.getReplyForMessage(messageReplyRequest.getBotId(), messageReplyRequest.getMessage());

        if(replyForMessageOptional.isPresent())
            return ResponseEntity.ok(MessageReplyResponse
                    .builder()
                    .botId(messageReplyRequest.getBotId())
                    .originalMessage(messageReplyRequest.getMessage())
                    .messageReply(replyForMessageOptional.get())
                    .build());
        else
            return ResponseEntity.notFound().build();
    }
}
