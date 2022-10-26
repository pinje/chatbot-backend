package sem3chatbot.backend.business.ServiceImpl;

import sem3chatbot.backend.domain.answers.Solution;
import sem3chatbot.backend.persistence.entity.SolutionEntity;

public class SolutionConverter {

    public static Solution convert(SolutionEntity entity)
    {
        return Solution.builder()
                .id(entity.getId())
                .text(entity.getText())
                .build();
    }
}
