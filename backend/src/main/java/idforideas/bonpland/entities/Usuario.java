package idforideas.bonpland.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Martina
 */
@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
public class Usuario implements Serializable {
    private final static String  CORREO_PATTERN = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9]{2,}(\\.[a-zA-Z0-9-]{2,}){1,4}$";
    private final static String  NOMBRE_PATTERN = "^[a-zA-Z]+( [a-zA-Z]+)*$";
    private final static String  CLAVE_PATTERN = "^[A-Za-z0-9._\\-@#~&]+$";

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @NotBlank(message = "El nombre no debe ser nulo ni estar vacio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Column(name = "nombre")
    @Pattern(regexp = NOMBRE_PATTERN,message = "El nombre no puede contener caracteres especiales")
    private String nombre;

    @Basic(optional = false)
    @NotBlank(message = "El apellido no debe ser nulo ni estar vacio")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    @Pattern(regexp = NOMBRE_PATTERN,message = "El apellido no puede contener caracteres especiales")
    @Column(name = "apellido")
    private String apellido;

    @Basic(optional = false)
    @NotBlank(message = "La clave no debe ser nula ni estar vacia")
    @Size(min = 8, max = 20,message = "La clave debe tener entre 8 y 20 caracteres")
    @Pattern(regexp = CLAVE_PATTERN,
    message = "La clave solo acepta letras, numeros y los siguientes caracteres especiales (.-_@#~&)")
    @Column(name = "clave")
    private String clave;

    @Basic(optional = false)
    @NotBlank(message = "El correo no debe ser nulo ni estar vacio")
    @Column(name = "correo")
    @Pattern(regexp = CORREO_PATTERN,message = "Formato de correo invalido")
    private String correo;

    @Basic(optional = false)
    @NotBlank(message = "El telefono no debe ser nulo ni estar vacio")
    @Column(name = "telefono")
    private String telefono;

    @Basic(optional = false)
    @NotNull(message = "El rol no debe ser nulo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<Inmueble> inmuebleList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<Publicacion> publicacionList;
    
}
