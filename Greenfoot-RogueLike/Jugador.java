import greenfoot.*;

public class Jugador extends Soldado
{
    private int cooldownDisparo = 0;
    private int dirX = 1;
    private int dirY = 0;

    public Jugador()
    {
        super(100, 4);
        setImage(FabricaImagenes.soldadoJugador());
    }

    public void act()
    {
        mover();
        disparar();
        if (cooldownDisparo > 0) cooldownDisparo--;
    }

    private void mover()
    {
        int dx = 0;
        int dy = 0;

        if (Greenfoot.isKeyDown("w"))
        {
            dy = -velocidad;
            dirX = 0; dirY = -1;
        }
        if (Greenfoot.isKeyDown("s"))
        {
            dy = velocidad;
            dirX = 0; dirY = 1;
        }
        if (Greenfoot.isKeyDown("a"))
        {
            dx = -velocidad;
            dirX = -1; dirY = 0;
        }
        if (Greenfoot.isKeyDown("d"))
        {
            dx = velocidad;
            dirX = 1; dirY = 0;
        }

        int nuevoX = Math.max(15,
            Math.min(getWorld().getWidth()-15, getX()+dx));
        int nuevoY = Math.max(15,
            Math.min(getWorld().getHeight()-15, getY()+dy));

        setLocation(nuevoX, nuevoY);
    }

    private void disparar()
    {
        if (Greenfoot.isKeyDown("space") && cooldownDisparo == 0)
        {
            int cantidadBalas = getWorld().getObjects(Bala.class).size();

            if (cantidadBalas < 60)
            {
                Bala bala = new Bala(dirX, dirY);
                getWorld().addObject(bala, getX(), getY());
            }
            cooldownDisparo = 8;
        }
    }

    protected void morir()
    {
        World mundo = getWorld();
        if (mundo instanceof JuegoWorld)
        {
            ((JuegoWorld)mundo).gameOver();
        }
        if (mundo != null) mundo.removeObject(this);
    }
}
