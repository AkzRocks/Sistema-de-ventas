package controlador;

import conexion.Conexion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.CabeceraVenta;
import modelo.DetalleVenta;

/**
 * @author will
 */
public class RegistrarVentaDAO {
    
    //ultimo id de la cabecera
    public static int idCabeceraRegistrada;
    java.math.BigDecimal iDColVar;
    
    
    /*
     * metodo para guardar la cabecera de venta
     */
    public boolean guardar(CabeceraVenta objeto) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();
        try {
             CallableStatement consulta = cn.prepareCall("EXEC sp_InsertarCabeceraVenta ?,?,?,?,?");
            consulta.setInt(1, objeto.getIdCliente());
            consulta.setDouble(2, objeto.getValorPagar());
            consulta.setString(3, objeto.getFechaVenta());
            consulta.setInt(4, objeto.getEstado());
            consulta.registerOutParameter(5, java.sql.Types.INTEGER); // Configura el parámetro OUTPUT
            consulta.execute();
        
            idCabeceraRegistrada = consulta.getInt(5); // Recupera el ID generado
            respuesta = true;

                cn.close();
        } catch (SQLException e) {
            System.out.println("Error al guardar cabecera de venta: " + e);
        }
        return respuesta;
    }
    
     /*
     * metodo para guardar el detalle de venta
     */
    public boolean guardarDetalle(DetalleVenta objeto) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();
        try {
            CallableStatement consulta = cn.prepareCall("EXEC sp_InsertarDetalleVenta ?,?,?,?,?,?,?,?,?");
            consulta.setInt(1, idCabeceraRegistrada);
            consulta.setInt(2, objeto.getIdProducto());
            consulta.setInt(3, objeto.getCantidad());   
            consulta.setDouble(4, objeto.getPrecioUnitario());
            consulta.setDouble(5, objeto.getSubTotal());
            consulta.setDouble(6, objeto.getDescuento());
            consulta.setDouble(7, objeto.getIgv());
            consulta.setDouble(8, objeto.getTotalPagar());
            consulta.setInt(9, objeto.getEstado());
            
            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }
            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al guardar detalle de venta: " + e);
        }
        return respuesta;
    }
    
    
    /*
    * metodo para actualizar un producto
    */
    public boolean actualizar(CabeceraVenta objeto, int idCabeceraVenta) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();
        try {

            PreparedStatement consulta = cn.prepareStatement(
                    "update tb_cabecera_venta set idCliente = ?, estado = ? "
                            + "where idCabeceraVenta ='" + idCabeceraVenta + "'");
            consulta.setInt(1, objeto.getIdCliente());
            consulta.setInt(2, objeto.getEstado());
           
            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }
            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al actualizar cabecera de venta: " + e);
        }
        return respuesta;
    }
}
