package idforideas.bonpland.dto;

import idforideas.bonpland.entities.Inmueble;
import idforideas.bonpland.entities.Usuario;
import idforideas.bonpland.enumerations.Estado;
import idforideas.bonpland.enumerations.TipoMoneda;
import idforideas.bonpland.enumerations.TipoOperacion;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Martina
 */
@Data
@NoArgsConstructor
public class PublicacionDTO {
    @Pattern(
            regexp = "^[a-zA-Z0-9\\s]+$",
            message = "El titulo no puede tener caracteres especiales"
    )
    @Length(min = 1, max = 50, message = "El titulo debe tener entre 1 y 50 caracteres")
    private String titulo;

    @Pattern(
            regexp = "^[a-zA-Z0-9\\s]+$",
            message = "La descripciom no puede tener caracteres especiales"
    )
    @Length(min = 1, max = 700, message = "La descripcion debe tener entre 1 y 50 caracteres")
    private String descripcion;

    @Min(value = 1, message = "El precio debe tener al menos 1 digito")
    @Max(value = 99999999, message = "El precio debe tener como maximo 8 digitos")
    private double precio;

    @Length(min = 7, max = 9, message = "El estado debe tener entre 7 y 9 letras")
    private Estado estado;

    @Length(min = 3, max = 10, message = "El estado debe tener entre 3 y 10 letras")
    private TipoMoneda tipoMoneda;

    @Length(min = 5, max = 17, message = "El estado debe tener entre 5 y 17 letras")
    private TipoOperacion tipoOperacion;

    @Min(value = 1, message = "el valor debe ser 1")
    private Inmueble inmueble;

    @Min(value = 1, message = "el valor debe ser 1")
    private Usuario usuario;
    
}
