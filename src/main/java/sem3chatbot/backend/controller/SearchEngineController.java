package sem3chatbot.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sem3chatbot.backend.business.SearchEngineService;
import sem3chatbot.backend.domain.answers.SearchEngineTopThreeResponse;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
@CrossOrigin(origins ={"http://localhost:3000", "https://pie3bot.azurewebsites.net", "https://stichtingfontys.sharepoint.com"}, allowedHeaders = {"*"})
public class SearchEngineController {
    // origins = {"http://localhost:3000", "https://pie3bot.azurewebsites.net"}
    private final SearchEngineService searchEngineService;
    @GetMapping()
    public ResponseEntity<SearchEngineTopThreeResponse> getTopThreeLinks(@RequestParam(value = "q")final  String query) throws IOException {
        SearchEngineTopThreeResponse res = searchEngineService.getTopLinksFromSearchQuery(query, 5);
        return ResponseEntity.ok(res);
    }
}
