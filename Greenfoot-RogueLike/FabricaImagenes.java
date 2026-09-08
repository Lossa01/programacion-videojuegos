import greenfoot.*;

/**
 * Todo el arte del juego se dibuja por codigo aca. No hay archivos de imagen.
 *
 * Cada imagen se crea UNA sola vez (son static final). Los tiles y las balas
 * usan directamente esa instancia compartida. Los personajes en cambio se
 * hacen una copia, porque al recibir dano cambian su transparencia para
 * parpadear y con la imagen compartida parpadearian todos juntos.
 */
public class FabricaImagenes
{
    public static final int TILE = 32;

    // --- Paleta ---------------------------------------------------------
    private static final Color C_SUELO      = new Color(30, 32, 43);
    private static final Color C_SUELO_2    = new Color(38, 41, 54);
    private static final Color C_SUELO_3    = new Color(45, 48, 63);
    private static final Color C_MURO       = new Color(60, 64, 84);
    private static final Color C_MURO_ALTO  = new Color(94, 100, 126);
    private static final Color C_MURO_BAJO  = new Color(36, 38, 52);

    // --- Imagenes cacheadas ---------------------------------------------
    private static final GreenfootImage SUELO    = crearSuelo();
    private static final GreenfootImage MURO     = crearMuro();
    private static final GreenfootImage JUGADOR  = crearJugador();
    private static final GreenfootImage MELEE    = crearMelee();
    private static final GreenfootImage TIRADOR  = crearTirador();
    private static final GreenfootImage BALA_J   = crearBala(new Color(120, 235, 255),
                                                             new Color(235, 255, 255));
    private static final GreenfootImage BALA_E   = crearBala(new Color(255, 105, 85),
                                                             new Color(255, 215, 170));
    private static final GreenfootImage PISTOLA  = crearPistola();
    private static final GreenfootImage ESCOPETA = crearEscopeta();

    public static GreenfootImage suelo()          { return SUELO; }
    public static GreenfootImage muro()           { return MURO; }
    public static GreenfootImage jugador()        { return JUGADOR; }
    public static GreenfootImage enemigoMelee()   { return MELEE; }
    public static GreenfootImage enemigoTirador() { return TIRADOR; }
    public static GreenfootImage balaJugador()    { return BALA_J; }
    public static GreenfootImage balaEnemigo()    { return BALA_E; }
    public static GreenfootImage pistola()        { return PISTOLA; }
    public static GreenfootImage escopeta()       { return ESCOPETA; }

    // --------------------------------------------------------------------
    // Piso y muros
    // --------------------------------------------------------------------

    private static GreenfootImage crearSuelo()
    {
        GreenfootImage img = new GreenfootImage(TILE, TILE);
        img.setColor(C_SUELO);
        img.fill();

        // manchitas para que no se vea plano
        img.setColor(C_SUELO_2);
        for (int i = 0; i < 14; i++)
        {
            int x = Greenfoot.getRandomNumber(TILE);
            int y = Greenfoot.getRandomNumber(TILE);
            img.fillRect(x, y, 2, 2);
        }

        img.setColor(C_SUELO_3);
        for (int i = 0; i < 4; i++)
        {
            int x = Greenfoot.getRandomNumber(TILE - 4);
            int y = Greenfoot.getRandomNumber(TILE - 4);
            img.fillRect(x, y, 3, 2);
        }

        // borde tenue para insinuar baldosas
        img.setColor(new Color(20, 21, 29));
        img.drawRect(0, 0, TILE - 1, TILE - 1);
        return img;
    }

    private static GreenfootImage crearMuro()
    {
        GreenfootImage img = new GreenfootImage(TILE, TILE);
        img.setColor(C_MURO);
        img.fill();

        // luz arriba, sombra abajo: da sensacion de bloque
        img.setColor(C_MURO_ALTO);
        img.fillRect(0, 0, TILE, 6);
        img.setColor(C_MURO_BAJO);
        img.fillRect(0, TILE - 5, TILE, 5);

        // juntas de ladrillo
        img.setColor(C_MURO_BAJO);
        img.fillRect(0, 14, TILE, 2);
        img.fillRect(15, 6, 2, 8);
        img.fillRect(7, 16, 2, 11);
        img.fillRect(23, 16, 2, 11);
        return img;
    }

    // --------------------------------------------------------------------
    // Personajes
    // --------------------------------------------------------------------

    private static GreenfootImage crearJugador()
    {
        GreenfootImage img = new GreenfootImage(26, 32);

        // sombra en el piso
        img.setColor(new Color(0, 0, 0, 90));
        img.fillOval(4, 27, 18, 5);

        // piernas
        img.setColor(new Color(40, 46, 72));
        img.fillRect(8, 22, 4, 6);
        img.fillRect(14, 22, 4, 6);

        // cuerpo
        img.setColor(new Color(70, 120, 200));
        img.fillRect(6, 13, 14, 10);
        img.setColor(new Color(95, 155, 235));
        img.fillRect(6, 13, 14, 3);

        // brazos
        img.setColor(new Color(60, 100, 175));
        img.fillRect(3, 15, 4, 6);
        img.fillRect(19, 15, 4, 6);

        // cabeza
        img.setColor(new Color(232, 190, 155));
        img.fillOval(7, 4, 12, 11);

        // casco con visor
        img.setColor(new Color(48, 58, 90));
        img.fillRect(6, 2, 14, 6);
        img.fillOval(6, 1, 14, 8);
        img.setColor(new Color(130, 235, 255));
        img.fillRect(8, 8, 10, 3);

        return img;
    }

    private static GreenfootImage crearMelee()
    {
        GreenfootImage img = new GreenfootImage(28, 28);

        img.setColor(new Color(0, 0, 0, 90));
        img.fillOval(5, 23, 18, 5);

        // cuerpo tipo baba
        img.setColor(new Color(130, 70, 180));
        img.fillOval(2, 4, 24, 21);
        img.setColor(new Color(165, 100, 215));
        img.fillOval(5, 6, 18, 10);

        // ojos
        img.setColor(Color.WHITE);
        img.fillOval(8, 12, 5, 6);
        img.fillOval(16, 12, 5, 6);
        img.setColor(new Color(25, 15, 40));
        img.fillOval(10, 14, 3, 3);
        img.fillOval(18, 14, 3, 3);

        return img;
    }

    private static GreenfootImage crearTirador()
    {
        GreenfootImage img = new GreenfootImage(26, 30);

        img.setColor(new Color(0, 0, 0, 90));
        img.fillOval(4, 25, 18, 5);

        // tunica
        img.setColor(new Color(120, 45, 55));
        img.fillRect(5, 12, 16, 13);
        img.setColor(new Color(160, 65, 75));
        img.fillRect(5, 12, 16, 3);

        // capucha
        img.setColor(new Color(95, 35, 45));
        img.fillOval(4, 2, 18, 14);

        // ojo brillante
        img.setColor(new Color(255, 210, 90));
        img.fillOval(10, 8, 6, 4);

        // baston
        img.setColor(new Color(90, 70, 55));
        img.fillRect(20, 8, 3, 18);
        img.setColor(new Color(255, 150, 90));
        img.fillOval(18, 4, 7, 7);

        return img;
    }

    // --------------------------------------------------------------------
    // Proyectiles y armas
    // --------------------------------------------------------------------

    private static GreenfootImage crearBala(Color centro, Color brillo)
    {
        GreenfootImage img = new GreenfootImage(14, 10);

        // halo
        img.setColor(new Color(centro.getRed(), centro.getGreen(), centro.getBlue(), 70));
        img.fillOval(0, 0, 14, 10);
        // nucleo
        img.setColor(centro);
        img.fillOval(3, 2, 8, 6);
        // punto de luz
        img.setColor(brillo);
        img.fillOval(5, 3, 4, 3);

        return img;
    }

    private static GreenfootImage crearPistola()
    {
        GreenfootImage img = new GreenfootImage(22, 12);
        img.setColor(new Color(55, 58, 70));
        img.fillRect(2, 4, 14, 5);   // cuerpo
        img.fillRect(4, 8, 5, 4);    // culata
        img.setColor(new Color(90, 95, 112));
        img.fillRect(14, 5, 7, 3);   // canhon
        img.setColor(new Color(140, 148, 170));
        img.fillRect(2, 4, 12, 1);   // brillo
        return img;
    }

    private static GreenfootImage crearEscopeta()
    {
        GreenfootImage img = new GreenfootImage(28, 12);
        img.setColor(new Color(85, 60, 45));
        img.fillRect(1, 5, 9, 5);    // culata de madera
        img.setColor(new Color(55, 58, 70));
        img.fillRect(9, 4, 12, 5);
        img.setColor(new Color(95, 100, 118));
        img.fillRect(19, 4, 9, 3);
        img.fillRect(19, 7, 9, 2);
        img.setColor(new Color(150, 158, 180));
        img.fillRect(9, 4, 10, 1);
        return img;
    }
}
