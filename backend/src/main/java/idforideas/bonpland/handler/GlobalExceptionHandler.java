
package idforideas.bonpland.handler;

import idforideas.bonpland.exception.UsuarioNoEncontradoException;
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
 *
 * @author Martina
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
     @ExceptionHandler(UsuarioNoEncontradoException.class)
    public ResponseEntity<String>handleUserNotFoundException(UsuarioNoEncontradoException uf){
         return new ResponseEntity<>(uf.getMessage(), HttpStatus.NOT_FOUND);
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
