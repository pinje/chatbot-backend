package sem3chatbot.backend.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;

@Data
@Builder
@Entity
@Table(name ="log")
@AllArgsConstructor
@NoArgsConstructor
public class MessageEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    @Column(name="message")
    private String message;
    //    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "senderId")
//    private UserEntity sender;
    @Column(name="type")
    private String type;
    @Column(name="time")
    private Time time;
}
