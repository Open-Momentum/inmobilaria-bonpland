package idforideas.bonpland.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
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

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @NotNull(message = "El nombre no debe ser nulo")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Column(name = "nombre")
    private String nombre;

    @Basic(optional = false)
    @NotNull(message = "El apellido no debe ser nulo")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    @Column(name = "apellido")
    private String apellido;

    @Basic(optional = false)
    @NotNull(message = "La clave no debe ser nula")
    @Size(min = 8, max = 20,message = "La clave debe tener entre 8 y 20 caracteres")
    @Column(name = "clave")
    private String clave;

    @Basic(optional = false)
    @NotNull(message = "El correo no debe ser nulo")
    //TODO regex de correo valido
    @Column(name = "correo")
    private String correo;

    @Basic(optional = false)
    @NotNull(message = "El telefono no debe ser nulo")
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
