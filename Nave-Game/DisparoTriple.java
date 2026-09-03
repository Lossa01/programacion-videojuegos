import greenfoot.*;

public class DisparoTriple implements EstrategiaDisparo {
    public void disparar(Actor nave, PoolDeBalas pool) {
        int[] angulos = {-15, 0, 15}; // abanico de tres balas
        for (int ang : angulos) {
            Bala b = pool.obtener();
            if (b != null) {
                b.activar(nave.getWorld(), nave.getX() + 20, nave.getY(), ang);
            }
        }
    }
}