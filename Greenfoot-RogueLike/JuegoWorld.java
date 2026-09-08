import greenfoot.*;

public class JuegoWorld extends World
{
    private Jugador jugador;
    private GestorOleadas gestor;
    private HUD hud;

    public JuegoWorld()
    {
        super(900, 600, 1);

        jugador = new Jugador();
        addObject(jugador, getWidth()/2, getHeight()/2);

        gestor = new GestorOleadas(this, jugador);

        hud = new HUD(this, jugador, gestor);
        addObject(hud, 220, 25);
    }

    public void act()
    {
        gestor.actualizar();
    }

    public void gameOver()
    {
        showText("GAME OVER", getWidth()/2, getHeight()/2);
        Greenfoot.stop();
    }
}
