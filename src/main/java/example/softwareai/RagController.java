package example.softwareai;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rag")
public class RagController {

    private final RagService ragService;

    public RagController(RagService ragService) {
        this.ragService = ragService;
    }

    // 문서 저장 API
    @PostMapping("/document")
    public String addDocument(@RequestParam (required = false) String id, @RequestParam String content) {
        ragService.addDocument(id, content);
        return "문서 저장 완료: " + id;
    }

    @GetMapping("/document")
    public List<String> getDocuments(){
        return ragService.getAllDocument();
    }

    // 질문 API
    @GetMapping("/ask")
    public String ask(@RequestParam String question,
                      @RequestParam(defaultValue = "3") int topK) {
        return ragService.askQuestion(question, topK);
    }
}
