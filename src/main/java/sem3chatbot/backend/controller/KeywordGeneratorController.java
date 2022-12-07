package sem3chatbot.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sem3chatbot.backend.business.KeywordGeneratorService;

import java.io.IOException;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/generate")
@CrossOrigin(origins ={"http://localhost:3000", "https://pie3bot.azurewebsites.net"}, allowedHeaders = {"*"})
public class KeywordGeneratorController {
    private final KeywordGeneratorService keywordGeneratorService;

    @GetMapping()
    public ResponseEntity<Set<String>> generateKeywords(@RequestParam(value = "url") final String fontysUrl) throws IOException {
        Set<String> keywords = keywordGeneratorService.scrapeKeywords(fontysUrl);
        return ResponseEntity.ok(keywords);
    }

}
