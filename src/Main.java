import Controlador.CompraControlador;
import Controlador.ProductoControlador;
import Controlador.VentaControlador;
import Modelo.CompraModelo;
import Modelo.ProductoModelo;
import Modelo.VentaModelo;
import Vista.CompraView;
import Vista.ProductoView;
import Vista.VentaView;

public class Main {
    public static void main(String[] args) {
        ProductoModelo modeloProducto = new ProductoModelo();
        ProductoView vistaProducto = new ProductoView();
        ProductoControlador controlador = new ProductoControlador(vistaProducto, modeloProducto);

        vistaProducto.setVisible(true);


        VentaView vistaVenta = new VentaView();
        VentaModelo modeloVenta = new VentaModelo();
        VentaControlador controladorVenta = new VentaControlador(modeloVenta, vistaVenta);

        vistaVenta.setVisible(true);

        CompraView vistaCompra = new CompraView();
        CompraModelo modeloCompra = new CompraModelo();
        CompraControlador controladorCompra = new CompraControlador(modeloCompra, vistaCompra);

        vistaCompra.setVisible(true);
    }
}


