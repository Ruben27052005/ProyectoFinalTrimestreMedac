package Clases.Gestión;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.swing.JTable;
import javax.swing.JOptionPane;

public class ExportarPDF {

    // ============================
    // MÉTODO 1: EXPORTAR UNA TABLA
    // ============================
    public void exportarTablaPDF(JTable tbListaAlumnos, String nombreArchivo) {
        Document documento = new Document();

        try {
            PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));
            documento.open();

            documento.add(new Paragraph("Lista de Alumnos\n\n"));

            PdfPTable tablaPDF = convertirTabla(tbListaAlumnos);

            documento.add(tablaPDF);

            JOptionPane.showMessageDialog(null, "PDF creado exitosamente en: " + nombreArchivo);

        } catch (FileNotFoundException | DocumentException e) {
            JOptionPane.showMessageDialog(null, "Error al crear el PDF: " + e.getMessage());
        } finally {
            documento.close();
        }
    }

    // ============================================
    // MÉTODO 2: EXPORTAR TRES TABLAS EN UN PDF
    // ============================================
    public void exportarMultiplesTablasPDF(JTable tabla1, JTable tabla2, JTable tabla3, String nombreArchivo) {
        Document documento = new Document();

        try {
            PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));
            documento.open();

            documento.add(new Paragraph("REPORTES - DATOS IMPORTANTES\n\n"));

            // === Tabla 1 ===
            documento.add(new Paragraph("Clientes VIP\n\n"));
            documento.add(convertirTabla(tabla1));
            documento.add(new Paragraph("\n--------------------------------------\n\n"));

            // === Tabla 2 ===
            documento.add(new Paragraph("Productos Bajo Stock\n\n"));
            documento.add(convertirTabla(tabla2));
            documento.add(new Paragraph("\n--------------------------------------\n\n"));

            // === Tabla 3 ===
            documento.add(new Paragraph("Servicios Rentables\n\n"));
            documento.add(convertirTabla(tabla3));

            JOptionPane.showMessageDialog(null, "PDF creado exitosamente!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar PDF: " + e.getMessage());
        } finally {
            documento.close();
        }
    }

    // ===========================================================
    // MÉTODO AUXILIAR: CONVERTIR UN JTable A PdfPTable (ARREGLADO)
    // ===========================================================
    private PdfPTable convertirTabla(JTable tabla) {
        int columnas = tabla.getColumnCount();

        // Evitar error si la tabla no tiene columnas
        if (columnas <= 0) {
            PdfPTable vacia = new PdfPTable(1);
            vacia.addCell("No hay columnas");
            return vacia;
        }

        PdfPTable tablaPDF = new PdfPTable(columnas);
        tablaPDF.setWidthPercentage(100f);

        // Formatos
        Font encabezado = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
        Font celda = FontFactory.getFont(FontFactory.HELVETICA, 9);

        try {
            // Encabezados
            for (int i = 0; i < columnas; i++) {
                String nombreCol = tabla.getColumnName(i);
                PdfPCell celdaEnc = new PdfPCell(new Phrase(nombreCol != null ? nombreCol : "", encabezado));
                celdaEnc.setHorizontalAlignment(Element.ALIGN_CENTER);
                tablaPDF.addCell(celdaEnc);
            }

            // Filas
            for (int r = 0; r < tabla.getRowCount(); r++) {
                for (int c = 0; c < columnas; c++) {
                    Object valor = tabla.getValueAt(r, c);
                    PdfPCell celdapdf = new PdfPCell(new Phrase(
                            valor == null ? "" : valor.toString(),
                            celda
                    ));
                    celdapdf.setHorizontalAlignment(Element.ALIGN_LEFT);
                    tablaPDF.addCell(celdapdf);
                }
            }

        } catch (Exception ex) {
            PdfPTable error = new PdfPTable(1);
            error.addCell("Error al convertir tabla: " + ex.getMessage());
            return error;
        }

        return tablaPDF;
    }
}
