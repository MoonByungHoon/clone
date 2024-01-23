package study.clone.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import study.clone.exception.DuplicateUsernameException;
import study.clone.exception.NotEnoughStockException;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(DuplicateUsernameException.class)
  public ResponseEntity<String> handleDuplicateUsernameException(DuplicateUsernameException e) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
  }

  @ExceptionHandler(NotEnoughStockException.class)
  public ResponseEntity<String> handleNotEnoughStockException(NotEnoughStockException e) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
  }
}
