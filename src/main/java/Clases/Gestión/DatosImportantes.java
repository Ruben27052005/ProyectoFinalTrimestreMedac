/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.Gestión;

import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alumno
 */
public class DatosImportantes {
    public void mostrarServiciosRentables(JTable tablaServiciosRentables) {
        ConexionBD conexion = new ConexionBD();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Precio");
        modelo.addColumn("Duración");
        modelo.addColumn("Producto Específico");
        modelo.addColumn("Tipo");

        modelo.setRowCount(0);
        tablaServiciosRentables.setModel(modelo);

        String sql = "SELECT * FROM servicios ORDER BY precio DESC LIMIT 3;";

        try (Statement st = conexion.establecerConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Object[] fila = new Object[6];
                fila[0] = rs.getInt("id_servicio");
                fila[1] = rs.getString("nombre_servicio");
                fila[2] = rs.getBigDecimal("precio");
                fila[3] = rs.getString("duracion_media");
                fila[4] = rs.getBoolean("producto_especifico") ? "Sí" : "No";
                fila[5] = rs.getString("tipo");
                modelo.addRow(fila);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar servicios: " + e);
        }
    }
    
    
    public void mostrarClientesVIP(JTable tablaClientesVIP) {
    ConexionBD conexion = new ConexionBD();
    DefaultTableModel modelo = new DefaultTableModel();

    // Definimos las columnas en el orden correcto
    modelo.addColumn("Id");
    modelo.addColumn("Nombre");
    modelo.addColumn("Apellidos");
    modelo.addColumn("Email");
    modelo.addColumn("Contraseña");
    modelo.addColumn("Fecha de Alta");
    modelo.addColumn("Tipo de Cliente");
    modelo.addColumn("Teléfono");
    modelo.addColumn("Número de Visitas");

    // Limpia las filas antes de volver a cargarlas
    modelo.setRowCount(0);
    tablaClientesVIP.setModel(modelo);

    String sql = "SELECT id_cliente, nombre, apellidos, email, contrasenia, fecha_de_alta, tipo_cliente, telefono, numero_visitas FROM clientes WHERE tipo_cliente IN ('Vip', 'VIP', 'vip');";

    try (Statement st = conexion.establecerConexion().createStatement();
         ResultSet rs = st.executeQuery(sql)) {

        while (rs.next()) {
            Object[] fila = {
                rs.getInt("id_cliente"),
                rs.getString("nombre"),
                rs.getString("apellidos"),
                rs.getString("email"),
                rs.getString("contrasenia"),
                rs.getDate("fecha_de_alta"),
                rs.getString("tipo_cliente"),
                rs.getString("telefono"),
                rs.getInt("numero_visitas")
            };
            modelo.addRow(fila);
        }

        // ⚠️ Actualiza el modelo una sola vez
        tablaClientesVIP.setModel(modelo);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al mostrar los clientes: " + e.toString());
    }
   }
    
    
    public void mostrarProductos(JTable tablaProductosBajoStock) {
        ConexionBD conexion = new ConexionBD();
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Tipo");
        modelo.addColumn("Stock");
        modelo.addColumn("Proveedor");
        modelo.addColumn("Precio Unitario");

       modelo.setRowCount(0);
    tablaProductosBajoStock.setModel(modelo);

        String sql = "SELECT * FROM inventario_productos WHERE stock <=3;";
        String[] datos = new String[6];

        try (Statement st = conexion.establecerConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                datos[0] = rs.getString("id_inventario_productos");
                datos[1] = rs.getString("nombre_producto");
                datos[2] = rs.getString("tipo_producto");
                datos[3] = rs.getString("stock");
                datos[4] = rs.getString("proveedor");
                datos[5] = rs.getString("precio_unitario");
                modelo.addRow(datos);
            }

            tablaProductosBajoStock.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar productos: " + e.toString());
        }
    }
}
