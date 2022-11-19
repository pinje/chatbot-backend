package sem3chatbot.backend.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import sem3chatbot.backend.business.exception.NoBlankQuestionsException;
import sem3chatbot.backend.business.exception.SolutionNotFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NoBlankQuestionsException.class)
    public ResponseEntity<Map<String, String>> HandleUsernameAlreadyExistsException(NoBlankQuestionsException exception){
        Map<String, String> err = new HashMap<>();
        err.put("message", exception.getLocalizedMessage());
        err.put("status", HttpStatus.BAD_REQUEST.toString());
        err.put("reason", exception.getReason());
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(SolutionNotFoundException.class)
    public ResponseEntity<Map<String, String>> SolutionNotFoundExceptionHandler(NoBlankQuestionsException exception){
        Map<String, String> err = new HashMap<>();
        err.put("message", exception.getLocalizedMessage());
        err.put("status", HttpStatus.NOT_FOUND.toString());
        err.put("reason", exception.getReason());
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);

}}
