package sem3chatbot.backend.business.ServiceImpl;

import sem3chatbot.backend.domain.FAQQuestion;
import sem3chatbot.backend.persistence.entity.FAQQuestionEntity;

import java.util.ArrayList;

public class FAQQuestionConverter {

    /**
     Converts each question and their children into a POJO. This function is recursive
     @param entity The entity to convert. A primary question should be passed here, otherwise
     this function will return an incomplete tree.
     */
    public static FAQQuestion convert(FAQQuestionEntity entity){

        //escape condition, usually triggered upon trying to convert
        //the first entity of a nested children list
        if(entity == null) {
            return null;
        }
        System.out.println("PASSED FOR: " + entity.getId());
        return FAQQuestion.builder()
                .id(entity.getId() == null ? null : entity.getId())
                .questionText(entity.getQuestionText())
                .questionTextDutch(entity.getQuestionTextDutch())
                .topicId(entity.getTopicId() == null ? null :FAQTopicConverter.convert(entity.getTopicId()))
                .children(entity.getChildren().size() == 0 ?
                        new ArrayList<>() :
                        entity.getChildren().stream().map(FAQQuestionConverter::convert).toList())
                .build();
    }
}
