package sem3chatbot.backend.controller;

import com.microsoft.graph.models.User;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sem3chatbot.backend.BackendApplication;
import sem3chatbot.backend.business.MSGraphService;

import java.io.IOException;
import java.util.Properties;

@RestController
@RequestMapping("/graph")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://pie3bot.azurewebsites.net"}, allowedHeaders = {"*"}, allowCredentials="true")
@PropertySource("classpath:oAuth.properties")
public class MSGraphController {
    private final MSGraphService graph;

    @PostMapping
    public ResponseEntity<String> authenticateGraph() throws Exception {

        final Properties oAuthProperties = new Properties();
        try {
            oAuthProperties.load(BackendApplication.class.getResourceAsStream("/oAuth.properties"));
        } catch (IOException e) {
            System.out.println("Unable to read OAuth configuration");
            return null;
        }
        graph.initializeGraph(oAuthProperties, consumer -> System.out.println(consumer.getMessage()));
        return ResponseEntity.ok("we chillin");
    }


    @GetMapping("/user")
    public ResponseEntity<String> getAuthUsername() throws Exception {
        final User user = graph.getUser();
        final String email = user.mail == null ? user.userPrincipalName : user.mail;
        return ResponseEntity.ok("" + email + user.displayName);
    }

}
