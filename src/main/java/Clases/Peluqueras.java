package Clases;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Peluqueras {

    int id;
    String nombrePeluquera;
    String apellidosPeluquera;
    String estadoPeluquera;
    int anios_experiencia;
    String emailPeluquera;
    String contraseñaPeluquera;
    String especialidadPeluquera;

    // ===== Getters y Setters =====
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombrePeluquera() { return nombrePeluquera; }
    public void setNombrePeluquera(String nombrePeluquera) { this.nombrePeluquera = nombrePeluquera; }
    public String getApellidosPeluquera() { return apellidosPeluquera; }
    public void setApellidosPeluquera(String apellidosPeluquera) { this.apellidosPeluquera = apellidosPeluquera; }
    public String getEstadoPeluquera() { return estadoPeluquera; }
    public void setEstadoPeluquera(String estadoPeluquera) { this.estadoPeluquera = estadoPeluquera; }
    public int getAnios_experiencia() { return anios_experiencia; }
    public void setAnios_experiencia(int anios_experiencia) { this.anios_experiencia = anios_experiencia; }
    public String getEmailPeluquera() { return emailPeluquera; }
    public void setEmailPeluquera(String emailPeluquera) { this.emailPeluquera = emailPeluquera; }
    public String getContraseñaPeluquera() { return contraseñaPeluquera; }
    public void setContraseñaPeluquera(String contraseñaPeluquera) { this.contraseñaPeluquera = contraseñaPeluquera; }
    public String getEspecialidadPeluquera() { return especialidadPeluquera; }
    public void setEspecialidadPeluquera(String especialidadPeluquera) { this.especialidadPeluquera = especialidadPeluquera; }

    // ===== Mostrar Peluqueras =====
    public void mostrarPeluqueras(JTable tabla) {
        ConexionBD conexion = new ConexionBD();
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellidos");
        modelo.addColumn("Correo Electrónico");
        modelo.addColumn("Contraseña");
        modelo.addColumn("Estado");
        modelo.addColumn("Especialidad");
        modelo.addColumn("Años Experiencia");

        modelo.setRowCount(0);
        tabla.setModel(modelo);

        String sql = "SELECT * FROM peluqueras ORDER BY id_peluquera ASC";

        try (Statement st = conexion.establecerConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("id_peluquera"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getString("correo_electronico"),
                    rs.getString("contrasenia"),
                    rs.getString("estado"),
                    rs.getString("especialidad"),
                    rs.getInt("anios_experiencia")
                };
                modelo.addRow(fila);
            }

            tabla.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar peluqueras: " + e.getMessage());
        }
    }

    // ===== Seleccionar Peluquera =====
    public void seleccionarPeluquera(JTable tabla, JTextField id, JTextField nombre,
                                     JTextField apellidos, JTextField email, JTextField contrasenia,
                                     JComboBox<String> estado, JTextField especialidad, JTextField aniosExp) {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            id.setText(tabla.getValueAt(fila, 0).toString());
            nombre.setText(tabla.getValueAt(fila, 1).toString());
            apellidos.setText(tabla.getValueAt(fila, 2).toString());
            email.setText(tabla.getValueAt(fila, 3).toString());
            contrasenia.setText(tabla.getValueAt(fila, 4).toString());
            estado.setSelectedItem(tabla.getValueAt(fila, 5).toString());
            especialidad.setText(tabla.getValueAt(fila, 6).toString());
            aniosExp.setText(tabla.getValueAt(fila, 7).toString());
        }
    }

    // ===== Insertar Peluquera =====
    public void insertarPeluquera(JTextField nombre, JTextField apellidos, JTextField email,
                                  JTextField contrasenia, JComboBox<String> estado, JTextField especialidad,
                                  JTextField aniosExp, JTable tabla) {
        setNombrePeluquera(nombre.getText());
        setApellidosPeluquera(apellidos.getText());
        setEmailPeluquera(email.getText());
        setContraseñaPeluquera(contrasenia.getText());
        setEstadoPeluquera(estado.getSelectedItem().toString());
        setEspecialidadPeluquera(especialidad.getText());
        setAnios_experiencia(Integer.parseInt(aniosExp.getText().trim()));

        ConexionBD conexion = new ConexionBD();
        String sql = "INSERT INTO peluqueras (nombre, apellidos, correo_electronico, contrasenia, estado, especialidad, anios_experiencia) VALUES (?,?,?,?,?,?,?)";

        try (PreparedStatement ps = conexion.establecerConexion().prepareStatement(sql)) {
            ps.setString(1, getNombrePeluquera());
            ps.setString(2, getApellidosPeluquera());
            ps.setString(3, getEmailPeluquera());
            ps.setString(4, getContraseñaPeluquera());
            ps.setString(5, getEstadoPeluquera());
            ps.setString(6, getEspecialidadPeluquera());
            ps.setInt(7, getAnios_experiencia());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Peluquera insertada correctamente");

            // Renumerar IDs
            renumerarIDs(conexion);
            mostrarPeluqueras(tabla);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al insertar peluquera: " + e.getMessage());
        }
    }

    // ===== Modificar Peluquera =====
    public void modificarPeluquera(JTextField id, JTextField nombre, JTextField apellidos, JTextField email,
                                   JTextField contrasenia, JComboBox<String> estado, JTextField especialidad,
                                   JTextField aniosExp, JTable tabla) {
        setId(Integer.parseInt(id.getText()));
        setNombrePeluquera(nombre.getText());
        setApellidosPeluquera(apellidos.getText());
        setEmailPeluquera(email.getText());
        setContraseñaPeluquera(contrasenia.getText());
        setEstadoPeluquera(estado.getSelectedItem().toString());
        setEspecialidadPeluquera(especialidad.getText());
        setAnios_experiencia(Integer.parseInt(aniosExp.getText().trim()));

        ConexionBD conexion = new ConexionBD();
        String sql = "UPDATE peluqueras SET nombre=?, apellidos=?, correo_electronico=?, contrasenia=?, estado=?, especialidad=?, anios_experiencia=? WHERE id_peluquera=?";

        try (PreparedStatement ps = conexion.establecerConexion().prepareStatement(sql)) {
            ps.setString(1, getNombrePeluquera());
            ps.setString(2, getApellidosPeluquera());
            ps.setString(3, getEmailPeluquera());
            ps.setString(4, getContraseñaPeluquera());
            ps.setString(5, getEstadoPeluquera());
            ps.setString(6, getEspecialidadPeluquera());
            ps.setInt(7, getAnios_experiencia());
            ps.setInt(8, getId());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Peluquera modificada correctamente");
            mostrarPeluqueras(tabla);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al modificar peluquera: " + e.getMessage());
        }
    }

    // ===== Eliminar Peluquera =====
    public void eliminarPeluquera(JTextField id, JTable tabla) {
        ConexionBD conexion = new ConexionBD();
        String sql = "DELETE FROM peluqueras WHERE id_peluquera=?";

        try {
            int confirm = JOptionPane.showConfirmDialog(null, "¿Seguro que deseas eliminar esta peluquera?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                PreparedStatement ps = conexion.establecerConexion().prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(id.getText().trim()));
                ps.executeUpdate();

                renumerarIDs(conexion);
                mostrarPeluqueras(tabla);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar peluquera: " + e.getMessage());
        }
    }

    // ===== Renumerar IDs =====
    private void renumerarIDs(ConexionBD conexion) {
        String renumerarSQL = """
            WITH renumerados AS (
                SELECT id_peluquera, ROW_NUMBER() OVER (ORDER BY id_peluquera) AS nuevo_id
                FROM peluqueras
            )
            UPDATE peluqueras
            SET id_peluquera = renumerados.nuevo_id
            FROM renumerados
            WHERE peluqueras.id_peluquera = renumerados.id_peluquera;
        """;

        String resetSecuencia = """
            SELECT setval(pg_get_serial_sequence('peluqueras', 'id_peluquera'),
                          COALESCE((SELECT MAX(id_peluquera) FROM peluqueras), 1));
        """;

        try (Statement st = conexion.establecerConexion().createStatement()) {
            st.executeUpdate(renumerarSQL);
            st.execute(resetSecuencia);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al renumerar IDs: " + e.getMessage());
        }
    }
}
