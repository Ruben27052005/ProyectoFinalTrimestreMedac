package Clases;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Productos {

    int id;
    String nombreProducto;
    String tipoProducto;
    int stock;
    String proveedorProducto;
    Double precioUnitarioProducto;

    // ===== Getters y Setters =====
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getProveedorProducto() {
        return proveedorProducto;
    }

    public void setProveedorProducto(String proveedorProducto) {
        this.proveedorProducto = proveedorProducto;
    }

    public Double getPrecioUnitarioProducto() {
        return precioUnitarioProducto;
    }

    public void setPrecioUnitarioProducto(Double precioUnitarioProducto) {
        this.precioUnitarioProducto = precioUnitarioProducto;
    }

    // ==============================
    // ===== Mostrar Productos ======
    // ==============================
    public void mostrarProductos(JTable tablaProductos) {
        ConexionBD conexion = new ConexionBD();
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Tipo");
        modelo.addColumn("Stock");
        modelo.addColumn("Proveedor");
        modelo.addColumn("Precio Unitario");

       modelo.setRowCount(0);
    tablaProductos.setModel(modelo);

        String sql = "SELECT * FROM inventario_productos ORDER BY id_inventario_productos ASC";
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

            tablaProductos.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar productos: " + e.toString());
        }
    }

    // ============================
    // ===== Insertar Producto =====
    // ============================
 public void insertarProductos(
        JTextField nombre,
        JTextField tipo,
        JTextField stock,
        JTextField precio,
        JTextField proveedor) {

    try {
        setNombreProducto(nombre.getText());
        setTipoProducto(tipo.getText());
        setStock(Integer.parseInt(stock.getText().trim()));
        setProveedorProducto(proveedor.getText());
        setPrecioUnitarioProducto(Double.parseDouble(precio.getText().trim()));
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Stock o Precio inválido");
        return;
    }

    ConexionBD conexion = new ConexionBD();
    String sql = "INSERT INTO inventario_productos (nombre_producto, stock, precio_unitario, tipo_producto, proveedor) VALUES (?,?,?,?,?)";

    try (PreparedStatement ps = conexion.establecerConexion().prepareStatement(sql)) {
        ps.setString(1, getNombreProducto());
        ps.setInt(2, getStock());
        ps.setDouble(3, getPrecioUnitarioProducto());
        ps.setString(4, getTipoProducto());
        ps.setString(5, getProveedorProducto());
        ps.executeUpdate();

        JOptionPane.showMessageDialog(null, "Producto insertado correctamente.");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al insertar producto: " + e.toString());
    }
}



    // ============================
    // ===== Seleccionar Producto ==
    // ============================
public void seleccionarProducto(
        JTable tbListaProductos,
        javax.swing.JTextField idProducto,
        javax.swing.JTextField nombreProducto,
        javax.swing.JTextField tipoProducto,
        javax.swing.JTextField stockProducto,
        javax.swing.JTextField precioUnitProducto,
        javax.swing.JTextField proveedorProducto
) {
    try {
        int fila = tbListaProductos.getSelectedRow();

        if (fila >= 0) {
            idProducto.setText(tbListaProductos.getValueAt(fila, 0).toString());
            nombreProducto.setText(tbListaProductos.getValueAt(fila, 1).toString());
            tipoProducto.setText(tbListaProductos.getValueAt(fila, 2).toString());
            stockProducto.setText(tbListaProductos.getValueAt(fila, 3).toString());
            proveedorProducto.setText(tbListaProductos.getValueAt(fila, 4).toString());
            precioUnitProducto.setText(tbListaProductos.getValueAt(fila, 5).toString());
        } else {
            JOptionPane.showMessageDialog(null, "Fila no seleccionada");
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al seleccionar producto: " + e.toString());
    }
}


    // ============================
    // ===== Modificar Producto ===
    // ============================
    public void modificarProducto(
            JTextField id,
            JTextField nombre,
            JTextField tipo,
            JTextField stock,
            JTextField proveedor,
            JTextField precio) {

        setId(Integer.parseInt(id.getText()));
        setNombreProducto(nombre.getText());
        setTipoProducto(tipo.getText());
        setStock(Integer.parseInt(stock.getText().trim()));
        setProveedorProducto(proveedor.getText());
        setPrecioUnitarioProducto(Double.valueOf(precio.getText()));

        ConexionBD conexion = new ConexionBD();
        String sql = "UPDATE inventario_productos SET nombre_producto=?, tipo_producto=?, stock=?, proveedor=?, precio_unitario=? WHERE id_inventario_productos=?";

        try (CallableStatement cs = conexion.establecerConexion().prepareCall(sql)) {
            cs.setString(1, getNombreProducto());
            cs.setString(2, getTipoProducto());
            cs.setInt(3, getStock());
            cs.setString(4, getProveedorProducto());
            cs.setDouble(5, getPrecioUnitarioProducto());
            cs.setInt(6, getId());
            cs.execute();
            JOptionPane.showMessageDialog(null, "✅ Producto modificado correctamente.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al modificar producto: " + e.toString());
        }
    }

    // ============================
    // ===== Eliminar Producto ====
    // ============================
   public void eliminarProducto(JTextField idProducto, JTable tablaClientes) {
    ConexionBD conexion = new ConexionBD();

    String sql = "DELETE FROM inventario_productos WHERE id_inventario_productos = ?;";

    try {
        // Confirmación antes de eliminar
        int confirmacion = JOptionPane.showConfirmDialog(
                null,
                "¿Seguro que deseas eliminar este producto?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {

            // Prepara la sentencia SQL
            CallableStatement cs = conexion.establecerConexion().prepareCall(sql);

            // Convierte el texto del campo ID a entero
            int id = Integer.parseInt(idProducto.getText().trim());
            cs.setInt(1, id);
           
           

            // Ejecuta la eliminación
            cs.execute();

            JOptionPane.showMessageDialog(null, "Producto eliminado correctamente");
            
            String renumerarSQL = """
    WITH renumerados AS (
        SELECT id_inventario_productos, ROW_NUMBER() OVER (ORDER BY id_inventario_productos) AS nuevo_id
        FROM inventario_productos
    )
    UPDATE inventario_productos
    SET id_inventario_productos = renumerados.nuevo_id
    FROM renumerados
    WHERE inventario_productos.id_inventario_productos = renumerados.id_inventario_productos;
""";

String resetSecuencia = """
    SELECT setval(pg_get_serial_sequence('inventario_productos', 'id_inventario_productos'),
                  COALESCE((SELECT MAX(id_inventario_productos) FROM inventario_productos), 1));
""";

try (Statement st = conexion.establecerConexion().createStatement()) {
    st.executeUpdate(renumerarSQL);
    st.execute(resetSecuencia);
} catch (Exception e) {
    JOptionPane.showMessageDialog(null, "Error al renumerar IDs: " + e.toString());
}

            // Refresca la tabla de clientes
            mostrarProductos(tablaClientes);
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al eliminar el producto: " + e.toString());
    }
}

}

