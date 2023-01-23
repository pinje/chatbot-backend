package sem3chatbot.backend.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TopicQuestionLimitExceededException extends ResponseStatusException{
    public TopicQuestionLimitExceededException() {super(HttpStatus.PRECONDITION_FAILED, "QUESTION_LIMIT_EXCEEDED");}

}
