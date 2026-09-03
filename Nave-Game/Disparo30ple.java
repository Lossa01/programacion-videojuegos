import greenfoot.*;

public class Disparo30ple implements EstrategiaDisparo {
    public void disparar(Actor nave, PoolDeBalas pool) {
        int cantidad = 30;

        for (int i = 0; i < cantidad; i++) {
            int ang = i * (360 / cantidad); // reparte las 30 balas en círculo completo
            Bala b = pool.obtener();
            if (b != null) {
                b.activar(nave.getWorld(), nave.getX(), nave.getY(), ang);
            }
        }
    }
}
