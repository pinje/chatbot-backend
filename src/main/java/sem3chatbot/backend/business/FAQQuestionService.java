package sem3chatbot.backend.business;

import sem3chatbot.backend.domain.CreateQuestionRequest;
import sem3chatbot.backend.domain.FAQQuestion;
import sem3chatbot.backend.domain.GetQuestionsByTopicIdRequest;
import sem3chatbot.backend.domain.GetQuestionsByTopicIdResponse;
import sem3chatbot.backend.business.exception.InvalidQuestionIdException;


public interface FAQQuestionService {
    /**
     Creates a new question attached to a specific topic of the FAQ.
     @param request The request body, containing the topic ID, the parent ID if present and the question text.
     */
    void create(CreateQuestionRequest request);
    /**
     Gets all questions associated with a specific topic, along with their nested children, if any are present.
     @param request A request object containing the topic ID.
     @return A response object containing the questions for the topic,
     along with their nested children, if any are present.
     */
    GetQuestionsByTopicIdResponse getPrimaryQuestionsByTopicId(GetQuestionsByTopicIdRequest request);

    /**
     * Deletes the question by ID.
     * @param questionId the question ID to be deleted.
     * @throws InvalidQuestionIdException if the question ID is not present in the database.
     */
    void delete(long questionId);

    FAQQuestion getById(long questionId);
}
