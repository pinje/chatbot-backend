package sem3chatbot.backend.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidQuestionIdException extends ResponseStatusException{
    public InvalidQuestionIdException() {super(HttpStatus.NOT_FOUND, "INVALID_QUESTION_ID");}
}

