import greenfoot.*;

public class Mundo extends World
{
    public Mundo()
    {
        super(800, 500, 1);
        prepararMundo();
    }

    private void prepararMundo()
    {
        Jugador jugador = new Jugador();
        addObject(jugador, 100, 400);
    }
}
