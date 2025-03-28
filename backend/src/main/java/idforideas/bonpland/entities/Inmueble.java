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
    @Column(name = "descripcion")
    private String descripcion;

    @Basic(optional = false)
    @Column(name = "codigo",unique = true, nullable = false)
    private String codigo;

    @Basic(optional = false)
    @Column(name = "direccion",nullable = false)
    private String direccion;

    @Basic(optional = false)
    @Column(name = "codigo_postal",nullable = false)
    private int codigoPostal;

    @Basic(optional = false)
    @Column(name = "cant_ambientes",nullable = false)
    private int cantAmbientes;

    @Basic(optional = false)
    @Column(name = "cant_dormi",nullable = false)
    private int cantDormi;

    @Basic(optional = false)
    @Column(name = "cant_banos",nullable = false)
    private int cantBanos;

    @Basic(optional = false)
    @Column(name = "cant_cochera",nullable = false)
    private int cantCochera;

    @Basic(optional = false)
    @Column(name = "metros_cuadrados",nullable = false)
    private int metrosCuadrados;

    @Basic(optional = false)
    @Column(name = "tipo_propiedad",nullable = false)
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
