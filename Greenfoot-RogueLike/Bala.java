import greenfoot.*;

public class Bala extends Actor
{
    private int dx;
    private int dy;
    private int velocidad = 8;
    private int vidaUtil = 80;

    public Bala(int dirX, int dirY)
    {
        dx = dirX;
        dy = dirY;
        setImage(FabricaImagenes.bala());

        if (dx == 1) setRotation(0);
        if (dx == -1) setRotation(180);
        if (dy == 1) setRotation(90);
        if (dy == -1) setRotation(270);
    }

    public void act()
    {
        setLocation(getX()+dx*velocidad, getY()+dy*velocidad);
        vidaUtil--;

        SoldadoEnemigo enemigo = (SoldadoEnemigo)
            getOneIntersectingObject(SoldadoEnemigo.class);

        if (enemigo != null)
        {
            enemigo.recibirDanio(10);
            getWorld().removeObject(this);
            return;
        }

        if (vidaUtil <= 0 || fueraDelMundo())
        {
            getWorld().removeObject(this);
        }
    }

    private boolean fueraDelMundo()
    {
        return getX() <= 2 || getY() <= 2
            || getX() >= getWorld().getWidth()-2
            || getY() >= getWorld().getHeight()-2;
    }
}
