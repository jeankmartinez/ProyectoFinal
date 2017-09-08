/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproyect;

import ciudadano.Ciudadano;
import inmueble.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import servicios.Conexion;

/**
 *
 * @author jcmc0669
 */
public class FinalProyect {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        // TODO code application logic here

        Scanner scan = new Scanner(System.in);
        int opcion;

        //Menu
        String menu;
        //Mensajes de errores
        String errorMsj = "Error en la opción :";

        menu = "***********************\n"
                + "*         MENU        *\n"
                + "* 1. leer Info        *\n"
                + "* 2. crear Cuidadano  *\n"
                + "* 3. crear Inmueble   *\n"
                + "* 4. reporte Inmuble  *\n"
                + "* 5. SALIR            *\n"
                + "***********************\n"
                + "Por favor Digite la opción deseada: ";

        do {
            System.out.println("********************************************");
            //Mostrar Menu
            System.out.println(menu);

            //Solicitar Menu
            opcion = scan.nextInt();

            switch (opcion) {
                case 1:
                    leerInfo();
                    break;
                case 2:
                    crearCuidadano();
                    break;
                case 3:
                    crearInmueble();
                    break;
                case 4:
                    reporteInmuble();
                    break;
                case 5:
                    System.out.println("********************************************");
                    System.out.println("GRACIAS POR UTILIZAR SU CAJERO DE CONFIANZA ");
                    System.out.println("********************************************");
                    break;

                default:
                    System.out.println("********************************************");
                    System.out.println(" ERROR, LA OPCIÓN NO ESTA DISPONIBLE ");
                    System.out.println("********************************************");
                    System.out.println("********************************************\n\n");
                    break;
            }
        } while (opcion != 6);
    }

    public static void leerInfo() throws SQLException {

        Conexion conexion = new Conexion();
        Connection con = conexion.getConnection();
        Statement st;
        ResultSet rs;
        try {
            String sql = "select * from ciudadano";

            st = con.createStatement();
            rs = st.executeQuery(sql);

            System.out.println("***************** CIUDADANOS ***************************");
            while (rs.next()) {
                System.out.println(" ID " + rs.getString(1));
                System.out.println(" Nombre " + rs.getString(2));
                System.out.println(" Apellido " + rs.getString(3));
                System.out.println("********************************************************");
            }

            con.close();
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static Ciudadano GetCiudadano(String idCiudadano) throws SQLException {
        Ciudadano ciudadano = null;
        Inmueble inmueble = null;
        Conexion conexion = new Conexion();
        Connection con = conexion.getConnection();
        Statement st;
        ResultSet rs;
        try {
            
            PreparedStatement pstmt = con.prepareStatement("SELECT C.*, I.* FROM ciudadano C LEFT JOIN inmueble I ON C.id = I.id_ciudadano WHERE C.id = ?");

            pstmt.setString(1, idCiudadano);

            rs = pstmt.executeQuery();
            List<Inmueble> inmuebles = new ArrayList();
            while (rs.next()) {
                if (ciudadano == null)
                {
                    ciudadano = new Ciudadano(rs.getString(1), rs.getString(2), rs.getString(3), null);
                }
                
                if (rs.getString(4) != null){
                    inmueble = GetTipeInmueble(rs.getString(6), rs.getString(4), rs.getString(7), rs.getDouble(8), rs.getBigDecimal(9), rs.getInt(10));
                    inmuebles.add(inmueble);
                }
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ciudadano;
    }
    
    public static Inmueble GetTipeInmueble(String tipoInmueble, String codigoNacional, String direccion, Double area, BigDecimal valorComercial, int estrato){
        Inmueble inmueble = null;
        TipoInmuebleEnum enumerador = TipoInmuebleEnum.valueOf(tipoInmueble);
        switch (enumerador) 
        {
            case APTO:
                inmueble = new Apartamento(codigoNacional, direccion, area, valorComercial, estrato);
                break;
            case CASA:
                inmueble = new Casa(codigoNacional, direccion, area, valorComercial, estrato);
                break;
            case LOTE:
                inmueble = new Lote(codigoNacional, direccion, area, valorComercial, estrato);
                break;
            default:
                System.out.println("********************************************");
                System.out.println(" ERROR, LA OPCIÓN NO ESTA DISPONIBLE ");
                System.out.println("********************************************");
                System.out.println("********************************************\n\n");
                break;
        }
        return inmueble;
    }
        
    public static void crearCuidadano() throws SQLException {

        System.out.println("Ingrese el id del ciudadano");
        String idCiudadano = new Scanner(System.in).next();

        System.out.println("Ingrese el nombres:");
        String nombreCiudadano = new Scanner(System.in).next();

        System.out.println("Ingrese el Apellidos:");
        String nombreApellido = new Scanner(System.in).next();

        Ciudadano ciudadano = new Ciudadano(idCiudadano, nombreCiudadano, nombreApellido, null);

        HashMap<String, Ciudadano> ciudadanoMap = new HashMap<>();

        ciudadanoMap.put(idCiudadano, ciudadano);

        guardarCiudadano(idCiudadano, ciudadanoMap);
        //ciudadanoMap.get(idCiudadano);

        System.out.println("***************** Datos en el HashMap ***************************");
        System.out.println(ciudadano.toString());
        System.out.println("*****************************************************************");
    }

    public static void guardarCiudadano(String idCiudadano, HashMap<String, Ciudadano> ciudadanoMap) throws SQLException {

        Conexion conexion = new Conexion();
        Connection con = conexion.getConnection();
        Statement st;
        ResultSet rs;
        try {
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO ciudadano (id,nombres,apellidos) VALUES (?, ?, ?)");

            pstmt.setString(1, idCiudadano);
            pstmt.setString(2, ciudadanoMap.get(idCiudadano).getNombre());
            pstmt.setString(3, ciudadanoMap.get(idCiudadano).getApellido());
            pstmt.executeUpdate();

            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void crearInmueble() throws SQLException {
        Inmueble inmueble = null;

        for (int enumerador = 0; enumerador < TipoInmuebleEnum.values().length; enumerador++) {
            System.out.println(String.format("%s",TipoInmuebleEnum.values()[enumerador]));
        }
        System.out.println("Selecione tipo de inmueble: ");
        String tipoInmueble = new Scanner(System.in).next();
        
        
        System.out.println("Digíte el código nacional: ");
        String codigoNacional = new Scanner(System.in).next();

        System.out.println("Digíte la dirección: ");
        String direccion = new Scanner(System.in).next();

        System.out.println("Digíte el área: ");
        double area = new Scanner(System.in).nextDouble();

        System.out.println("Digíte el valor comercial: ");
        BigDecimal valorComercial = new Scanner(System.in).nextBigDecimal();

        System.out.println("Digíte el estracto: ");
        int estrato = new Scanner(System.in).nextInt();

        System.out.println("Ingrese el id del ciudadano");
        String idCiudadano = new Scanner(System.in).next();
        
        Ciudadano ciudadano = GetCiudadano(idCiudadano);
        
        if (ciudadano == null)
        {
            System.out.println(String.format("El ciudadano con el id %s no existe", idCiudadano));
        }
        else
        {
            inmueble = GetTipeInmueble(tipoInmueble, codigoNacional, direccion, area, valorComercial, estrato);
            crearInmueble(ciudadano, inmueble);
        }
    }
    
    public static void crearInmueble(Ciudadano ciudadano, Inmueble inmueble) throws SQLException 
    {
        Conexion conexion = new Conexion();
        Connection con = conexion.getConnection();
        Statement st;
        ResultSet rs;
        String tipo = "";
        
        if (Apartamento.class.equals(inmueble)){
            tipo = String.valueOf(TipoInmuebleEnum.APTO);
        }else if (Casa.class.equals(inmueble)){
            tipo = String.valueOf(TipoInmuebleEnum.CASA);
        }else{
            tipo = String.valueOf(TipoInmuebleEnum.LOTE);
        }
        
        try {
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO inmueble (codigoNacional, id_ciudadano, tipo, direccion, area, valorComercial, estrato) VALUES (?, ?, ?, ?, ?, ?, ?)");

            pstmt.setString(1, inmueble.getCodigoNacional());
            pstmt.setString(2, ciudadano.getId());
            pstmt.setString(3, tipo);
            pstmt.setString(4, inmueble.getDireccion());
            pstmt.setString(5, String.valueOf(inmueble.getArea()));
            pstmt.setString(6, String.valueOf(inmueble.getValorComercial()));
            pstmt.setString(7, String.valueOf(inmueble.getEstrato()));
            pstmt.executeUpdate();

            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void reporteInmuble() {

    }
}
