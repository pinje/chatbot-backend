package sem3chatbot.backend.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateAnswerRequest {
    private long questionId;
    private String answerText;
    private String answerTextDutch;
    private String answerLink;
}

