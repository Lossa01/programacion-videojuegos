import greenfoot.*;

/**
 * Enemigo cuerpo a cuerpo: corre derecho hacia el jugador y hace dano al tocarlo.
 * La vida y el dano suben con el numero de piso.
 */
public class EnemigoMelee extends Enemigo
{
    private int danioContacto;
    private int enfriamientoGolpe = 0;

    public EnemigoMelee(Jugador objetivo, int piso)
    {
        super(objetivo, 28 + piso * 6, Math.min(2 + piso / 3, 4));
        this.danioContacto = Math.min(8 + piso * 2, 20);
        // copia propia para poder parpadear sin afectar a los demas enemigos
        setImage(new GreenfootImage(FabricaImagenes.enemigoMelee()));
    }

    protected void comportarse()
    {
        moverHaciaObjetivo(1);

        if (enfriamientoGolpe > 0) enfriamientoGolpe--;

        if (enfriamientoGolpe == 0 && isTouching(Jugador.class))
        {
            objetivo.recibirDanio(danioContacto);
            enfriamientoGolpe = 35;
        }
    }
}
