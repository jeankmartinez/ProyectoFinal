/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproyect;

import ciudadano.Ciudadano;
import com.sun.xml.internal.ws.api.model.ExceptionType;
import inmueble.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
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

        Scanner scan = new Scanner(System.in);
        int opcion;

        //Menu
        String menu = GenerarMenu();
        //Mensajes de errores
        String errorMsj = "Error en la opción :";

        do {
            System.out.println("\n********************************************************");
            //Mostrar Menu
            System.out.println(menu);

            //Solicitar opción diligenciada
            opcion = scan.nextInt();

            //Se valida la opción digitada.
            switch (opcion) {
                case 1:
                    LeerInfo();
                    break;
                case 2:
                    CrearCuidadano();
                    break;
                case 3:
                    CrearInmueble();
                    break;
                case 4:
                    ReporteInmuble();
                    break;
                case 5:
                    MostrarMensajeSalida();
                    break;
                default:
                    MostrarMensajeErrorOpc();
                    break;
            }
        } while (opcion != 6);
    }

    /**
     * @func ----- LeerInfo
     * @desc ----- Consulta la información de la db
     * @author --- jean.martiinez
     * @example -- Iteria
     * @date ----- 06/09/2017
    *
     */
    public static void LeerInfo() throws SQLException {

        Conexion conexion = new Conexion();
        Connection con = conexion.getConnection();
        Statement st;
        ResultSet rs;
        try {
            String sql = "select * from ciudadano";

            st = con.createStatement();
            rs = st.executeQuery(sql);

            System.out.println("*********** TODOS LOS CIUDADANOS ***********************");
            while (rs.next()) {
                System.out.println(" ID: " + rs.getString(1));
                System.out.println(" Nombre: " + rs.getString(2));
                System.out.println(" Apellido: " + rs.getString(3));
                System.out.println("--------------------------------------------------------");
            }
            System.out.println("********************************************************");

            sql = "select * from inmueble";

            st = con.createStatement();
            rs = st.executeQuery(sql);

            System.out.println("*********** TODOS LOS INMUEBLES ************************");
            while (rs.next()) {
                System.out.println(" Codigo Nacional: " + rs.getString(1));
                System.out.println(" ID Ciudadano: " + rs.getString(2));
                System.out.println(" Tipo: " + rs.getString(3));
                System.out.println(" Direccion: " + rs.getString(4));
                System.out.println(" Area: " + rs.getString(5));
                System.out.println(" Valor Comercial: " + rs.getString(6));
                System.out.println(" Estrato: " + rs.getString(7));
                System.out.println("--------------------------------------------------------");
            }
            System.out.println("********************************************************");

            con.close();
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @func ----- GetCiudadano
     * @desc ----- Consulta del ciudadano y sus inmuebles
     * @author --- jean.martiinez
     * @example -- Iteria
     * @date ----- 07/09/2017
    *
     */
    public static Ciudadano GetCiudadano(String idCiudadano) throws SQLException {
        Ciudadano ciudadano = null;
        Inmueble inmueble = null;
        Conexion conexion = new Conexion();
        Connection con = conexion.getConnection();
        //Statement st;
        ResultSet rs;
        try {

            PreparedStatement pstmt = con.prepareStatement("SELECT C.*, I.* FROM ciudadano C LEFT JOIN inmueble I ON C.id = I.id_ciudadano WHERE C.id = ?");

            pstmt.setString(1, idCiudadano);

            rs = pstmt.executeQuery();
            List<Inmueble> inmuebles = new ArrayList();
            while (rs.next()) {
                //Se valida ciudadano para solamente se pase 1 vez el ciudadano
                //Se valida si la consulta trajo información del inmueble o esta nula.
                if (rs.getString(4) != null) {
                    inmueble = GetTipeInmueble(rs.getString(6), rs.getString(4), rs.getString(7), rs.getDouble(8), rs.getBigDecimal(9), rs.getInt(10));
                    inmuebles.add(inmueble);
                }
            }
            if (rs.last()) {
                ciudadano = new Ciudadano(rs.getString(1), rs.getString(2), rs.getString(3), inmuebles);
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ciudadano;
    }

    public static Inmueble GetInmueble(String codigoNacional) throws SQLException {
        Inmueble inmueble = null;
        Conexion conexion = new Conexion();
        Connection con = conexion.getConnection();
        //Statement st;
        ResultSet rs;
        try {

            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM inmueble WHERE codigoNacional = ?");

            pstmt.setString(1, codigoNacional);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                String tipo = rs.getString(3);
                String codigoNacionalConsultado = rs.getString(1);
                String direccion = rs.getString(4);
                Double area = rs.getDouble(5);
                BigDecimal valorComercial = rs.getBigDecimal(6);
                int estrato = rs.getInt(7);
                inmueble = GetTipeInmueble(tipo, codigoNacionalConsultado, direccion, area, valorComercial, estrato);
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inmueble;
    }

    /**
     * @func ----- LeerInfo
     * @desc ----- Verifica el tipo de inmueble y retorna un tipo inmueble
     * @author --- jean.martiinez
     * @example -- Iteria
     * @date ----- 07/09/2017
    *
     */
    public static Inmueble GetTipeInmueble(String tipoInmueble, String codigoNacional, String direccion, Double area, BigDecimal valorComercial, int estrato) {
        Inmueble inmueble = null;
        //TipoInmuebleEnum enumerador = TipoInmuebleEnum.valueOf(tipoInmueble);
        switch (tipoInmueble) {
            case "1":
                inmueble = new Apartamento(codigoNacional, direccion, area, valorComercial, estrato);
                break;
            case "2":
                inmueble = new Casa(codigoNacional, direccion, area, valorComercial, estrato);
                break;
            case "3":
                inmueble = new Lote(codigoNacional, direccion, area, valorComercial, estrato);
                break;
            default:
                MostrarMensajeErrorOpc();
                break;
        }
        return inmueble;
    }

    /**
     * @func ----- CrearCuidadano
     * @desc ----- Crea un ciudadano con sus atributos enviados como parametros
     * @author --- jean.martiinez
     * @example -- Iteria
     * @date ----- 07/09/2017
    *
     */
    public static void CrearCuidadano() throws SQLException {

        System.out.println("Ingrese el id del ciudadano");
        String idCiudadano = new Scanner(System.in).next();

        //Se valida que ya exista el ciudadano
        Ciudadano ciudadanoVal = GetCiudadano(idCiudadano);

        if (ciudadanoVal == null) {

            System.out.println("Ingrese el nombres:");
            String nombreCiudadano = new Scanner(System.in).next();

            System.out.println("Ingrese el Apellidos:");
            String nombreApellido = new Scanner(System.in).next();

            Ciudadano ciudadano = new Ciudadano(idCiudadano, nombreCiudadano, nombreApellido, null);

            HashMap<String, Ciudadano> ciudadanoMap = new HashMap<>();

            ciudadanoMap.put(idCiudadano, ciudadano);

            GuardarCiudadano(idCiudadano, ciudadanoMap);
            //ciudadanoMap.get(idCiudadano);

            System.out.println("*************** Datos del Usuario Guardados *********************");
            System.out.println(ciudadano.toString());
            System.out.println("*****************************************************************");
        } else {
            System.out.println(String.format("El ciudadano con ID %s YA existe", idCiudadano));
        }
    }

    /**
     * @func ----- GuardarCiudadano
     * @desc ----- Función que guarda la información creada del ciudadano.
     * @author --- jean.martiinez
     * @example -- Iteria
     * @date ----- 07/09/2017
    *
     */
    public static void GuardarCiudadano(String idCiudadano, HashMap<String, Ciudadano> ciudadanoMap) throws SQLException {

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

    /**
     * @func ----- CrearInmueble
     * @desc ----- Función crear inmueble con sus atributos enviados como
     * parametros
     * @author --- jean.martiinez
     * @example -- Iteria
     * @date ----- 07/09/2017
    *
     */
    public static void CrearInmueble() throws SQLException {
        Inmueble inmueble = null;

        System.out.println("Ingrese el id del ciudadano");
        String idCiudadano = new Scanner(System.in).next();

        Ciudadano ciudadano = GetCiudadano(idCiudadano);

        if (ciudadano == null) {
            System.out.println(String.format("El ciudadano con el id %s NO existe", idCiudadano));
        } else {
            try {

                for (int enumerador = 0; enumerador < TipoInmuebleEnum.values().length; enumerador++) {
                    System.out.println(String.format(" %s - %s", enumerador + 1, TipoInmuebleEnum.values()[enumerador]));
                }
                System.out.println("Selecione tipo de inmueble: ");
                String tipoInmueble = new Scanner(System.in).next();
                
                if (Integer.parseInt(tipoInmueble) > TipoInmuebleEnum.values().length || Integer.parseInt(tipoInmueble)<1){
                    System.out.println(String.format("La opción %s no se encuentra dentro de las opciones", tipoInmueble));
                    return;
                }
                
                System.out.println("Digíte el código nacional: ");
                String codigoNacional = new Scanner(System.in).next();

                Inmueble inmuebleConsultado = GetInmueble(codigoNacional);

                if (inmuebleConsultado != null) {
                    System.out.println(String.format("El inmueble con el codigo nacional %s ya existe", codigoNacional));
                    return;
                }

                System.out.println("Digíte la dirección: ");
                String direccion = new Scanner(System.in).next();

                System.out.println("Digíte el área: ");
                double area = new Scanner(System.in).nextDouble();

                System.out.println("Digíte el valor comercial: ");
                BigDecimal valorComercial = new Scanner(System.in).nextBigDecimal();

                System.out.println("Digíte el estracto: ");
                int estrato = new Scanner(System.in).nextInt();

                
                CrearInmueble(ciudadano, inmueble);
                
            } catch (Exception ex) {
                
                  System.out.println("Error, el tipo de dato ingresado no corresponde con el formato");
                  return;
                  
            }
        }
    }

    /**
     * @func ----- CrearInmueble(Ciudadano, Inmueble)
     * @desc ----- Función crear inmueble enviando un ciudadano y el inmueble
     * creado
     * @author --- jean.martiinez
     * @example -- Iteria
     * @date ----- 07/09/2017
    *
     */
    public static void CrearInmueble(Ciudadano ciudadano, Inmueble inmueble) throws SQLException {
        Conexion conexion = new Conexion();
        Connection con = conexion.getConnection();
        //Statement st;
        //ResultSet rs;
        String tipo = "";

        if (Apartamento.class.equals(inmueble)) {
            tipo = String.valueOf(TipoInmuebleEnum.APTO);
        } else if (Casa.class.equals(inmueble)) {
            tipo = String.valueOf(TipoInmuebleEnum.CASA);
        } else {
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

            System.out.println("*************** Datos del Inmueble Guardado *********************");
            System.out.println(inmueble.toString());
            System.out.println("*****************************************************************");

            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @func ----- ReporteInmuble
     * @desc ----- Función generar informe de inmueble
     * @author --- jean.martiinez
     * @example -- Iteria
     * @date ----- 07/09/2017
    *
     */
    public static void ReporteInmuble() throws SQLException {

        System.out.println("Ingrese el id del ciudadano");
        String idCiudadano = new Scanner(System.in).next();

        Ciudadano ciudadano = GetCiudadano(idCiudadano);

        if (ciudadano == null) {
            System.out.println(String.format("El ciudadano con el id %s NO existe", idCiudadano));
        } else {
            ReporteInmublePorCiudadano(ciudadano);
        }
    }

    public static void ReporteInmublePorCiudadano(Ciudadano ciudadano) throws SQLException {

        ciudadano.getInmueble();

        List<Inmueble> inmuebles = new ArrayList();
        inmuebles = ciudadano.getInmueble();

        System.out.println("************** Datos del reporte *********************");
        System.out.println("idCiudadano,codigoNacional,area, estrato,valorComercial");
        for (int inte = 0; inte < inmuebles.size(); inte++) {
            System.out.println(ciudadano.getId() + ", " + inmuebles.get(inte).toString());
        }
        System.out.println("******************************************************");
    }

    /**
     * @func ----- MostrarMensajeSalida
     * @desc ----- Función que muestra mensaje de salida utilizado en el menú
     * @author --- jean.martiinez
     * @example -- Iteria
     * @date ----- 08/09/2017
    *
     */
    public static String GenerarMenu() {
        String menu = "***********************\n"
                + "*         MENU        *\n"
                + "* 1. leer Info        *\n"
                + "* 2. crear Cuidadano  *\n"
                + "* 3. crear Inmueble   *\n"
                + "* 4. reporte Inmuble  *\n"
                + "* 5. SALIR            *\n"
                + "***********************\n"
                + "Por favor Digite la opción deseada: ";
        return menu;
    }

    /**
     * @func ----- MostrarMensajeSalida
     * @desc ----- Función que muestra mensaje de salida utilizado en el menú
     * @author --- jean.martiinez
     * @example -- Iteria
     * @date ----- 08/09/2017
    *
     */
    public static void MostrarMensajeSalida() {
        System.out.println("********************************************");
        System.out.println("GRACIAS POR UTILIZAR SU CAJERO DE CONFIANZA ");
        System.out.println("********************************************");
    }

    /**
     * @func ----- MostrarMensajeErrorOpc
     * @desc ----- Función que muestra error cuando no selecciona una opción
     * existente.
     * @author --- jean.martiinez
     * @example -- Iteria
     * @date ----- 08/09/2017
    *
     */
    public static void MostrarMensajeErrorOpc() {
        System.out.println("********************************************");
        System.out.println(" ERROR, LA OPCIÓN NO ESTA DISPONIBLE ");
        System.out.println("********************************************");
        System.out.println("********************************************\n\n");
    }
}
