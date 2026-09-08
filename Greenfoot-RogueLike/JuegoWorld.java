import greenfoot.*;
import java.util.List;

/**
 * Mundo principal. Se encarga de:
 *  - generar y mostrar la sala actual de la mazmorra
 *  - responder si una posicion en pixeles choca con un muro
 *  - pasar de una sala a otra cuando el jugador cruza una puerta
 *
 * Los muros NO son actores: se dibujan sobre el fondo del mundo y la colision
 * se resuelve consultando la grilla de la sala. Con 600 tiles por sala eso es
 * muchisimo mas barato que tener 600 objetos preguntando si chocan.
 */
public class JuegoWorld extends World
{
    public static final int TILE  = FabricaImagenes.TILE;
    public static final int COLS  = 30;
    public static final int FILAS = 20;

    private Mazmorra mazmorra;
    private Jugador jugador;
    private HUD hud;
    private Minimapa minimapa;
    private GestorSonido sonido;

    private int piso = 1;
    private int monedas = 0;
    private boolean terminado = false;
    private int enfriamientoPuerta = 0;

    public JuegoWorld()
    {
        super(COLS * TILE, FILAS * TILE, 1);

        setPaintOrder(HUD.class, Minimapa.class, Proyectil.class,
                      ArmaVista.class, Jugador.class, Enemigo.class);

        sonido   = new GestorSonido();
        mazmorra = new Mazmorra(piso, COLS, FILAS);
        jugador  = new Jugador();

        addObject(jugador, getWidth() / 2, getHeight() / 2);

        hud = new HUD(this, jugador);
        addObject(hud, getWidth() / 2, getHeight() / 2);

        minimapa = new Minimapa(this);
        addObject(minimapa, getWidth() - 90, 90);

        cargarSala();
    }

    public void act()
    {
        if (terminado)
        {
            if (Greenfoot.isKeyDown("r")) Greenfoot.setWorld(new JuegoWorld());
            return;
        }

        if (enfriamientoPuerta > 0) enfriamientoPuerta--;

        revisarSalaLimpia();
        revisarPuertas();
    }

    // --------------------------------------------------------------------
    // Salas
    // --------------------------------------------------------------------

    /** Borra lo de la sala anterior, dibuja la nueva y genera sus enemigos. */
    private void cargarSala()
    {
        removeObjects(getObjects(Enemigo.class));
        removeObjects(getObjects(Proyectil.class));

        Sala sala = mazmorra.getSalaActual();
        sala.marcarVisitada();

        dibujarFondo(sala);

        if (!sala.estaLimpiada())
        {
            generarEnemigos(sala);
        }
    }

    /** Pinta piso y muros de la sala directamente sobre el fondo del mundo. */
    private void dibujarFondo(Sala sala)
    {
        GreenfootImage fondo = getBackground();
        GreenfootImage suelo = FabricaImagenes.suelo();
        GreenfootImage muro  = FabricaImagenes.muro();

        for (int c = 0; c < COLS; c++)
        {
            for (int f = 0; f < FILAS; f++)
            {
                GreenfootImage tile = sala.esMuro(c, f) ? muro : suelo;
                fondo.drawImage(tile, c * TILE, f * TILE);
            }
        }

        dibujarPuertas(sala, fondo);
    }

    /** Marca visualmente las puertas: verdes si estan abiertas, rojas si no. */
    private void dibujarPuertas(Sala sala, GreenfootImage fondo)
    {
        boolean abierta = sala.estaLimpiada();
        Color color = abierta ? new Color(110, 220, 140) : new Color(200, 80, 80);
        fondo.setColor(color);

        int cm = COLS / 2;
        int fm = FILAS / 2;

        if (sala.tienePuerta(Sala.ARRIBA))
            fondo.fillRect((cm - 1) * TILE, 0, TILE * 2, 5);
        if (sala.tienePuerta(Sala.ABAJO))
            fondo.fillRect((cm - 1) * TILE, getHeight() - 5, TILE * 2, 5);
        if (sala.tienePuerta(Sala.IZQUIERDA))
            fondo.fillRect(0, (fm - 1) * TILE, 5, TILE * 2);
        if (sala.tienePuerta(Sala.DERECHA))
            fondo.fillRect(getWidth() - 5, (fm - 1) * TILE, 5, TILE * 2);
    }

    private void generarEnemigos(Sala sala)
    {
        int cantidad = sala.getEnemigosPorGenerar();

        for (int i = 0; i < cantidad; i++)
        {
            int intentos = 0;
            while (intentos < 60)
            {
                intentos++;
                int c = 2 + Greenfoot.getRandomNumber(COLS - 4);
                int f = 2 + Greenfoot.getRandomNumber(FILAS - 4);
                if (sala.esMuro(c, f)) continue;

                int x = c * TILE + TILE / 2;
                int y = f * TILE + TILE / 2;

                // que no aparezcan encima del jugador
                int dx = x - jugador.getX();
                int dy = y - jugador.getY();
                if (dx * dx + dy * dy < 200 * 200) continue;

                Enemigo enemigo;
                if (Greenfoot.getRandomNumber(100) < 65)
                    enemigo = new EnemigoMelee(jugador, piso);
                else
                    enemigo = new EnemigoTirador(jugador, piso);

                addObject(enemigo, x, y);
                break;
            }
        }
    }

    private void revisarSalaLimpia()
    {
        Sala sala = mazmorra.getSalaActual();
        if (sala.estaLimpiada()) return;

        if (getObjects(Enemigo.class).isEmpty())
        {
            sala.marcarLimpiada();
            dibujarFondo(sala);          // repinta las puertas en verde
            sonido.reproducir("puerta");
        }
    }

    /** Si el jugador toca el borde donde hay puerta abierta, cambia de sala. */
    private void revisarPuertas()
    {
        if (enfriamientoPuerta > 0) return;

        Sala sala = mazmorra.getSalaActual();
        if (!sala.estaLimpiada()) return;

        int x = jugador.getX();
        int y = jugador.getY();
        int margen = 24;

        int lado = -1;
        if (y < margen                 && sala.tienePuerta(Sala.ARRIBA))    lado = Sala.ARRIBA;
        else if (y > getHeight()-margen && sala.tienePuerta(Sala.ABAJO))     lado = Sala.ABAJO;
        else if (x < margen             && sala.tienePuerta(Sala.IZQUIERDA)) lado = Sala.IZQUIERDA;
        else if (x > getWidth()-margen  && sala.tienePuerta(Sala.DERECHA))   lado = Sala.DERECHA;

        if (lado == -1) return;
        if (!mazmorra.mover(lado)) return;

        // reubicar al jugador en la puerta opuesta de la sala nueva
        if (lado == Sala.ARRIBA)         jugador.setLocation(getWidth() / 2, getHeight() - margen - 22);
        else if (lado == Sala.ABAJO)     jugador.setLocation(getWidth() / 2, margen + 22);
        else if (lado == Sala.IZQUIERDA) jugador.setLocation(getWidth() - margen - 22, getHeight() / 2);
        else                             jugador.setLocation(margen + 22, getHeight() / 2);

        enfriamientoPuerta = 25;
        cargarSala();
    }

    // --------------------------------------------------------------------
    // Colision contra muros
    // --------------------------------------------------------------------

    /** true si el pixel dado cae dentro de un muro. */
    public boolean esMuroPixel(int px, int py)
    {
        return mazmorra.getSalaActual().esMuro(px / TILE, py / TILE);
    }

    /**
     * true si un cuadrado de lado 2*radio centrado en (x,y) cabe sin tocar muro.
     * Se revisan las cuatro esquinas, que es suficiente para hitboxes chicas.
     */
    public boolean puedeEstar(int x, int y, int radio)
    {
        if (x - radio < 0 || y - radio < 0 ||
            x + radio >= getWidth() || y + radio >= getHeight()) return false;

        return !esMuroPixel(x - radio, y - radio)
            && !esMuroPixel(x + radio, y - radio)
            && !esMuroPixel(x - radio, y + radio)
            && !esMuroPixel(x + radio, y + radio);
    }

    // --------------------------------------------------------------------
    // Estado del juego
    // --------------------------------------------------------------------

    public void gameOver()
    {
        terminado = true;
        showText("GAME OVER  -  presiona R para reintentar", getWidth() / 2, getHeight() / 2);
        sonido.reproducir("muerte");
    }

    public void sumarMonedas(int n) { monedas += n; }

    public Jugador     getJugador()  { return jugador; }
    public Mazmorra    getMazmorra() { return mazmorra; }
    public GestorSonido getSonido()  { return sonido; }
    public int getMonedas()          { return monedas; }
    public int getPiso()             { return piso; }
    public boolean estaTerminado()   { return terminado; }
}
