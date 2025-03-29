package idforideas.bonpland.dto.inmuebles;

import idforideas.bonpland.dto.DTO;
import idforideas.bonpland.entities.Foto;
import idforideas.bonpland.enumerations.TipoPropiedad;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

public record InmuebleDTO(

        @Schema(example = "1")
        Long id,

        @Schema(example = "Casa en alquiler ubicada en el centro de la ciudad. 3 ambientes con cochera")
        @Size(min = 1, max = 500, message = "Debe tener entre 1 y 500 caracteres")
        String descripcion,

        @Schema(example = "Calle ejemplo n2222")
        @NotBlank
        @Size(min = 5, max = 100, message = "Debe tener entre 5 y 100 caracteres")
        String direccion,


        @Max(value = 9999,message = "El codigo postal debe ser máximo 9999(4 caracteres)")
        @Schema(example = "1888")
        @NotNull
        int codigoPostal,

        @Min(value = 1,message = "La cantidad de ambientes mínima es 1")
        @Max(value = 300,message = "La cantidad de ambientes máxima es de 300")
        @Schema(example = "3")
        @NotNull
        int cantAmbientes,

        @PositiveOrZero(message = "No puedes ingresar un numero menor a 0")
        @Max(value = 200,message = "La cantidad de dormitorios máxima es de 200")
        @Schema(example = "2")
        @NotNull
        int cantDormi,

        @Positive(message = "No puedes ingresar un numero menor a 0")
        @Max(value = 200,message = "La cantidad de baños máxima es de 200")
        @Schema(example = "1")
        @NotNull
        int cantBanos,

        @PositiveOrZero(message = "No puedes ingresar un numero menor a 0")
        @Max(value = 100,message = "La cantidad de cocheras máxima es de 100")
        @Schema(example = "1")
        @NotNull
        int cantCochera,

        @Schema(example = "100")
        @Min(value = 50,message = "Los m2 mínimos deben ser 50")
        @Max(value = 50000,message = "Los m2 máximos deben ser 50000")
        @NotNull
        int metrosCuadrados,

        @Schema(example = "CASA")
        @NotNull
        TipoPropiedad tipoPropiedad

) implements DTO {
}
