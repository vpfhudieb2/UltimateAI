package ai.ultimate.task.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.ultimate.task.repositories.IntentRepository;
import ai.ultimate.task.repositories.model.IntentDocument;

@Service
public class MongoCollectionInitializer {

    @Autowired
    private IntentRepository intentRepository;

    @Value("classpath:db.json")
    private Resource dbDocumentsFile;

    @EventListener(ApplicationReadyEvent.class)
    public void initializeMongoDB(){

        intentRepository.deleteAll();
        intentRepository.saveAll(readInitialDocuments());
    }

    private List<IntentDocument> readInitialDocuments(){

        ObjectMapper objectMapper = new ObjectMapper();
        try {

            final InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("db.json");
            return objectMapper.readValue(resourceAsStream, new TypeReference<List<IntentDocument>>() {});

        } catch (IOException e) {
            throw new IllegalStateException("Could not load resource file for DB initialization!", e);
        }
    }
}
