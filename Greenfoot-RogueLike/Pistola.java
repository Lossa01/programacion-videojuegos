import greenfoot.*;

/** Arma inicial: un tiro por vez, barata en energia y bastante rapida. */
public class Pistola extends Arma
{
    public Pistola()
    {
        super(11, 2, 12, 11);
    }

    protected void crearProyectiles(JuegoWorld mundo, int x, int y,
                                    double angulo, boolean deJugador)
    {
        // dispersion minima para que no se sienta robotico
        double desvio = (Greenfoot.getRandomNumber(9) - 4) * 0.5;
        Proyectil p = new Proyectil(angulo + desvio, velocidadBala, danio, deJugador);
        mundo.addObject(p, x, y);
    }

    public String getNombre() { return "Pistola"; }

    public GreenfootImage getImagen() { return FabricaImagenes.pistola(); }
}
