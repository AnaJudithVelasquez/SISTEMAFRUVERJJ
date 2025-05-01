package Modelo;

public class ProductoDetalle {
    private String codProducto;
    private String nombreProducto;
    private String cantidadKg;
    private String precioProducto;
    private String totalProducto;

    public ProductoDetalle(String codProducto, String nombreProducto, String cantidadKg, String precioProducto, String totalProducto) {
        this.codProducto = codProducto;
        this.nombreProducto = nombreProducto;
        this.cantidadKg = cantidadKg;
        this.precioProducto = precioProducto;
        this.totalProducto = totalProducto;
    }

    // Getters
    public String getCodProducto() { return codProducto; }
    public String getNombreProducto() { return nombreProducto; }
    public String getCantidadKg() { return cantidadKg; }
    public String getPrecioProducto() { return precioProducto; }
    public String getTotalProducto() { return totalProducto; }
}

