package sem3chatbot.backend.business.ServiceImpl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sem3chatbot.backend.business.FAQAnswerService;
import sem3chatbot.backend.business.exception.InvalidAnswerIdException;
import sem3chatbot.backend.business.exception.InvalidQuestionIdException;
import sem3chatbot.backend.domain.requests.CreateAnswerRequest;
import sem3chatbot.backend.persistence.FAQAnswerRepository;
import sem3chatbot.backend.persistence.FAQQuestionRepository;
import sem3chatbot.backend.persistence.entity.FAQAnswerEntity;
import sem3chatbot.backend.persistence.entity.FAQQuestionEntity;

import java.util.Optional;

@Service
@AllArgsConstructor
public class FAQAnswerServiceImpl implements FAQAnswerService{
    private final FAQAnswerRepository answerRepository;
    private final FAQQuestionRepository questionRepository;


    @Override
    public void createOrUpdate(CreateAnswerRequest request) {
        Optional<FAQQuestionEntity> question = questionRepository.findById(request.getQuestionId());
        if(question.isPresent()){
            answerRepository.save(FAQAnswerEntity
                    .builder()
                    .answerText(request.getAnswerText())
                    .id(request.getQuestionId())
                    .question(question.get())
                    .build());
        }
        else{
            throw new InvalidQuestionIdException();
        }

    }

    @Override
    public void delete(long answerId) {
        if(answerId == 0){
            throw new InvalidAnswerIdException();
        }
        var answer = answerRepository.findById(answerId);
        answer.ifPresent(answerRepository::delete);
    }
}

