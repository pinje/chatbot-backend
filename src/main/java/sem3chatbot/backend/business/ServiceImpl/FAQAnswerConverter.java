package sem3chatbot.backend.business.ServiceImpl;

import sem3chatbot.backend.domain.FAQAnswer;
import sem3chatbot.backend.persistence.entity.FAQAnswerEntity;

public class FAQAnswerConverter {
    public static FAQAnswer convert(FAQAnswerEntity entity){
        return FAQAnswer.builder()
                .answerText(entity.getAnswerText())
                .link(entity.getLink())
                .answerTextDutch(entity.getAnswerTextDutch())
                .questionId(entity.getId())
                .build();
    }
}

