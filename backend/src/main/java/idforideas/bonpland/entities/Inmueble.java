/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package idforideas.bonpland.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "inmueble")
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
    @Column(name = "codigoPostal")
    private int codigoPostal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantAmbientes")
    private int cantAmbientes;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantDormi")
    private int cantDormi;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantBanos")
    private int cantBanos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantCochera")
    private int cantCochera;
    @Basic(optional = false)
    @NotNull
    @Column(name = "metrosCuadrados")
    private double metrosCuadrados;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "tipoPropiedad")
    @Enumerated
    private String tipoPropiedad;
    @JoinColumn(name = "fotos", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Foto fotos;
    @JoinColumn(name = "usuario", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inmueble")
    private List<Publicacion> publicacionList;
}
