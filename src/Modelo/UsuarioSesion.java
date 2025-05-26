// UsuarioSesion.java
package Modelo;

public class UsuarioSesion {
    private static int posicion; // 1 = admin, 2 = empleado

    public static int getPosicion() {
        return posicion;
    }

    public static void setPosicion(int pos) {
        posicion = pos;
    }
}

