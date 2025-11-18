package Clases;

import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ControlDePantalla {

    private static final Color COLOR_FONDO_CLARO = Color.WHITE;
    private static final Color COLOR_TEXTO_CLARO = Color.BLACK;
    private static final Color COLOR_FONDO_OSCURO = new Color(45, 45, 45);
    private static final Color COLOR_TEXTO_OSCURO = Color.WHITE;

    public static void aplicarModoOscuro(
            boolean modoOscuro,
            JPanel[] paneles,
            JTextField[] camposTexto,
            JTable tabla,
            JComboBox<String> comboBox
    ) {
        Color fondo = modoOscuro ? COLOR_FONDO_OSCURO : COLOR_FONDO_CLARO;
        Color texto = modoOscuro ? COLOR_TEXTO_OSCURO : COLOR_TEXTO_CLARO;

        // Cambiar colores de los paneles
        for (JPanel panel : paneles) {
            panel.setBackground(fondo);
        }

        // Cambiar colores de los campos de texto
        for (JTextField campo : camposTexto) {
            campo.setBackground(fondo);
            campo.setForeground(texto);
        }

        // Cambiar colores de la tabla
        tabla.setBackground(fondo);
        tabla.setForeground(texto);

        // Cambiar colores del ComboBox
        comboBox.setBackground(fondo);
        comboBox.setForeground(texto);
    }
}
