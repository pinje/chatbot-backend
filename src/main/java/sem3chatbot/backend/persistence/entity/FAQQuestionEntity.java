package sem3chatbot.backend.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import sem3chatbot.backend.domain.FAQTopic;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@Table(name = "faq_questions")
@AllArgsConstructor
@NoArgsConstructor
public class FAQQuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,optional = true)
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    private FAQQuestionEntity parent;

    @Column
    private String questionText;
    @Column
    private String questionTextDutch;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<FAQQuestionEntity> children;

    @JoinColumn(name="topic_id")
    @ManyToOne
    private FAQTopicEntity topicId;

}
