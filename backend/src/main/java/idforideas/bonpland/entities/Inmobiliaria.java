
package idforideas.bonpland.entities;

import idforideas.bonpland.enumerations.CondicionFiscal;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Martina
 */
@Entity
@Table(name = "inmobiliaria")
@Data
@NoArgsConstructor
public class Inmobiliaria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;


    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "correo")
    private String correo;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "razon_social")
    private String razonSocial;

    @Basic(optional = false)
    @NotNull
    @Column(name = "cuit")
    private int cuit;

    @Basic(optional = false)
    @NotNull
    @Column(name = "telefono")
    private int telefono;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "c_fiscal")
    @Enumerated(EnumType.STRING)
    private CondicionFiscal cFiscal;

    @OneToOne
    private Usuario usuario;
    
}
