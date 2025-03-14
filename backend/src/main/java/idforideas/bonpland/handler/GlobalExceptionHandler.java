
package idforideas.bonpland.handler;

import idforideas.bonpland.exception.UsuarioNoEncontradoException;
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
     @ExceptionHandler(UsuarioNoEncontradoException.class)
    public ResponseEntity<String>handleUserNotFoundException(UsuarioNoEncontradoException uf){
         return new ResponseEntity<>(uf.getMessage(), HttpStatus.NOT_FOUND);
    }
}
