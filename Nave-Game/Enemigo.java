import greenfoot.*;

public class Enemigo extends Actor {
    private Estado estado = new Avanzar();

    public Enemigo() {
        GreenfootImage img = new GreenfootImage(24, 24);
        img.setColor(Color.RED);
        img.fillOval(0, 0, 24, 24);
        setImage(img);
    }

    public void act() {
        estado = estado.actuar(this);
        if (getX() < 0) {
            getWorld().removeObject(this);
        }
    }

    // Llamado por la bala al impactar: suma puntos vía el Singleton
    public void destruir() {
        GameManager.getInstancia().sumarPuntos(100000);
        if (getWorld() != null) {
            getWorld().removeObject(this);
        }
    }
}          
