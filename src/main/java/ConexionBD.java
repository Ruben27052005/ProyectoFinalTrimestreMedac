/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author alumno
 */
public class ConexionBD {
    
    public static Connection conexionBD(){
        Connection con = null;
        try{
           Class.forName("org.postgresql.Driver");
           con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Peluqueria", "postgres", "postgres");
        }catch(Exception e){
            System.out.println("Error de conexión; " + e.getMessage());
        }
        return con;
    }
}
