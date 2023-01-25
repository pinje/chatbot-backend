package sem3chatbot.backend.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidAnswerIdException extends ResponseStatusException {
    public InvalidAnswerIdException() {super(HttpStatus.NOT_FOUND, "INVALID_ANSWER_ID");}
}

