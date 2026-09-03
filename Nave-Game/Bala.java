import greenfoot.*;

public class Bala extends Actor {
    private boolean activa = false;

    public Bala() {
        GreenfootImage img = new GreenfootImage(10, 4);
        img.setColor(Color.YELLOW);
        img.fill();
        setImage(img);
    }

    // La estrategia de disparo llama a este método para "sacar" la bala del pool
    public void activar(World mundo, int x, int y, int angulo) {
        activa = true;
        setRotation(angulo);
        mundo.addObject(this, x, y);
    }

    public void act() {
        if (!activa) return;
        move(100); // bala más rápida (antes era 8)

        Enemigo e = (Enemigo) getOneIntersectingObject(Enemigo.class);
        if (e != null) {
            e.destruir();
            desactivar();
            return;
        }

        if (isAtEdge()) {
            desactivar();
        }
    }

    public void desactivar() {
        activa = false;
        if (getWorld() != null) {
            getWorld().removeObject(this); // vuelve al pool, no se destruye
        }
    }

    public boolean estaActiva() {
        return activa;
    }
}
