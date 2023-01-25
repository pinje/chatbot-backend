package sem3chatbot.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sem3chatbot.backend.business.CreateLogService;
import sem3chatbot.backend.domain.requests.CreateLogRequest;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://pie3bot.azurewebsites.net", "https://stichtingfontys.sharepoint.com"}, allowedHeaders = {"*"}, allowCredentials="true")
@RequestMapping("/log")
@RequiredArgsConstructor
public class LogController{
    private final CreateLogService createLogService;

    @PostMapping()
    public void createUser (@RequestBody @Validated CreateLogRequest request) {
        createLogService.createLog(request);
    }
}

