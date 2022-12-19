package sem3chatbot.backend.business.ServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sem3chatbot.backend.business.CreateLogService;
import sem3chatbot.backend.domain.requests.CreateLogRequest;
import sem3chatbot.backend.persistence.LogRepository;
import sem3chatbot.backend.persistence.RatingRepository;
import sem3chatbot.backend.persistence.entity.MessageEntity;
import sem3chatbot.backend.persistence.entity.RatingEntity;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class CreateLogServiceImpl implements CreateLogService{
    private final RatingRepository ratingRepository;
    private final LogRepository logRepository;
    @Override
    public void createLog (CreateLogRequest request) {
       final RatingEntity rating = RatingEntity.builder().rating(request.getRating()).build();


        List<MessageEntity> messages = Arrays.stream(request.getMessages()).toList();
        messages.forEach(m -> m.setRating(rating));
        logRepository.saveAll(messages);
    }
}
