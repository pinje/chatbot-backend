package sem3chatbot.backend.domain.answers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Solution {
    private Long id;
    private String text;
    private String dutchAnswer;

}
