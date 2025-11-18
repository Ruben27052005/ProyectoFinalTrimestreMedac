/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.Gestión;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author alumno
 */
public class ConexionBD {
 
    Connection conectar = null;
    String usuario = "postgres";
    String contraseña = "postgres";
    String bd = "BDpeluqueriafinal";
    String ip = "localhost";
    String puerto = "5432";
   
    String cadena = "jdbc:postgresql://"+ip+":"+puerto+"/"+bd;
   
    public Connection establecerConexion(){
       
        try {
            Class.forName("org.postgresql.Driver");
           
            conectar= DriverManager.getConnection(cadena,usuario,contraseña);
           
            System.out.println("Se conecto correctamente a la base de datos");
           
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos"+ e.toString());
        }
        return conectar;
    }

}

