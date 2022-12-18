package sem3chatbot.backend.business.ServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sem3chatbot.backend.business.CreateLogService;
import sem3chatbot.backend.domain.requests.CreateLogRequest;
import sem3chatbot.backend.persistence.LogRepository;

import java.util.Arrays;

@Service
@AllArgsConstructor
public class CreateLogServiceImpl implements CreateLogService{
    private final LogRepository logRepository;
    @Override
    public void createLog (CreateLogRequest request) {
        logRepository.saveAll(Arrays.stream(request.getMessages()).toList());
    }
}
