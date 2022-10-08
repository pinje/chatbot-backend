package sem3chatbot.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sem3chatbot.backend.business.ResponseService;
import sem3chatbot.backend.domain.BotResponse;
import sem3chatbot.backend.domain.UserInput;

@RestController
@RequestMapping("/responses")
@AllArgsConstructor
public class ResponseController {
    private final ResponseService responseService;

    @PostMapping()
    public ResponseEntity<BotResponse> processQuestion(@RequestBody @Validated UserInput request){
        BotResponse res = responseService.processQuestion(request);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

}
