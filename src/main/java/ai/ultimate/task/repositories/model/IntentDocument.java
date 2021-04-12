package ai.ultimate.task.repositories.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "intents")
public class IntentDocument {

    private String id;

    private String name;

    private String description;

    private TrainingData trainingData;

    private Reply reply;

}
