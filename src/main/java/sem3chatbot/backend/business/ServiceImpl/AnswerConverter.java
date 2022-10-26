package sem3chatbot.backend.business.ServiceImpl;

import sem3chatbot.backend.domain.answers.Answer;
import sem3chatbot.backend.persistence.entity.AnswerEntity;

public class AnswerConverter {
    public static Answer convert(AnswerEntity entity){
        return Answer.builder()
                .id(entity.getId())
                .secondaryKeyword(entity.getSecondaryKeyword())
                .tertiaryKeyword(entity.getTertiaryKeyword())
                .solution(entity.getSolution())
                .build();
    }
}
