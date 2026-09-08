import greenfoot.*;

/**
 * Base de todas las armas.
 *
 * La idea es que agregar un arma nueva sea solo crear una subclase y definir
 * como salen sus proyectiles, sin tocar nada del jugador.
 */
public abstract class Arma
{
    protected int cadencia;       // frames de espera entre disparos
    protected int costoEnergia;
    protected int danio;
    protected int velocidadBala;

    private int enfriamiento = 0;

    public Arma(int cadencia, int costoEnergia, int danio, int velocidadBala)
    {
        this.cadencia = cadencia;
        this.costoEnergia = costoEnergia;
        this.danio = danio;
        this.velocidadBala = velocidadBala;
    }

    /** Llamar una vez por frame para que baje el enfriamiento. */
    public void actualizar()
    {
        if (enfriamiento > 0) enfriamiento--;
    }

    public boolean listo()
    {
        return enfriamiento == 0;
    }

    /** Dispara si esta lista. Devuelve true si efectivamente disparo. */
    public boolean disparar(JuegoWorld mundo, int x, int y, double angulo, boolean deJugador)
    {
        if (enfriamiento > 0) return false;
        enfriamiento = cadencia;
        crearProyectiles(mundo, x, y, angulo, deJugador);
        return true;
    }

    protected abstract void crearProyectiles(JuegoWorld mundo, int x, int y,
                                             double angulo, boolean deJugador);

    public abstract String getNombre();
    public abstract GreenfootImage getImagen();

    public int getCostoEnergia() { return costoEnergia; }
    public int getDanio()        { return danio; }
}
