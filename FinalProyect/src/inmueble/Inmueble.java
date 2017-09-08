/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inmueble;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author jcmc0669
 */
public abstract class Inmueble {
    
    private final String codigoNacional;
    private final String direccion;
    private final double area;
    private final BigDecimal valorComercial;
    private final int estrato;
    
    public Inmueble(String codigoNacional, String direccion, double area, BigDecimal valorComercial, int estrato) {
        this.codigoNacional = codigoNacional;
        this.direccion = direccion;
        this.area = area;
        this.valorComercial = valorComercial;
        this.estrato = estrato;
    }
    
    public abstract BigDecimal CalcularImpuesto();

    public String getCodigoNacional() {
        return codigoNacional;
    }

    public String getDireccion() {
        return direccion;
    }

    public double getArea() {
        return area;
    }

    public BigDecimal getValorComercial() {
        return valorComercial;
    }

    public int getEstrato() {
        return estrato;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.codigoNacional);
        hash = 97 * hash + Objects.hashCode(this.direccion);
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.area) ^ (Double.doubleToLongBits(this.area) >>> 32));
        hash = 97 * hash + Objects.hashCode(this.valorComercial);
        hash = 97 * hash + this.estrato;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Inmueble other = (Inmueble) obj;
        if (!Objects.equals(this.codigoNacional, other.codigoNacional)) {
            return false;
        }
        if (!Objects.equals(this.direccion, other.direccion)) {
            return false;
        }
        if (Double.doubleToLongBits(this.area) != Double.doubleToLongBits(other.area)) {
            return false;
        }
        if (!Objects.equals(this.valorComercial, other.valorComercial)) {
            return false;
        }
        if (this.estrato != other.estrato) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Inmueble{" + "codigoNacional=" + codigoNacional + ", direccion=" + direccion + ", area=" + area + ", valorComercial=" + valorComercial + ", estrato=" + estrato + '}';
    }
    
    public BigDecimal calculaImpuesto(){return null;}
    
}
