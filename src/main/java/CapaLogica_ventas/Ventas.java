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
@Table(name = "ventas", catalog = "data_ventas", schema = "usuariosql")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ventas.findAll", query = "SELECT v FROM Ventas v"),
    @NamedQuery(name = "Ventas.findByIdventa", query = "SELECT v FROM Ventas v WHERE v.idventa = :idventa"),
    @NamedQuery(name = "Ventas.findByFecha", query = "SELECT v FROM Ventas v WHERE v.fecha = :fecha"),
    @NamedQuery(name = "Ventas.findByHora", query = "SELECT v FROM Ventas v WHERE v.hora = :hora"),
    @NamedQuery(name = "Ventas.findBySerie", query = "SELECT v FROM Ventas v WHERE v.serie = :serie"),
    @NamedQuery(name = "Ventas.findByNumDocumento", query = "SELECT v FROM Ventas v WHERE v.numDocumento = :numDocumento"),
    @NamedQuery(name = "Ventas.findByTipoDocumento", query = "SELECT v FROM Ventas v WHERE v.tipoDocumento = :tipoDocumento"),
    @NamedQuery(name = "Ventas.findByTotal", query = "SELECT v FROM Ventas v WHERE v.total = :total")})
public class Ventas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idventa", nullable = false)
    private Integer idventa;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 10)
    @Column(name = "hora", length = 10)
    private String hora;
    @Size(max = 10)
    @Column(name = "serie", length = 10)
    private String serie;
    @Size(max = 7)
    @Column(name = "num_documento", length = 7)
    private String numDocumento;
    @Size(max = 7)
    @Column(name = "tipo_documento", length = 7)
    private String tipoDocumento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total", precision = 8, scale = 2)
    private BigDecimal total;
    @OneToMany(mappedBy = "idventa", fetch = FetchType.LAZY)
    private List<DetalleVentas> detalleVentasList;
    @JoinColumn(name = "idcliente", referencedColumnName = "idcliente")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente idcliente;
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuarios idusuario;

    public Ventas() {
    }

    public Ventas(Integer idventa) {
        this.idventa = idventa;
    }

    public Integer getIdventa() {
        return idventa;
    }

    public void setIdventa(Integer idventa) {
        this.idventa = idventa;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
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

    @XmlTransient
    public List<DetalleVentas> getDetalleVentasList() {
        return detalleVentasList;
    }

    public void setDetalleVentasList(List<DetalleVentas> detalleVentasList) {
        this.detalleVentasList = detalleVentasList;
    }

    public Cliente getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Cliente idcliente) {
        this.idcliente = idcliente;
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
        hash += (idventa != null ? idventa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ventas)) {
            return false;
        }
        Ventas other = (Ventas) object;
        if ((this.idventa == null && other.idventa != null) || (this.idventa != null && !this.idventa.equals(other.idventa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CapaLogica_ventas.Ventas[ idventa=" + idventa + " ]";
    }
    
}
