import greenfoot.*;

/**
 * Base comun del jugador y de los enemigos.
 *
 * Maneja vida, escudo regenerable e invulnerabilidad breve tras recibir
 * un golpe (si no, dos enemigos pegados te vacian la barra en medio segundo).
 */
public abstract class Personaje extends Actor
{
    protected int vidaMax;
    protected int vida;
    protected int escudoMax;
    protected int escudo;
    protected int velocidad;

    /** Frames que faltan para poder volver a recibir dano. */
    protected int invulnerable = 0;
    /** Frames que faltan para que el escudo empiece a regenerar. */
    protected int esperaEscudo = 0;
    private int tickEscudo = 0;

    public Personaje(int vidaMax, int escudoMax, int velocidad)
    {
        this.vidaMax = vidaMax;
        this.vida = vidaMax;
        this.escudoMax = escudoMax;
        this.escudo = escudoMax;
        this.velocidad = velocidad;
    }

    /**
     * Aplica dano. Primero se come el escudo y despues la vida, igual que en
     * Soul Knight. Devuelve true si el golpe efectivamente entro.
     */
    public boolean recibirDanio(int cantidad)
    {
        if (invulnerable > 0 || cantidad <= 0) return false;

        invulnerable = 18;
        esperaEscudo = 150;

        if (escudo > 0)
        {
            escudo -= cantidad;
            if (escudo < 0)
            {
                vida += escudo;   // el sobrante pasa a la vida
                escudo = 0;
            }
        }
        else
        {
            vida -= cantidad;
        }

        if (vida <= 0)
        {
            vida = 0;
            morir();
        }
        return true;
    }

    /** Llamar una vez por frame desde act(). */
    protected void actualizarEstado()
    {
        if (invulnerable > 0) invulnerable--;

        if (esperaEscudo > 0)
        {
            esperaEscudo--;
        }
        else if (escudo < escudoMax)
        {
            tickEscudo++;
            if (tickEscudo >= 25)
            {
                tickEscudo = 0;
                escudo++;
            }
        }

        // parpadeo mientras es invulnerable
        if (invulnerable > 0 && invulnerable % 6 < 3) getImage().setTransparency(120);
        else getImage().setTransparency(255);
    }

    protected void morir()
    {
        World mundo = getWorld();
        if (mundo != null) mundo.removeObject(this);
    }

    /**
     * Mueve en X e Y por separado para poder "deslizarse" contra los muros:
     * si te topas en diagonal, igual avanzas por el eje que si esta libre.
     */
    protected void moverCon(int dx, int dy, int radio)
    {
        JuegoWorld mundo = (JuegoWorld) getWorld();
        if (mundo == null) return;

        if (dx != 0 && mundo.puedeEstar(getX() + dx, getY(), radio))
            setLocation(getX() + dx, getY());

        if (dy != 0 && mundo.puedeEstar(getX(), getY() + dy, radio))
            setLocation(getX(), getY() + dy);
    }

    public int getVida()     { return vida; }
    public int getVidaMax()  { return vidaMax; }
    public int getEscudo()   { return escudo; }
    public int getEscudoMax(){ return escudoMax; }
}
