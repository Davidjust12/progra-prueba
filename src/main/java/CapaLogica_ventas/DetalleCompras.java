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
@Table(name = "detalle_compras", catalog = "data_ventas", schema = "usuariosql")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleCompras.findAll", query = "SELECT d FROM DetalleCompras d"),
    @NamedQuery(name = "DetalleCompras.findByIddetallecompra", query = "SELECT d FROM DetalleCompras d WHERE d.iddetallecompra = :iddetallecompra"),
    @NamedQuery(name = "DetalleCompras.findByCantidad", query = "SELECT d FROM DetalleCompras d WHERE d.cantidad = :cantidad"),
    @NamedQuery(name = "DetalleCompras.findByPrecio", query = "SELECT d FROM DetalleCompras d WHERE d.precio = :precio"),
    @NamedQuery(name = "DetalleCompras.findByTotal", query = "SELECT d FROM DetalleCompras d WHERE d.total = :total")})
public class DetalleCompras implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddetallecompra", nullable = false)
    private Integer iddetallecompra;
    @Column(name = "cantidad")
    private Integer cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio", precision = 8, scale = 2)
    private BigDecimal precio;
    @Column(name = "total", precision = 8, scale = 2)
    private BigDecimal total;
    @JoinColumn(name = "idcompra", referencedColumnName = "idcompra")
    @ManyToOne(fetch = FetchType.LAZY)
    private Compras idcompra;
    @JoinColumn(name = "idproducto", referencedColumnName = "idproducto")
    @ManyToOne(fetch = FetchType.LAZY)
    private Producto idproducto;

    public DetalleCompras() {
    }

    public DetalleCompras(Integer iddetallecompra) {
        this.iddetallecompra = iddetallecompra;
    }

    public Integer getIddetallecompra() {
        return iddetallecompra;
    }

    public void setIddetallecompra(Integer iddetallecompra) {
        this.iddetallecompra = iddetallecompra;
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

    public Compras getIdcompra() {
        return idcompra;
    }

    public void setIdcompra(Compras idcompra) {
        this.idcompra = idcompra;
    }

    public Producto getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Producto idproducto) {
        this.idproducto = idproducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddetallecompra != null ? iddetallecompra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleCompras)) {
            return false;
        }
        DetalleCompras other = (DetalleCompras) object;
        if ((this.iddetallecompra == null && other.iddetallecompra != null) || (this.iddetallecompra != null && !this.iddetallecompra.equals(other.iddetallecompra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CapaLogica_ventas.DetalleCompras[ iddetallecompra=" + iddetallecompra + " ]";
    }
    
}
