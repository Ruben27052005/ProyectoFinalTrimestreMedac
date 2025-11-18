package Clases;

import java.math.BigDecimal;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Servicios {

    int id;
    String nombre;
    BigDecimal precio;
    String duracionMedia;
    boolean productoEspecifico;
    String tipo;

    // ==========================
    // ===== Getters/Setters ====
    // ==========================
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public String getDuracionMedia() { return duracionMedia; }
    public void setDuracionMedia(String duracionMedia) { this.duracionMedia = duracionMedia; }
    public boolean isProductoEspecifico() { return productoEspecifico; }
    public void setProductoEspecifico(boolean productoEspecifico) { this.productoEspecifico = productoEspecifico; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    // ==========================
    // ===== Mostrar Servicios ===
    // ==========================
   public void mostrarServicios(JTable tablaServicios) {

    ConexionBD conexion = new ConexionBD();
    Connection con = null;
    Statement st = null;
    ResultSet rs = null;

    DefaultTableModel modelo = new DefaultTableModel();
    modelo.addColumn("ID");
    modelo.addColumn("Nombre");
    modelo.addColumn("Precio");
    modelo.addColumn("Duración");
    modelo.addColumn("Producto Específico");
    modelo.addColumn("Tipo");

    tablaServicios.setModel(modelo);

    String sql = "SELECT * FROM servicios ORDER BY id_servicio ASC";

    try {
        con = conexion.establecerConexion();  // una sola conexión
        st = con.createStatement();
        rs = st.executeQuery(sql);

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



    // ==========================
    // ===== Seleccionar Servicio
    // ==========================
    public void seleccionarServicio(JTable tabla, JTextField idField, JTextField nombreField,
                                    JTextField precioField, JTextField duracionField,
                                    JComboBox<String> productoCombo, JTextField tipoField) {
        try {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                idField.setText(tabla.getValueAt(fila, 0).toString());
                nombreField.setText(tabla.getValueAt(fila, 1).toString());
                precioField.setText(tabla.getValueAt(fila, 2).toString());
                duracionField.setText(tabla.getValueAt(fila, 3).toString());
                productoCombo.setSelectedItem(tabla.getValueAt(fila, 4).toString());
                tipoField.setText(tabla.getValueAt(fila, 5).toString());
            } else {
                JOptionPane.showMessageDialog(null, "Fila no seleccionada");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al seleccionar servicio: " + e);
        }
    }

    // ==========================
    // ===== Insertar Servicio ===
    // ==========================
   public void insertarServicio(JTextField nombreField, JTextField precioField,
                             JTextField duracionField, JComboBox<String> productoCombo,
                             JTextField tipoField, JTable tablaServicios) {

    ConexionBD conexion = new ConexionBD();
    Connection con = null;
    PreparedStatement ps = null;

    String sql = "INSERT INTO servicios (nombre_servicio, precio, duracion_media, producto_especifico, tipo) VALUES (?,?,?,?,?)";

    try {
        con = conexion.establecerConexion();  
        con.setAutoCommit(true);  // <<------ MUY IMPORTANTE

        ps = con.prepareStatement(sql);
        ps.setString(1, nombreField.getText().trim());
        ps.setBigDecimal(2, new BigDecimal(precioField.getText().trim()));
        ps.setString(3, duracionField.getText().trim());
        ps.setBoolean(4, productoCombo.getSelectedItem().toString().equalsIgnoreCase("Sí"));
        ps.setString(5, tipoField.getText().trim());

        ps.executeUpdate();

        JOptionPane.showMessageDialog(null, "Servicio insertado correctamente");
        mostrarServicios(tablaServicios);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al insertar servicio: " + e);
    }
}

    // ==========================
    // ===== Modificar Servicio ==
    // ==========================
    public void modificarServicio(JTextField idField, JTextField nombreField, JTextField precioField,
                              JTextField duracionField, JComboBox<String> productoCombo,
                              JTextField tipoField, JTable tablaServicios) {

    ConexionBD conexion = new ConexionBD();
    Connection con = null;
    PreparedStatement ps = null;

    String sql = "UPDATE servicios SET nombre_servicio=?, precio=?, duracion_media=?, producto_especifico=?, tipo=? WHERE id_servicio=?";

    try {
        con = conexion.establecerConexion();
        con.setAutoCommit(true);  // <<---- OBLIGATORIO

        ps = con.prepareStatement(sql);

        ps.setString(1, nombreField.getText().trim());
        ps.setBigDecimal(2, new BigDecimal(precioField.getText().trim()));
        ps.setString(3, duracionField.getText().trim());
        ps.setBoolean(4, productoCombo.getSelectedItem().toString().equalsIgnoreCase("Sí"));
        ps.setString(5, tipoField.getText().trim());
        ps.setInt(6, Integer.parseInt(idField.getText().trim()));

        ps.executeUpdate();

        JOptionPane.showMessageDialog(null, "Servicio modificado correctamente");
        mostrarServicios(tablaServicios);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al modificar servicio: " + e);
    }
}



    // ==========================
    // ===== Eliminar Servicio ===
    // ==========================
 public void eliminarServicio(JTable tablaServicios) {

    int fila = tablaServicios.getSelectedRow();
    if (fila < 0) {
        JOptionPane.showMessageDialog(null, "Selecciona un servicio primero.");
        return;
    }

    // Obtener el ID de la fila seleccionada
    String idText = tablaServicios.getValueAt(fila, 0).toString().trim();
    int id;
    try {
        id = Integer.parseInt(idText);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "ID inválido: " + idText);
        return;
    }

    // Confirmación de borrado
    int confirmacion = JOptionPane.showConfirmDialog(
            null,
            "¿Seguro que deseas eliminar este servicio?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION
    );
    if (confirmacion != JOptionPane.YES_OPTION) {
        return;
    }

    ConexionBD conexion = new ConexionBD();

    try (Connection con = conexion.establecerConexion();
         PreparedStatement ps = con.prepareStatement("DELETE FROM servicios WHERE id_servicio=?")) {

        ps.setInt(1, id);
        ps.executeUpdate();

        JOptionPane.showMessageDialog(null, "Servicio eliminado correctamente.");

        // Actualizar JTable
        mostrarServicios(tablaServicios);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al eliminar servicio: " + e);
    }
}




}