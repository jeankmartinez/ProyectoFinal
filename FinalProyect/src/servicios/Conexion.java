package servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Conexion {
    
    private static String servidor  = "jdbc:mysql://localhost/proyectofinal";
    private static String user      = "root";
    private static String pass      = "root";
    private static String driver    = "com.mysql.jdbc.Driver";
    private static Connection conexion;

    public Conexion() throws SQLException {

        try {
            //Driver JDBC
            Class.forName(driver);
            //Se inicia la conexión
            conexion = DriverManager.getConnection(servidor, user, pass);

        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error en la conexión a la base de datos: " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            conexion = null;
        }
    }
    
    public Connection getConnection(){
        return conexion;
    }
}