import greenfoot.*;

/**
 * ===================================================================
 *  CLASE DE FRANCO  -  ESTA VERSION ES SOLO UN PROVISORIO
 * ===================================================================
 *
 * Deberia mostrar la grilla de salas del piso: donde estas parado, cuales ya
 * limpiaste, cuales no visitaste, y marcar la sala del jefe y la del cofre.
 * La especificacion completa esta en Guia-para-Franco.md.
 *
 * Toda la informacion que necesita sale de Mazmorra (getSala, getColActual,
 * getFilaActual) y de Sala (fueVisitada, estaLimpiada, getTipo).
 */
public class Minimapa extends Actor
{
    private JuegoWorld mundo;
    private int contador = 0;

    public Minimapa(JuegoWorld mundo)
    {
        this.mundo = mundo;
        redibujar();
    }

    public void act()
    {
        contador++;
        if (contador >= 15)
        {
            contador = 0;
            redibujar();
        }
    }

    private void redibujar()
    {
        GreenfootImage img = new GreenfootImage(150, 150);
        img.setColor(new Color(0, 0, 0, 140));
        img.fillRect(0, 0, 150, 150);
        img.setColor(new Color(180, 180, 190));
        img.drawRect(0, 0, 149, 149);
        img.drawString("MINIMAPA", 38, 68);
        img.drawString("(pendiente)", 34, 88);
        setImage(img);
    }
}
