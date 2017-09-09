/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ciudadano;

import inmueble.Inmueble;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import inmueble.*;

/**
 *
 * @author jcmc0669
 */
public class Ciudadano {
    
    private final String id;
    private final String nombre;
    private final String apellido;
    private final List inmueble;

    public Ciudadano(String id, String nombre, String apellido, List inmueble) {
        //super();
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.inmueble = (List) inmueble;
    }
    
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }


    public String getApellido() {
        return apellido;
    }

    public List <Inmueble> getInmueble (Inmueble inmueble){
        //this.inmueble = (List) inmueble;
        //List <Inmueble> = ;
        return null;
    }
    
    
    public List <Inmueble> getInmueble(){
        //this.inmueble = (List) inmueble;
        return inmueble;
    }
    /*
    public void setId(String id) {
        this.id = id;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public void setInmueble(Inmueble inmueble) {
        this.inmueble = (List) inmueble;
    }*/

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ciudadano other = (Ciudadano) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.apellido, other.apellido)) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + Objects.hashCode(this.apellido);
        return hash;
    }

    @Override
    public String toString() {
        return "Ciudadano{" + "id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + '}';
    }
    
}
