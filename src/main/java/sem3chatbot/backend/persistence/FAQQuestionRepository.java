package sem3chatbot.backend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import sem3chatbot.backend.persistence.entity.FAQQuestionEntity;

import java.util.List;

public interface FAQQuestionRepository extends JpaRepository<FAQQuestionEntity, Long>{

    List<FAQQuestionEntity> findAllByTopicId_IdAndParent(long topicId, FAQQuestionEntity parent);
    long countAllByTopicId(long topicId);
}
