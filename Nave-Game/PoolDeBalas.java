import java.util.*;

public class PoolDeBalas {
    private List<Bala> balas = new ArrayList<>();

    public PoolDeBalas(int tamanio) {
        for (int i = 0; i < tamanio; i++) {
            balas.add(new Bala()); // se crean todas UNA sola vez
        }
    }

    // Devuelve una bala libre, o null si todas están en uso
    public Bala obtener() {
        for (Bala b : balas) {
            if (!b.estaActiva()) {
                return b;
            }
        }
        return null;
    }
}