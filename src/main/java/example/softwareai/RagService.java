package example.softwareai;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RagService {

    private final VectorStore vectorStore;
    private final ChatModel chatModel;
    private final VectorStoreRepository repository;

    public RagService(VectorStore vectorStore, ChatModel chatModel, VectorStoreRepository repository) {
        this.vectorStore = vectorStore;
        this.chatModel = chatModel;
        this.repository = repository;
    }

    // 문서 저장
    public void addDocument(String id, String content) {

        String docId = (id == null || id.isBlank()) ? UUID.randomUUID().toString() : UUID.nameUUIDFromBytes(id.getBytes()).toString();

        Document doc = Document.builder()
                .id(docId)
                .text(content)
                .build();
        vectorStore.add(List.of(doc));
    }

    // 질문 처리
    public String askQuestion(String question, int topK) {
        List<Document> docs = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(question)
                        .topK(topK)
                        .build()
        );

        StringBuilder context = new StringBuilder();
        for (Document doc : docs) {
            context.append(doc.getText()).append("\n");
        }

        return chatModel.call(context.toString() + "\n" + question);
    }

    public List<String> getAllDocument() {
        return repository.findAll().stream()
                .map(entity -> entity.getId() + " : " + entity.getContent())
                .collect(Collectors.toList());
    }
}

