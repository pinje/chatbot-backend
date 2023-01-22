package sem3chatbot.backend.business.ServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sem3chatbot.backend.business.FAQTopicService;
import sem3chatbot.backend.business.exception.InvalidTopicIdException;
import sem3chatbot.backend.domain.FAQTopic;
import sem3chatbot.backend.domain.GetTopicsResponse;
import sem3chatbot.backend.domain.UpdateTopicRequest;
import sem3chatbot.backend.persistence.FAQTopicRepository;
import sem3chatbot.backend.persistence.entity.FAQTopicEntity;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class FAQTopicServiceImpl implements FAQTopicService{
    private final FAQTopicRepository topicRepository;
    @Override
    public void updateTopic(UpdateTopicRequest request) {
        Optional<FAQTopicEntity> topicEntity = topicRepository.findById(request.getTopicId());

        if(topicEntity.isEmpty()){
            throw new InvalidTopicIdException();
        }
        topicEntity.get().setDescription(request.getNewDescription());
        topicRepository.saveAndFlush(topicEntity.get());
    }

    @Override
    public GetTopicsResponse getTopics() {
        List<FAQTopic> topics = topicRepository.findAll().
                stream().
                map(FAQTopicConverter::convert)
                .toList();
        return GetTopicsResponse.builder()
                .topics(topics)
                .build();
    }
}
