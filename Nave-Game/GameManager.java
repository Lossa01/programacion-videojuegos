import java.util.*;

public class GameManager {
    private static GameManager instancia; // la única instancia
    private int puntos = 0;
    private List<Observador> observadores = new ArrayList<>();

    private GameManager() { } // constructor privado

    public static GameManager getInstancia() {
        if (instancia == null) {
            instancia = new GameManager();
        }
        return instancia;
    }

    public void suscribir(Observador o) {
        observadores.add(o);
    }

    public void sumarPuntos(int p) {
        puntos += p;
        notificar(); // avisa a los observadores
    }

    public void reiniciar() {
        puntos = 0;
        observadores.clear(); // evita observadores obsoletos tras un Reset
    }

    public int getPuntos() {
        return puntos;
    }

    private void notificar() {
        for (Observador o : observadores) {
            o.actualizar(puntos);
        }
    }
}