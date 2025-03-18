
package idforideas.bonpland.handler;

import idforideas.bonpland.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Martina
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleUsuarioNoEncontradoException(UsuarioNoEncontradoException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(RolNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleRolNoEncontradoException(RolNoEncontradoException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(CorreoExistenteException.class)
    public ResponseEntity<Map<String, Object>> handleCorreoExistente(CorreoExistenteException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(IdInexistenteException.class)
    public ResponseEntity<Map<String, Object>> handleIdInexistenteException(IdInexistenteException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(CredencialesIncorrectasException.class)
    public ResponseEntity<Map<String, Object>> handleCredencialesIncorrectasException(CredencialesIncorrectasException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                                      .map(FieldError::getDefaultMessage)
                                      .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("error", errors.size() == 1 ? errors.get(0) : errors);

        return ResponseEntity.badRequest().body(response);
    }
}
