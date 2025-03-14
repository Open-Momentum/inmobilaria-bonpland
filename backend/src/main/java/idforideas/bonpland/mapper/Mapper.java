
package idforideas.bonpland.mapper;

/**
 *
 * @author Figueroa Mauro
 */
public interface Mapper<T,D> {

    T aEntidad(D dto);

    D aDTO(T entidad);

}
