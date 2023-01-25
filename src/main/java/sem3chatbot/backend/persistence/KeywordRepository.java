package sem3chatbot.backend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sem3chatbot.backend.persistence.entity.KeywordEntity;

import java.util.List;

@Repository
public interface KeywordRepository extends JpaRepository<KeywordEntity, Long> {

    // Could be very expensive in performance as a long question will create a lot of queries.
    // Probably not working. containing checks the whole string and still can break in cases like:
    // parameter: "room" -->   "ROOMservice" instead of only room, sdfdsf etc.
//    List<KeywordEntity> findByTextOrSynonymsContaining(String keyword, String synonyms);

    List<KeywordEntity> findByType(String type);

    KeywordEntity findByText(String text);

    boolean existsByText(String text);
}
