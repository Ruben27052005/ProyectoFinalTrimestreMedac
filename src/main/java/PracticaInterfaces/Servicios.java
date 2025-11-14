package PracticaInterfaces;

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
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Precio");
        modelo.addColumn("Duración");
        modelo.addColumn("Producto Específico");
        modelo.addColumn("Tipo");

        modelo.setRowCount(0);
        tablaServicios.setModel(modelo);

        String sql = "SELECT * FROM servicios ORDER BY id_servicio ASC";

        try (Statement st = conexion.establecerConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Object[] fila = new Object[6];
                fila[0] = rs.getInt("id_servicio");
                fila[1] = rs.getString("nombre");
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
        String sql = "INSERT INTO servicios (nombre, precio, duracion_media, producto_especifico, tipo) VALUES (?,?,?,?,?)";

        try (PreparedStatement ps = conexion.establecerConexion().prepareStatement(sql)) {
            ps.setString(1, nombreField.getText().trim());
            ps.setBigDecimal(2, new BigDecimal(precioField.getText().trim()));
            ps.setString(3, duracionField.getText().trim());
            ps.setBoolean(4, productoCombo.getSelectedItem().toString().equalsIgnoreCase("Sí"));
            ps.setString(5, tipoField.getText().trim());

            ps.executeUpdate();

            // Renumerar IDs después de insertar
            renumerarIDs();

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
        String sql = "UPDATE servicios SET nombre=?, precio=?, duracion_media=?, producto_especifico=?, tipo=? WHERE id_servicio=?";

        try (PreparedStatement ps = conexion.establecerConexion().prepareStatement(sql)) {
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
    public void eliminarServicio(JTextField idField, JTable tablaServicios) {
        ConexionBD conexion = new ConexionBD();
        String sql = "DELETE FROM servicios WHERE id_servicio=?";

        try {
            int confirmacion = JOptionPane.showConfirmDialog(null,
                    "¿Seguro que deseas eliminar este servicio?",
                    "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                PreparedStatement ps = conexion.establecerConexion().prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(idField.getText().trim()));
                ps.executeUpdate();

                // Renumerar IDs después de eliminar
                renumerarIDs();

                JOptionPane.showMessageDialog(null, "Servicio eliminado correctamente");
                mostrarServicios(tablaServicios);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar servicio: " + e);
        }
    }

    // ==========================
    // ===== Renumerar IDs ======
    // ==========================
    private void renumerarIDs() {
        ConexionBD conexion = new ConexionBD();
        String renumerarSQL = """
            WITH renumerados AS (
                SELECT id_servicio, ROW_NUMBER() OVER (ORDER BY id_servicio) AS nuevo_id
                FROM servicios
            )
            UPDATE servicios
            SET id_servicio = renumerados.nuevo_id
            FROM renumerados
            WHERE servicios.id_servicio = renumerados.id_servicio;
        """;

        String resetSecuencia = """
            SELECT setval(pg_get_serial_sequence('servicios', 'id_servicio'),
                          COALESCE((SELECT MAX(id_servicio) FROM servicios), 1));
        """;

        try (Statement st = conexion.establecerConexion().createStatement()) {
            st.executeUpdate(renumerarSQL);
            st.execute(resetSecuencia);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al renumerar IDs: " + e);
        }
    }
}
