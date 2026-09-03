import greenfoot.*;

public class Espacio extends World {
    private PoolDeBalas pool;
    private int contadorAparicion = 0;

    public Espacio() {
        super(600, 400, 1);
        GameManager.getInstancia().reiniciar(); // limpia estado y observadores
        pool = new PoolDeBalas(20); // 20 balas reutilizables
        addObject(new Nave(pool), 60, getHeight() / 2);
        addObject(new Marcador(), 80, 20); // se suscribe en su constructor
    }

    public void act() {
        contadorAparicion++;
        if (contadorAparicion >= 60) { // cada ~60 frames, un enemigo
            addObject(new Enemigo(), getWidth() - 20,
                      Greenfoot.getRandomNumber(getHeight()));
            contadorAparicion = 0;
        }
    }
}