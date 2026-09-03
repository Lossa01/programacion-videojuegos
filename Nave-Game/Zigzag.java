import greenfoot.*;

public class Zigzag implements Estado {
    private int direccion = 3;
    private int pasos = 0;

    public Estado actuar(Enemigo enemigo) {
        enemigo.setLocation(enemigo.getX() - 2,
                             enemigo.getY() + direccion * 3);
        pasos++;
        if (pasos % 15 == 0) {
            direccion = -direccion; // invierte el zigzag
        }
        return this;
    }
}
