import greenfoot.*;

public class Avanzar implements Estado {
    private int pasos = 0;

    public Estado actuar(Enemigo enemigo) {
        enemigo.setLocation(enemigo.getX() - 2, enemigo.getY());
        pasos++;
        if (pasos > 40) {
            return new Zigzag(); // tras un rato, cambia de patrón
        }
        return this;
    }
}