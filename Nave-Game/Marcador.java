import greenfoot.*;

public class Marcador extends Actor implements Observador {

    public Marcador() {
        GameManager.getInstancia().suscribir(this);
        actualizar(GameManager.getInstancia().getPuntos()); // imagen inicial
    }

    @Override
    public void actualizar(int puntos) {
        setImage(new GreenfootImage("Puntos: " + puntos, 24, Color.WHITE, null));
    }
}
