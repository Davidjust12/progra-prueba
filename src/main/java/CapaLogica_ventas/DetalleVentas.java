/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaLogica_ventas;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author David
 */
@Entity
@Table(name = "detalle_ventas", catalog = "data_ventas", schema = "usuariosql")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleVentas.findAll", query = "SELECT d FROM DetalleVentas d"),
    @NamedQuery(name = "DetalleVentas.findByIddetalleventas", query = "SELECT d FROM DetalleVentas d WHERE d.iddetalleventas = :iddetalleventas"),
    @NamedQuery(name = "DetalleVentas.findByCantidad", query = "SELECT d FROM DetalleVentas d WHERE d.cantidad = :cantidad"),
    @NamedQuery(name = "DetalleVentas.findByPrecio", query = "SELECT d FROM DetalleVentas d WHERE d.precio = :precio"),
    @NamedQuery(name = "DetalleVentas.findByTotal", query = "SELECT d FROM DetalleVentas d WHERE d.total = :total")})
public class DetalleVentas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddetalleventas", nullable = false)
    private Integer iddetalleventas;
    @Column(name = "cantidad")
    private Integer cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio", precision = 8, scale = 2)
    private BigDecimal precio;
    @Column(name = "total", precision = 8, scale = 2)
    private BigDecimal total;
    @JoinColumn(name = "idproducto", referencedColumnName = "idproducto")
    @ManyToOne(fetch = FetchType.LAZY)
    private Producto idproducto;
    @JoinColumn(name = "idventa", referencedColumnName = "idventa")
    @ManyToOne(fetch = FetchType.LAZY)
    private Ventas idventa;

    public DetalleVentas() {
    }

    public DetalleVentas(Integer iddetalleventas) {
        this.iddetalleventas = iddetalleventas;
    }

    public Integer getIddetalleventas() {
        return iddetalleventas;
    }

    public void setIddetalleventas(Integer iddetalleventas) {
        this.iddetalleventas = iddetalleventas;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Producto getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Producto idproducto) {
        this.idproducto = idproducto;
    }

    public Ventas getIdventa() {
        return idventa;
    }

    public void setIdventa(Ventas idventa) {
        this.idventa = idventa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddetalleventas != null ? iddetalleventas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleVentas)) {
            return false;
        }
        DetalleVentas other = (DetalleVentas) object;
        if ((this.iddetalleventas == null && other.iddetalleventas != null) || (this.iddetalleventas != null && !this.iddetalleventas.equals(other.iddetalleventas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CapaLogica_ventas.DetalleVentas[ iddetalleventas=" + iddetalleventas + " ]";
    }
    
}
