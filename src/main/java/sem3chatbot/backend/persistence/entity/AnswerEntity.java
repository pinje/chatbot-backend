package sem3chatbot.backend.persistence.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import sem3chatbot.backend.domain.answers.Solution;
import sem3chatbot.backend.domain.keywords.Keyword;

import javax.persistence.*;

@Table(name = "responses")
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "questionKeywordIds")
    private String questionsKeywords;

    @Column(name = "auxiliaryKeywordIds")
    private String secondaryKeywords;

    @Column(name = "topicKeywordIds")
    private String tertiaryKeywords;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "solution")
    private SolutionEntity solution;
}
