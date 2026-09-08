import greenfoot.*;

/**
 * Enemigo a distancia: trata de mantenerse a media distancia del jugador y
 * dispara. Si te le acercas mucho retrocede, asi obliga a moverse en vez de
 * quedarse parado disparando.
 */
public class EnemigoTirador extends Enemigo
{
    private static final int DISTANCIA_IDEAL = 220;
    private static final int MARGEN = 45;

    private int danioTiro;
    private int enfriamiento;
    private int cadencia;

    public EnemigoTirador(Jugador objetivo, int piso)
    {
        super(objetivo, 20 + piso * 4, 2);
        this.danioTiro = Math.min(6 + piso, 16);
        this.cadencia = Math.max(45, 90 - piso * 6);
        this.enfriamiento = 30 + Greenfoot.getRandomNumber(40);
        // copia propia para poder parpadear sin afectar a los demas enemigos
        setImage(new GreenfootImage(FabricaImagenes.enemigoTirador()));
    }

    protected void comportarse()
    {
        double dist = distanciaAlObjetivo();

        if (dist > DISTANCIA_IDEAL + MARGEN)      moverHaciaObjetivo(1);
        else if (dist < DISTANCIA_IDEAL - MARGEN) moverHaciaObjetivo(-1);

        if (enfriamiento > 0)
        {
            enfriamiento--;
            return;
        }

        disparar();
        enfriamiento = cadencia;
    }

    private void disparar()
    {
        JuegoWorld mundo = (JuegoWorld) getWorld();
        if (mundo == null) return;

        double angulo = Math.toDegrees(Math.atan2(objetivo.getY() - getY(),
                                                  objetivo.getX() - getX()));
        // punteria imperfecta: si no, es imposible esquivar
        angulo += Greenfoot.getRandomNumber(13) - 6;

        mundo.addObject(new Proyectil(angulo, 6, danioTiro, false), getX(), getY());
        mundo.getSonido().reproducir("disparo");
    }
}
