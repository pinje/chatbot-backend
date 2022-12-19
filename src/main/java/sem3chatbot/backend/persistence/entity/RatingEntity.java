package sem3chatbot.backend.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@Entity
@Table(name ="rating")
@AllArgsConstructor
@NoArgsConstructor
public class RatingEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    @Column(name="rating")
    private long rating;
    @OneToMany(mappedBy = "rating")
    private List<MessageEntity> messages;
}
