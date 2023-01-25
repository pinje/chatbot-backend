package sem3chatbot.backend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import sem3chatbot.backend.persistence.entity.MessageEntity;
import sem3chatbot.backend.persistence.entity.RatingEntity;

public interface RatingRepository extends JpaRepository<RatingEntity, Long>{
}
