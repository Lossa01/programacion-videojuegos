import greenfoot.*;

/**
 * Bala. Sirve tanto para el jugador como para los enemigos: el flag deJugador
 * decide a quien puede golpear y de que color se ve.
 *
 * La posicion se guarda en double aparte de la del Actor, porque Greenfoot
 * trabaja en enteros y al moverse en diagonal se iria acumulando el error.
 */
public class Proyectil extends Actor
{
    private double px;
    private double py;
    private double rad;
    private int velocidad;
    private int danio;
    private boolean deJugador;
    private int vidaUtil = 110;

    public Proyectil(double angulo, int velocidad, int danio, boolean deJugador)
    {
        this.rad = Math.toRadians(angulo);
        this.velocidad = velocidad;
        this.danio = danio;
        this.deJugador = deJugador;

        setImage(deJugador ? FabricaImagenes.balaJugador()
                           : FabricaImagenes.balaEnemigo());
        setRotation((int) Math.round(angulo));
    }

    /** Frames que vive la bala antes de desaparecer sola. */
    public void setAlcance(int frames)
    {
        vidaUtil = frames;
    }

    protected void addedToWorld(World mundo)
    {
        px = getX();
        py = getY();
    }

    public void act()
    {
        JuegoWorld mundo = (JuegoWorld) getWorld();
        if (mundo == null) return;

        px += Math.cos(rad) * velocidad;
        py += Math.sin(rad) * velocidad;

        int nx = (int) Math.round(px);
        int ny = (int) Math.round(py);

        // fuera del mundo o contra un muro
        if (nx < 0 || ny < 0 || nx >= mundo.getWidth() || ny >= mundo.getHeight()
            || mundo.esMuroPixel(nx, ny))
        {
            mundo.removeObject(this);
            return;
        }

        setLocation(nx, ny);

        if (impactar(mundo)) return;

        vidaUtil--;
        if (vidaUtil <= 0) mundo.removeObject(this);
    }

    /** Devuelve true si la bala golpeo algo y ya fue eliminada. */
    private boolean impactar(JuegoWorld mundo)
    {
        if (deJugador)
        {
            Enemigo enemigo = (Enemigo) getOneIntersectingObject(Enemigo.class);
            if (enemigo != null)
            {
                enemigo.recibirDanio(danio);
                mundo.getSonido().reproducir("golpe");
                mundo.removeObject(this);
                return true;
            }
        }
        else
        {
            Jugador jugador = (Jugador) getOneIntersectingObject(Jugador.class);
            if (jugador != null)
            {
                if (jugador.recibirDanio(danio)) mundo.getSonido().reproducir("golpe");
                mundo.removeObject(this);
                return true;
            }
        }
        return false;
    }
}
