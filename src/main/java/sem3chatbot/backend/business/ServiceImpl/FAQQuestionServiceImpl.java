package sem3chatbot.backend.business.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sem3chatbot.backend.business.FAQQuestionService;
import sem3chatbot.backend.business.exception.InvalidQuestionIdException;
import sem3chatbot.backend.business.exception.InvalidTopicIdException;
import sem3chatbot.backend.business.exception.TopicQuestionLimitExceededException;
import sem3chatbot.backend.domain.CreateQuestionRequest;
import sem3chatbot.backend.domain.FAQQuestion;
import sem3chatbot.backend.domain.GetQuestionsByTopicIdRequest;
import sem3chatbot.backend.domain.GetQuestionsByTopicIdResponse;
import sem3chatbot.backend.persistence.FAQAnswerRepository;
import sem3chatbot.backend.persistence.FAQQuestionRepository;
import sem3chatbot.backend.persistence.FAQTopicRepository;
import sem3chatbot.backend.persistence.entity.FAQAnswerEntity;
import sem3chatbot.backend.persistence.entity.FAQQuestionEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FAQQuestionServiceImpl implements FAQQuestionService{

    private final FAQQuestionRepository questionRepository;
    private final FAQAnswerRepository answerRepository;

    private final FAQTopicRepository faqTopicRepository;

    @Override
    public void create(CreateQuestionRequest request) throws TopicQuestionLimitExceededException {
        final var parent = questionRepository.findById(request.getParentId());
        var topic = faqTopicRepository.findById(request.getTopicId());

        if(parent.isEmpty() && questionRepository.countAllByTopicId(request.getTopicId()) >= 5){
            throw new TopicQuestionLimitExceededException();
        }
        FAQQuestionEntity newQuestion;
        if(parent.isEmpty() && questionRepository.countAllByTopicId(request.getTopicId()) < 5){
            newQuestion = FAQQuestionEntity.builder()
                    .questionText(request.getQuestionText())
                    .topicId(topic.get())
                    .build();
            questionRepository.save(newQuestion);
        }
        if(parent.isPresent()){
            newQuestion = FAQQuestionEntity.builder()
                    .questionText(request.getQuestionText())
                    .parent(null)
                    //topic needs to be null in order for the tree to be returned nested
                    .topicId(null)
                    .build();
            newQuestion.setParent(parent.get());
            questionRepository.saveAndFlush(newQuestion);
        }


    }

    @Override
    public GetQuestionsByTopicIdResponse getPrimaryQuestionsByTopicId(GetQuestionsByTopicIdRequest request) {
        List<FAQQuestion> questions = questionRepository.findAllByTopicId_IdAndParent(request.getTopicId(), null)
                .stream().map(FAQQuestionConverter::convert).collect(Collectors.toList());

        //this algorithm traverses through the question tree and sets the answers, if any, to the questions.
        for(var primary: questions){

            //traverse first level of tree for primary questions
            Optional<FAQAnswerEntity> answer = answerRepository.findById(primary.getId());
            answer.ifPresent(entity -> primary.setAnswer(FAQAnswerConverter.convert(entity)));
            for(var child : primary.getChildren()){
                //traverse subtrees for subquestions
                Optional<FAQAnswerEntity> answerChild = answerRepository.findById(child.getId());
                answerChild.ifPresent(entity -> child.setAnswer(FAQAnswerConverter.convert(entity)));

            }
        }

        return GetQuestionsByTopicIdResponse
                .builder()
                .questions(questions)
                .build();
    }

    @Override
    public void delete(long questionId) {
        var questionOptional = questionRepository.findById(questionId);
        if(questionOptional.isPresent()){
            answerRepository.deleteById(questionId);
            questionRepository.delete(questionOptional.get());
        }
        else{
            throw new InvalidQuestionIdException();
        }
    }

    @Override
    public FAQQuestion getById (long questionId) {
        var questionOptional = questionRepository.findById(questionId);
        if(questionOptional.isPresent()){
            return FAQQuestionConverter.convert(questionOptional.get());
        }
        else{
            throw new InvalidQuestionIdException();
        }
    }

}
