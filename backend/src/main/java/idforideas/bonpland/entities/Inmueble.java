package idforideas.bonpland.entities;

import idforideas.bonpland.enumerations.TipoPropiedad;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @Column(name = "descripcion")
    private String descripcion;

    @Basic(optional = false)
    @NotNull
    @Column(name = "codigo",unique = true)
    private String codigo;

    @Basic(optional = false)
    @NotNull
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
    private int metrosCuadrados;

    @Basic(optional = false)
    @NotNull
    @Column(name = "tipo_propiedad")
    @Enumerated(EnumType.STRING)
    private TipoPropiedad tipoPropiedad;

    @OneToMany(mappedBy = "inmueble")
    private List<Foto> fotos;

    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Inmueble(Long id, String descripcion, String direccion, int codigoPostal, int cantAmbientes, int cantDormi,
                    int cantBanos, int cantCochera, int metrosCuadrados, TipoPropiedad tipoPropiedad,
                    List<Foto> fotos, Usuario usuario) {
        this.id = id;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.cantAmbientes = cantAmbientes;
        this.cantDormi = cantDormi;
        this.cantBanos = cantBanos;
        this.cantCochera = cantCochera;
        this.metrosCuadrados = metrosCuadrados;
        this.tipoPropiedad = tipoPropiedad;
        this.fotos = fotos;
        this.usuario = usuario;
    }

}
