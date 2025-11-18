/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alumno
 */
public class Clientes{

          int id;
    String nombreCliente;
    String apellidosCliente;
    String tipoCliente;
    String fechaAlta;
    String telefonoCliente;
    String estadoCliente;
    String contraseñaCliente;
    int numeroVisitas;
    String emailCliente;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidosCliente() {
        return apellidosCliente;
    }

    public void setApellidosCliente(String apellidosCliente) {
        this.apellidosCliente = apellidosCliente;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public String getEstadoCliente() {
        return estadoCliente;
    }

    public void setEstadoCliente(String estadoCliente) {
        this.estadoCliente = estadoCliente;
    }

    public String getContraseñaCliente() {
        return contraseñaCliente;
    }

    public void setContraseñaCliente(String contraseñaCliente) {
        this.contraseñaCliente = contraseñaCliente;
    }

    public int getNumeroVisitas() {
        return numeroVisitas;
    }

    public void setNumeroVisitas(int numeroVisitas) {
        this.numeroVisitas = numeroVisitas;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }
    
 
    
    
    
   public void mostrarClientes(JTable tablaClientes) {
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
    tablaClientes.setModel(modelo);

    String sql = "SELECT id_cliente, nombre, apellidos, email, contrasenia, fecha_de_alta, tipo_cliente, telefono, numero_visitas FROM clientes ORDER BY id_cliente ASC";

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
        tablaClientes.setModel(modelo);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al mostrar los clientes: " + e.toString());
    }
}

    
    public void insertarCliente(
        JTextField nombre,
        JTextField apellidos,
        JTextField email,
        JTextField contrasenia,
        JTextField tipo,
        JTextField telefono,
        JTextField numeroVisitas) {

    setNombreCliente(nombre.getText());
    setApellidosCliente(apellidos.getText());
    setEmailCliente(email.getText());
    setContraseñaCliente(contrasenia.getText());
    setTipoCliente(tipo.getText());
    setTelefonoCliente(telefono.getText());
    setNumeroVisitas(Integer.parseInt(numeroVisitas.getText().trim()));

    ConexionBD conexion = new ConexionBD();

    String sql = "INSERT INTO clientes (nombre, apellidos, email, contrasenia, tipo_cliente, telefono, numero_visitas) VALUES (?,?,?,?,?,?,?)";

    try (CallableStatement cs = conexion.establecerConexion().prepareCall(sql)) {
        cs.setString(1, getNombreCliente());
        cs.setString(2, getApellidosCliente());
        cs.setString(3, getEmailCliente());
        cs.setString(4, getContraseñaCliente());
        cs.setString(5, getTipoCliente());
        cs.setString(6, getTelefonoCliente());
        cs.setInt(7, getNumeroVisitas());

        cs.execute();
        JOptionPane.showMessageDialog(null, "Cliente insertado correctamente");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al insertar el cliente: " + e);
    }
}

    
  public void seleccionarCliente(
        JTable tablaClientes,
        JTextField id,
        JTextField nombre,
        JTextField apellidos,
        JTextField email,
        JTextField contrasenia,
        JTextField fechaAlta,
        JTextField tipo,
        JTextField telefono,
        JTextField numeroVisitas) {

    try {
        int fila = tablaClientes.getSelectedRow();

        if (fila >= 0) {
            id.setText(tablaClientes.getValueAt(fila, 0).toString());
            nombre.setText(tablaClientes.getValueAt(fila, 1).toString());
            apellidos.setText(tablaClientes.getValueAt(fila, 2).toString());
            email.setText(tablaClientes.getValueAt(fila, 3).toString());
            contrasenia.setText(tablaClientes.getValueAt(fila, 4).toString());
            fechaAlta.setText(tablaClientes.getValueAt(fila, 5).toString());
            tipo.setText(tablaClientes.getValueAt(fila, 6).toString());
            telefono.setText(tablaClientes.getValueAt(fila, 7).toString());
            numeroVisitas.setText(tablaClientes.getValueAt(fila, 8).toString());
        } else {
            JOptionPane.showMessageDialog(null, "Fila no seleccionada");
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al seleccionar el cliente: " + e);
    }
}

   public void modificarCliente(
        JTextField id,
        JTextField nombre,
        JTextField apellidos,
        JTextField email,
        JTextField contrasenia,
        JTextField tipo,
        JTextField telefono,
        JTextField numeroVisitas) {

    setId(Integer.parseInt(id.getText()));
    setNombreCliente(nombre.getText());
    setApellidosCliente(apellidos.getText());
    setEmailCliente(email.getText());
    setContraseñaCliente(contrasenia.getText());
    setTipoCliente(tipo.getText());
    setTelefonoCliente(telefono.getText());
    setNumeroVisitas(Integer.parseInt(numeroVisitas.getText().trim()));

    ConexionBD conexion = new ConexionBD();

    String sql = "UPDATE clientes SET nombre=?, apellidos=?, email=?, contrasenia=?, tipo_cliente=?, telefono=?, numero_visitas=? WHERE id_cliente=?";

    try (CallableStatement cs = conexion.establecerConexion().prepareCall(sql)) {
        cs.setString(1, getNombreCliente());
        cs.setString(2, getApellidosCliente());
        cs.setString(3, getEmailCliente());
        cs.setString(4, getContraseñaCliente());
        cs.setString(5, getTipoCliente());
        cs.setString(6, getTelefonoCliente());
        cs.setInt(7, getNumeroVisitas());
        cs.setInt(8, getId());

        cs.execute();
        JOptionPane.showMessageDialog(null, "Cliente modificado correctamente");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al modificar el cliente: " + e);
    }
}
  
  public void eliminarCliente(JTextField id_cliente, JTable tablaClientes) {
    ConexionBD conexion = new ConexionBD();

    String sql = "DELETE FROM clientes WHERE id_cliente = ?;";

    try {
        // Confirmación antes de eliminar
        int confirmacion = JOptionPane.showConfirmDialog(
                null,
                "¿Seguro que deseas eliminar este cliente?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {

            // Prepara la sentencia SQL
            CallableStatement cs = conexion.establecerConexion().prepareCall(sql);

            // Convierte el texto del campo ID a entero
            int id = Integer.parseInt(id_cliente.getText().trim());
            cs.setInt(1, id);

            // Ejecuta la eliminación
            cs.execute();

            JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente");
            
            String renumerarSQL = """
    WITH renumerados AS (
        SELECT id_cliente, ROW_NUMBER() OVER (ORDER BY id_cliente) AS nuevo_id
        FROM clientes
    )
    UPDATE clientes
    SET id_cliente = renumerados.nuevo_id
    FROM renumerados
    WHERE clientes.id_cliente = renumerados.id_cliente;
""";

String resetSecuencia = """
    SELECT setval(pg_get_serial_sequence('clientes', 'id_cliente'),
                  COALESCE((SELECT MAX(id_cliente) FROM clientes), 1));
""";

try (Statement st = conexion.establecerConexion().createStatement()) {
    st.executeUpdate(renumerarSQL);
    st.execute(resetSecuencia);
} catch (Exception e) {
    JOptionPane.showMessageDialog(null, "Error al renumerar IDs: " + e.toString());
}

            // Refresca la tabla de clientes
            mostrarClientes(tablaClientes);
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al eliminar el cliente: " + e.toString());
    }
}
public void mostrarClientesFiltrados(JTable tablaClientes, String filtro) {
    ConexionBD conexion = new ConexionBD();
    DefaultTableModel modelo = new DefaultTableModel();

    modelo.addColumn("Id");
    modelo.addColumn("Nombre");
    modelo.addColumn("Apellidos");
    modelo.addColumn("Email");
    modelo.addColumn("Contraseña");
    modelo.addColumn("Fecha de Alta");
    modelo.addColumn("Tipo de Cliente");
    modelo.addColumn("Teléfono");
    modelo.addColumn("Número de Visitas");

    tablaClientes.setModel(modelo);

    String sql = "SELECT id_cliente, nombre, apellidos, email, contrasenia, fecha_de_alta, tipo_cliente, telefono, numero_visitas FROM clientes";

    // Aplica filtro si es VIP o No VIP
    if (filtro.equalsIgnoreCase("VIP")) {
        sql += " WHERE tipo_cliente = 'VIP'";
    } else if (filtro.equalsIgnoreCase("No VIP")) {
        sql += " WHERE tipo_cliente <> 'VIP'";
    }

    sql += " ORDER BY id_cliente ASC";

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

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al mostrar clientes filtrados: " + e.toString());
    }
}

}

