import greenfoot.*;

public class Nave extends Actor {
    private EstrategiaDisparo estrategia = new DisparoSimple();
    private PoolDeBalas pool;
    private int recarga = 0;

    public Nave(PoolDeBalas pool) {
        this.pool = pool;
        GreenfootImage img = new GreenfootImage(30, 20);
        img.setColor(Color.GREEN);
        img.fillPolygon(new int[]{0, 0, 30}, new int[]{0, 20, 10}, 3);
        setImage(img);
    }

    public void act() {
        mover();
        cambiarArma();
        disparar();
    }

    private void mover() {
        if (Greenfoot.isKeyDown("up") && getY() > 10) {
            setLocation(getX(), getY() - 4);
        }
        if (Greenfoot.isKeyDown("down") && getY() < getWorld().getHeight() - 10) {
            setLocation(getX(), getY() + 4);
        }
    }

    private void cambiarArma() {
        if (Greenfoot.isKeyDown("1")) estrategia = new DisparoSimple();
        if (Greenfoot.isKeyDown("2")) estrategia = new DisparoTriple();
        if (Greenfoot.isKeyDown("3")) estrategia = new Disparo30ple();
    }

    private void disparar() {
        if (recarga > 0) recarga--;
        if (Greenfoot.isKeyDown("space") && recarga == 0) {
            estrategia.disparar(this, pool); // el "qué" lo decide la estrategia
            recarga = 15;
        }
    }
}
