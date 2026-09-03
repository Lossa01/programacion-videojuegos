import greenfoot.*;

public class HUD extends Actor
{
    private JuegoWorld mundo;
    private Jugador jugador;
    private GestorOleadas gestor;
    private int contador = 0;

    public HUD(JuegoWorld mundo,
               Jugador jugador,
               GestorOleadas gestor)
    {
        this.mundo = mundo;
        this.jugador = jugador;
        this.gestor = gestor;
        actualizarImagen();
    }

    public void act()
    {
        contador++;
        if (contador >= 10)
        {
            actualizarImagen();
            contador = 0;
        }
    }

    private void actualizarImagen()
    {
        int enemigos = mundo.getObjects(SoldadoEnemigo.class).size();
        int balas = mundo.getObjects(Bala.class).size();

        String texto = "Oleada: " + gestor.getOleada()
            + " | Vida: " + jugador.getVida()
            + " | Enemigos: " + enemigos
            + " | Balas: " + balas;

        GreenfootImage img = new GreenfootImage(
            texto, 22, Color.WHITE, Color.BLACK);
        setImage(img);
    }
}
