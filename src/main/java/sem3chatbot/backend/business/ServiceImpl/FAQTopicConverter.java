package sem3chatbot.backend.business.ServiceImpl;

import sem3chatbot.backend.domain.FAQTopic;
import sem3chatbot.backend.persistence.entity.FAQTopicEntity;

public class FAQTopicConverter {
    public static FAQTopic convert(FAQTopicEntity entity){
        return FAQTopic.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .descriptionDutch(entity.getDescriptionDutch())
                .photo(entity.getPhoto())
                .build();
    }
}
