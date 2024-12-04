package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author will
 */
public class Conexion {
    
    public static Connection conectar() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
           
            Connection cn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=bd_sistema_ventas;trustServerCertificate=true;",
                                                        "admin",
                                                        "root");
            System.out.println("Conexión exitosa a SQL Server");
            return cn;
        } catch (ClassNotFoundException e) {
            System.out.println("No se encontró el driver de SQL Server: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error en la conexión local: " + e.getMessage());
        }
        return null;
    }  
}
