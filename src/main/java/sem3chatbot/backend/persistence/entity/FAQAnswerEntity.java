package sem3chatbot.backend.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Table(name = "faq_answers")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class FAQAnswerEntity {
    @Id
    private Long id;

    @Column(name = "answer_text")
    private String answerText;
    @Column(name = "answer_text_dutch")

    private String answerTextDutch;
    @Column(name = "link")
    private String link;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @MapsId
    private FAQQuestionEntity question;
}
