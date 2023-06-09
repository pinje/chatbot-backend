package sem3chatbot.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sem3chatbot.backend.business.FAQQuestionService;
import sem3chatbot.backend.domain.CreateQuestionRequest;
import sem3chatbot.backend.domain.FAQQuestion;
import sem3chatbot.backend.domain.GetQuestionsByTopicIdRequest;
import sem3chatbot.backend.domain.GetQuestionsByTopicIdResponse;

import javax.validation.Valid;


@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://pie3bot.azurewebsites.net"}, allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("/faq-questions")
@RequiredArgsConstructor
public class FAQQuestionController{
    private final FAQQuestionService questionService;

    @PostMapping
    public ResponseEntity<Void> create (@RequestBody @Valid CreateQuestionRequest request) {
        questionService.create(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/topic")
    public ResponseEntity<GetQuestionsByTopicIdResponse> getQuestionsByTopicId (
            @RequestParam @Valid long topicId) {
        GetQuestionsByTopicIdResponse res = questionService.getPrimaryQuestionsByTopicId(
                GetQuestionsByTopicIdRequest.builder().topicId(topicId).build());
        return ResponseEntity.ok(res);
    }

    @GetMapping("/id")
    public ResponseEntity<FAQQuestion> getQuestionByIdIs (
            @RequestParam @Valid long id) {
        var res = questionService.getById(id);
        System.out.println(res);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete (@PathVariable(value = "id") long id) {
        questionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

