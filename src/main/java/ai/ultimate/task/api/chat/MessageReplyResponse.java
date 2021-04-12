package ai.ultimate.task.api.chat;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageReplyResponse {

    private String botId;

    private String originalMessage;

    private String messageReply;
}
