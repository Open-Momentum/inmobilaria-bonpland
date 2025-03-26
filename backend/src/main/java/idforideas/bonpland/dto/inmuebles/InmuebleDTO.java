package idforideas.bonpland.dto.inmuebles;

import idforideas.bonpland.dto.DTO;
import idforideas.bonpland.entities.Foto;
import idforideas.bonpland.enumerations.TipoPropiedad;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record InmuebleDTO (

        Long id,

        @NotNull
        @Size(min = 1, max = 500)
        String descripcion,

        @NotNull
        @Size(min = 1, max = 500)
        String direccion,

        @NotNull
        int codigoPostal,

        @NotNull
        int cantAmbientes,

        @NotNull
        int cantDormi,

        @NotNull
        int cantBanos,

        @NotNull
        int cantCochera,

        @NotNull
        int metrosCuadrados,

        @NotNull
        TipoPropiedad tipoPropiedad

) implements DTO{
}
