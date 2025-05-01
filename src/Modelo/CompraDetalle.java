package Modelo;

public class CompraDetalle {
    private String producto;
    private String valorUnitario;
    private String cantidadKg;
    private String totalProducto;

    public CompraDetalle(String producto, String valorUnitario, String cantidadKg, String totalProducto) {
        this.producto = producto;
        this.valorUnitario = valorUnitario;
        this.cantidadKg = cantidadKg;
        this.totalProducto = totalProducto;
    }

    public String getProducto() {
        return producto;
    }

    public String getValorUnitario() {
        return valorUnitario;
    }

    public String getCantidadKg() {
        return cantidadKg;
    }

    public String getTotalProducto() {
        return totalProducto;
    }
}

