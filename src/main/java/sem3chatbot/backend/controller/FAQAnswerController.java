package sem3chatbot.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sem3chatbot.backend.business.FAQAnswerService;
import sem3chatbot.backend.domain.requests.CreateAnswerRequest;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://pie3bot.azurewebsites.net"}, allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("/faq-answers")
@RequiredArgsConstructor
public class FAQAnswerController {
    private final FAQAnswerService answerService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid CreateAnswerRequest request){
        answerService.createOrUpdate(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable (value = "id") final long id){
        answerService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
