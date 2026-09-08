import greenfoot.*;

/**
 * El arma que se ve en pantalla girando alrededor del jugador.
 *
 * Es solo decoracion: el disparo real lo maneja la clase Arma. Se separa del
 * jugador para poder rotarla sin rotar al personaje.
 */
public class ArmaVista extends Actor
{
    private static final int DISTANCIA = 15;

    private Jugador duenio;
    private Arma armaCacheada;
    private GreenfootImage normal;
    private GreenfootImage espejada;

    public ArmaVista(Jugador duenio)
    {
        this.duenio = duenio;
        actualizarImagenes();
    }

    public void act()
    {
        World mundo = getWorld();
        if (mundo == null) return;

        // si el jugador murio, esta imagen ya no tiene sentido
        if (duenio == null || duenio.getWorld() == null)
        {
            mundo.removeObject(this);
            return;
        }

        actualizarImagenes();

        double ang = duenio.getAngulo();

        // apuntando a la izquierda hay que espejar, si no el arma queda de cabeza
        boolean haciaIzquierda = ang > 90 || ang < -90;
        setImage(haciaIzquierda ? espejada : normal);
        setRotation((int) Math.round(ang));

        double rad = Math.toRadians(ang);
        int x = duenio.getX() + (int) Math.round(Math.cos(rad) * DISTANCIA);
        int y = duenio.getY() + 3 + (int) Math.round(Math.sin(rad) * DISTANCIA);
        setLocation(x, y);
    }

    /** Solo rehace las imagenes si el jugador cambio de arma. */
    private void actualizarImagenes()
    {
        Arma actual = duenio.getArma();
        if (actual == armaCacheada) return;

        armaCacheada = actual;
        normal = actual.getImagen();
        espejada = new GreenfootImage(normal);
        espejada.mirrorVertically();
        setImage(normal);
    }
}
