package ai.ultimate.task.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.ultimate.task.repositories.model.IntentDocument;

public interface IntentRepository extends MongoRepository<IntentDocument, String> {

    Optional<IntentDocument> findByName(String name);
}
