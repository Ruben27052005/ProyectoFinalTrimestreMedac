package PracticaInterfaces;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login {
    // Referencias a las ventanas
 
   
    private String correo;
    private String contraseña;

    // Getters y Setters
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    // Método para iniciar sesión
    public void iniciarSesion(VLoginPeluqueras ventanaLogin, JTextField txtcorreo, JPasswordField txtcontraseña) {
    setCorreo(txtcorreo.getText().trim());
    setContraseña(new String(txtcontraseña.getPassword()).trim());

    ConexionBD conexion = new ConexionBD();
    String sql = "SELECT nombre, apellidos FROM peluqueras WHERE correo_electronico=? AND contrasenia=?";

    try (PreparedStatement ps = conexion.establecerConexion().prepareStatement(sql)) {
        ps.setString(1, getCorreo());
        ps.setString(2, getContraseña());
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            String nombre = rs.getString("nombre");
            String apellido = rs.getString("apellidos");
            JOptionPane.showMessageDialog(null,
                    "Sesión iniciada con éxito\nBienvenido: " + nombre + " " + apellido);

            // cerrar la ventana actual
            ventanaLogin.dispose();
           
            // abrir la siguiente
                VMenu panelInicio = new VMenu();
                panelInicio.setVisible(true);
           
        } else {
            JOptionPane.showMessageDialog(null, "Correo o contraseña incorrectos");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al iniciar sesión: " + e.getMessage());
    }
}

    

    

 

}