package sem3chatbot.backend.business.ServiceImpl;

import sem3chatbot.backend.domain.answers.Answer;
import sem3chatbot.backend.domain.keywords.Keyword;
import sem3chatbot.backend.persistence.entity.AnswerEntity;

public class AnswerConverter {
    public static Answer convert(AnswerEntity entity){
        return Answer.builder()
                .id(entity.getId())
                .questionsKeywords(entity.getQuestionsKeywords())
                .secondaryKeywords(entity.getSecondaryKeywords())
                .tertiaryKeywords(entity.getTertiaryKeywords())
                .solution(SolutionConverter.convert(entity.getSolution()))
                .build();
    }
}
