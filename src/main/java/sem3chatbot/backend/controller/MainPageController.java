package sem3chatbot.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sem3chatbot.backend.domain.BotResponse;
import sem3chatbot.backend.domain.UserInput;

@RestController
@RequestMapping("")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://pie3bot.azurewebsites.net"})
public class MainPageController{

    @GetMapping()
    public ResponseEntity<String> processQuestion () {
        return ResponseEntity.status(HttpStatus.OK).body("Hi");
    }
}
