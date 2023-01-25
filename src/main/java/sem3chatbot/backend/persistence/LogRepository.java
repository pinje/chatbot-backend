package sem3chatbot.backend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import sem3chatbot.backend.persistence.entity.MessageEntity;

public interface LogRepository extends JpaRepository<MessageEntity, Long>{
}
