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
        ProductoModelo modelo = new ProductoModelo();
        ProductoView vista = new ProductoView();
        ProductoControlador controlador = new ProductoControlador(modelo, vista);

        vista.setVisible(true);


        VentaView vistaVenta = new VentaView();
        VentaModelo modeloVenta = new VentaModelo();
        VentaControlador controladorVenta = new VentaControlador(vistaVenta, modeloVenta);

        vista.setVisible(true);

        CompraView vistaCompra = new CompraView();
        CompraModelo modeloCompra = new CompraModelo();
        CompraControlador controladorCompra = new VentaControlador(vistaCompra, modeloCompra);
    }
}


