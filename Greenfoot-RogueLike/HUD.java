import greenfoot.*;

/**
 * ===================================================================
 *  CLASE DE FRANCO  -  ESTA VERSION ES SOLO UN PROVISORIO
 * ===================================================================
 *
 * Ahora mismo escribe una linea de texto plano para poder probar el juego.
 * La version final (barras de vida/escudo/energia, arma actual, monedas,
 * piso, aviso de vida baja) esta especificada en Guia-para-Franco.md.
 *
 * Como funciona: este Actor tiene una imagen del tamano completo del mundo,
 * transparente, y dibuja encima. Asi puede pintar en cualquier parte de la
 * pantalla sin importar donde este posicionado el actor.
 */
public class HUD extends Actor
{
    private JuegoWorld mundo;
    private Jugador jugador;
    private int contador = 0;

    public HUD(JuegoWorld mundo, Jugador jugador)
    {
        this.mundo = mundo;
        this.jugador = jugador;
        redibujar();
    }

    public void act()
    {
        // no hace falta redibujar 60 veces por segundo
        contador++;
        if (contador >= 6)
        {
            contador = 0;
            redibujar();
        }
    }

    private void redibujar()
    {
        GreenfootImage img = new GreenfootImage(mundo.getWidth(), mundo.getHeight());

        String texto = "[HUD provisorio]  Vida " + jugador.getVida() + "/" + jugador.getVidaMax()
            + "   Escudo " + jugador.getEscudo() + "/" + jugador.getEscudoMax()
            + "   Energia " + jugador.getEnergia() + "/" + jugador.getEnergiaMax()
            + "   Arma: " + jugador.getArma().getNombre();

        img.setColor(new Color(0, 0, 0, 150));
        img.fillRect(8, 8, 640, 26);
        img.setColor(Color.WHITE);
        img.drawString(texto, 16, 26);

        setImage(img);
    }
}
