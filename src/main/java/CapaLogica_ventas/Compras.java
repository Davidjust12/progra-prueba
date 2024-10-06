/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaLogica_ventas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author David
 */
@Entity
@Table(name = "compras", catalog = "data_ventas", schema = "usuariosql")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Compras.findAll", query = "SELECT c FROM Compras c"),
    @NamedQuery(name = "Compras.findByIdcompra", query = "SELECT c FROM Compras c WHERE c.idcompra = :idcompra"),
    @NamedQuery(name = "Compras.findByFecha", query = "SELECT c FROM Compras c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "Compras.findByHora", query = "SELECT c FROM Compras c WHERE c.hora = :hora"),
    @NamedQuery(name = "Compras.findByNumDocumento", query = "SELECT c FROM Compras c WHERE c.numDocumento = :numDocumento"),
    @NamedQuery(name = "Compras.findByTipoDocumento", query = "SELECT c FROM Compras c WHERE c.tipoDocumento = :tipoDocumento"),
    @NamedQuery(name = "Compras.findByTotal", query = "SELECT c FROM Compras c WHERE c.total = :total")})
public class Compras implements Serializable {

    @OneToMany(mappedBy = "idcompra", fetch = FetchType.LAZY)
    private List<DetalleCompras> detalleComprasList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcompra", nullable = false)
    private Integer idcompra;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 10)
    @Column(name = "hora", length = 10)
    private String hora;
    @Size(max = 7)
    @Column(name = "num_documento", length = 7)
    private String numDocumento;
    @Size(max = 7)
    @Column(name = "tipo_documento", length = 7)
    private String tipoDocumento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total", precision = 8, scale = 2)
    private BigDecimal total;
    @JoinColumn(name = "idproveedor", referencedColumnName = "idproveedor")
    @ManyToOne(fetch = FetchType.LAZY)
    private Proveedor idproveedor;
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuarios idusuario;

    public Compras() {
    }

    public Compras(Integer idcompra) {
        this.idcompra = idcompra;
    }

    public Integer getIdcompra() {
        return idcompra;
    }

    public void setIdcompra(Integer idcompra) {
        this.idcompra = idcompra;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(java.sql.Date fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Proveedor getIdproveedor() {
        return idproveedor;
    }

    public void setIdproveedor(Proveedor idproveedor) {
        this.idproveedor = idproveedor;
    }

    public Usuarios getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Usuarios idusuario) {
        this.idusuario = idusuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcompra != null ? idcompra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Compras)) {
            return false;
        }
        Compras other = (Compras) object;
        if ((this.idcompra == null && other.idcompra != null) || (this.idcompra != null && !this.idcompra.equals(other.idcompra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CapaLogica_ventas.Compras[ idcompra=" + idcompra + " ]";
    }

    @XmlTransient
    public List<DetalleCompras> getDetalleComprasList() {
        return detalleComprasList;
    }

    public void setDetalleComprasList(List<DetalleCompras> detalleComprasList) {
        this.detalleComprasList = detalleComprasList;
    }
    
}
