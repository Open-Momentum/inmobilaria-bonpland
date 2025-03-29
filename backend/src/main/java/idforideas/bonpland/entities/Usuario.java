package idforideas.bonpland.entities;

import jakarta.persistence.*;

import java.io.Serial;
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
    @Serial
    private final static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Basic(optional = false)
    @Column(name = "apellido",nullable = false)
    private String apellido;

    @Basic(optional = false)
    @Column(name = "clave",nullable = false)
    private String clave;

    @Basic(optional = false)
    @Column(name = "correo",nullable = false)
    private String correo;

    @Basic(optional = false)
    @Column(name = "telefono",nullable = false)
    private String telefono;

    @Basic(optional = false)
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @Basic(optional = false)
    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;



    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<Inmueble> inmuebleList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<Publicacion> publicacionList;
    
}
