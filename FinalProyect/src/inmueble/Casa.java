/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inmueble;

import java.math.BigDecimal;

/**
 *
 * @author jcmc0669
 */
public class Casa extends Inmueble {
    
    public Casa(String codigoNacional, String direccion, double area, BigDecimal valorComercial, int estrato) {
        super(codigoNacional, direccion, area, valorComercial, estrato);
    }

    @Override
    public BigDecimal CalcularImpuesto() {
        return this.getValorComercial().multiply(BigDecimal.valueOf(this.getArea())).multiply(BigDecimal.valueOf(0,9));
    }
    
    
}
