package sem3chatbot.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/graph")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://pie3bot.azurewebsites.net"}, allowedHeaders = {"*"}, allowCredentials="true")
public class MSGraphController {
    //MSGraphService consumer goes here
}
