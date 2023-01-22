package sem3chatbot.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateQuestionRequest{
    private long parentId;
    private String questionText;
    private String questionTextDutch;
    private long topicId;
}
