package sem3chatbot.backend.persistence.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

//@Table(name = "keywords")
@Table(name = "keywords_refactored")
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeywordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //key is unique because we wouldn't want to duplicate already existing words into the db, makes it easier
    //to iterate over it afterwards
    @Column(unique = true, name = "text")
    private String text;

    @Column(name = "timestamp")
    private Date timestamp;

    @Column(name = "synonyms")
    private String synonyms;

    @Column(name = "type")
    private String type;
}
