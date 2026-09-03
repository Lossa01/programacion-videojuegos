import greenfoot.*;

public class DisparoSimple implements EstrategiaDisparo {
    public void disparar(Actor nave, PoolDeBalas pool) {
        Bala b = pool.obtener();
        if (b != null) {
            b.activar(nave.getWorld(), nave.getX() + 20, nave.getY(), 0);
        }
    }
}
