import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot y MouseInfo)

/**
 * El mundo del juego: un escenario de 800x500 donde vive el Jugador.
 *
 * Drivers arquitectónicos abordados aquí:
 *  - Simplicidad: el mundo solo prepara el escenario y coloca al jugador.
 *  - Extensibilidad: agregar plataformas o enemigos se hará en prepararMundo().
 */
public class Mundo extends World
{
    public Mundo()
    {
        // Ancho 800, alto 500, tamaño de celda 1 pixel
        super(800, 500, 1);
        prepararMundo();
    }

    /**
     * Coloca los objetos iniciales del juego.
     */
    private void prepararMundo()
    {
        Jugador jugador = new Jugador();
        addObject(jugador, 100, 400);
    }
}
