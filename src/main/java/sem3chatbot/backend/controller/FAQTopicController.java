package sem3chatbot.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sem3chatbot.backend.business.FAQTopicService;
import sem3chatbot.backend.domain.GetTopicsResponse;
import sem3chatbot.backend.domain.UpdateTopicRequest;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://pie3bot.azurewebsites.net"}, allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("/faq-topics")
@RequiredArgsConstructor
public class FAQTopicController {
    private final FAQTopicService topicService;

    @PatchMapping
    public ResponseEntity<Void> updateTopic(@RequestBody @Valid UpdateTopicRequest request){
        topicService.updateTopic(request);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<GetTopicsResponse> getTopics(){
        GetTopicsResponse res = topicService.getTopics();
        return ResponseEntity.ok(res);
    }
}
