package ai.ultimate.task.services;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntentResponse {

    private List<Intent> intents;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Intent {

        private String name;

        private BigDecimal confidence;

    }
}
