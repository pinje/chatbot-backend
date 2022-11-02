package sem3chatbot.backend.domain.keywords;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Keyword {
    private Long id;
    private String text;
    private Date timestamp;
    private String type;
}
