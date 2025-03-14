package idforideas.bonpland.mapper;

import idforideas.bonpland.dto.DTO;

public interface MapperDoble<T, D extends DTO> {
    T map(D dto);
    D map(T entidad);
}
