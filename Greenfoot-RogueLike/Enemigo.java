import greenfoot.*;

/**
 * Base de los enemigos. Cada subclase solo define su comportamiento en
 * comportarse(); todo lo demas (vida, dano, muerte) ya viene de Personaje.
 */
public abstract class Enemigo extends Personaje
{
    protected Jugador objetivo;

    public Enemigo(Jugador objetivo, int vida, int velocidad)
    {
        super(vida, 0, velocidad);
        this.objetivo = objetivo;
    }

    public void act()
    {
        if (objetivo == null || objetivo.getWorld() == null) return;
        actualizarEstado();
        comportarse();
    }

    protected abstract void comportarse();

    /** Distancia en pixeles hasta el jugador. */
    protected double distanciaAlObjetivo()
    {
        int dx = objetivo.getX() - getX();
        int dy = objetivo.getY() - getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Avanza hacia el jugador (signo 1) o se aleja (signo -1),
     * respetando los muros.
     */
    protected void moverHaciaObjetivo(int signo)
    {
        int dx = objetivo.getX() - getX();
        int dy = objetivo.getY() - getY();
        double dist = Math.sqrt(dx * dx + dy * dy);
        if (dist < 1) return;

        int mx = (int) Math.round(signo * velocidad * dx / dist);
        int my = (int) Math.round(signo * velocidad * dy / dist);
        moverCon(mx, my, 12);
    }

    protected void morir()
    {
        World mundo = getWorld();
        if (mundo instanceof JuegoWorld)
        {
            ((JuegoWorld) mundo).getSonido().reproducir("muerte");
        }
        super.morir();
    }
}
