package sem3chatbot.backend.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoBlankQuestionsException extends ResponseStatusException {
    public NoBlankQuestionsException() {super(HttpStatus.BAD_REQUEST, "QUESTION_WAS_BLANK");}
}
