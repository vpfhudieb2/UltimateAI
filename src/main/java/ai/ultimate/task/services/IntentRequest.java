package ai.ultimate.task.services;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IntentRequest {

    private String botId;

    private String message;
}
