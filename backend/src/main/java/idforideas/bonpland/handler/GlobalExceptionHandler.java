
package idforideas.bonpland.handler;

import idforideas.bonpland.exception.UsuarioNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author Martina
 */
@ControllerAdvice
public class GlobalExceptionHandler {
     @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<String>handleUserNotFoundException(UsuarioNotFoundException uf){
         return new ResponseEntity<>(uf.getMessage(), HttpStatus.NOT_FOUND);
    }
}
