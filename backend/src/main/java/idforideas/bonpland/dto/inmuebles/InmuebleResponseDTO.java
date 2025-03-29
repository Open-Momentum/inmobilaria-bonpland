package idforideas.bonpland.dto.inmuebles;

import idforideas.bonpland.enumerations.TipoPropiedad;

public record InmuebleResponseDTO(Long id,
                                  String descripcion,
                                  String direccion,
                                  int codigoPostal,
                                  int cantAmbientes,
                                  int cantDormi,
                                  int cantCochera,
                                  int metrosCuadrados,
                                  TipoPropiedad tipoPropiedad,
                                  String codigo) {
}
