package sem3chatbot.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sem3chatbot.backend.business.QuestionAnsweringService;
import sem3chatbot.backend.domain.BotResponse;
import sem3chatbot.backend.domain.UserInput;

@RestController
@RequestMapping("/responses")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://pie3bot.azurewebsites.net", "https://stichtingfontys.sharepoint.com"}, allowedHeaders = {"*"}, allowCredentials = "true")
public class ResponseController {

    private final QuestionAnsweringService questionAnsweringService;

    @PostMapping()
    public ResponseEntity<BotResponse> processQuestion(@RequestBody @Validated UserInput request){
        BotResponse res =  questionAnsweringService.processUserInput(request);
        System.out.println(res);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
