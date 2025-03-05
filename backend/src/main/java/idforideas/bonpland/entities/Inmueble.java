package idforideas.bonpland.entities;

import idforideas.bonpland.enumerations.TipoPropiedad;
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
@Table(name = "inmuebles")
@Data
@NoArgsConstructor
public class Inmueble implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "descripcion")
    private String descripcion;

    @Basic(optional = false)
    @NotNull
    @Column(name = "codigo")
    private int codigo;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "direccion")
    private String direccion;

    @Basic(optional = false)
    @NotNull
    @Column(name = "codigo_postal")
    private int codigoPostal;

    @Basic(optional = false)
    @NotNull
    @Column(name = "cant_ambientes")
    private int cantAmbientes;

    @Basic(optional = false)
    @NotNull
    @Column(name = "cant_dormi")
    private int cantDormi;

    @Basic(optional = false)
    @NotNull
    @Column(name = "cant_banos")
    private int cantBanos;

    @Basic(optional = false)
    @NotNull
    @Column(name = "cant_cochera")
    private int cantCochera;

    @Basic(optional = false)
    @NotNull
    @Column(name = "metros_cuadrados")
    private double metrosCuadrados;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "tipo_propiedad")
    @Enumerated(EnumType.STRING)
    private TipoPropiedad tipoPropiedad;

    @OneToMany(mappedBy = "inmueble")
    private List<Foto> fotos;

    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuario;

}
