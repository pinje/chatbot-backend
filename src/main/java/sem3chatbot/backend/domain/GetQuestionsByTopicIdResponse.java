package sem3chatbot.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GetQuestionsByTopicIdResponse{
    private List<FAQQuestion> questions;

}
