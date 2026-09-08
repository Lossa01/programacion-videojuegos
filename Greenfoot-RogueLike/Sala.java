import greenfoot.*;

/**
 * Datos de UNA sala de la mazmorra. No es un Actor: es solo informacion.
 * JuegoWorld la lee para dibujar el piso y saber donde hay muros.
 *
 * La grilla de muros es [columna][fila] en unidades de tile, no de pixeles.
 */
public class Sala
{
    public static final int NORMAL = 0;
    public static final int INICIO = 1;
    public static final int COFRE  = 2;
    public static final int JEFE   = 3;
    public static final int TIENDA = 4;

    // lados: 0 arriba, 1 derecha, 2 abajo, 3 izquierda
    public static final int ARRIBA    = 0;
    public static final int DERECHA   = 1;
    public static final int ABAJO     = 2;
    public static final int IZQUIERDA = 3;

    private int tipo;
    private int cols;
    private int filas;
    private boolean[][] muros;
    private boolean[] puertas = new boolean[4];

    private boolean visitada = false;
    private boolean limpiada = false;
    private int enemigosPorGenerar = 0;

    public Sala(int tipo, int cols, int filas)
    {
        this.tipo = tipo;
        this.cols = cols;
        this.filas = filas;
        this.muros = new boolean[cols][filas];
        generarMuros();
    }

    /**
     * Muro perimetral de 1 tile mas algunos obstaculos internos.
     * Se deja libre el centro para que el jugador nunca aparezca encajado.
     */
    private void generarMuros()
    {
        for (int c = 0; c < cols; c++)
            for (int f = 0; f < filas; f++)
                muros[c][f] = (c == 0 || f == 0 || c == cols - 1 || f == filas - 1);

        if (tipo == INICIO) return;   // sala de partida despejada

        int bloques = 3 + Greenfoot.getRandomNumber(4);
        for (int i = 0; i < bloques; i++)
        {
            int ancho = 1 + Greenfoot.getRandomNumber(3);
            int alto  = 1 + Greenfoot.getRandomNumber(3);
            int c0 = 3 + Greenfoot.getRandomNumber(cols - 6 - ancho);
            int f0 = 3 + Greenfoot.getRandomNumber(filas - 6 - alto);

            for (int c = c0; c < c0 + ancho; c++)
                for (int f = f0; f < f0 + alto; f++)
                    if (!esZonaProtegida(c, f)) muros[c][f] = true;
        }
    }

    /**
     * Cruz central protegida: los dos corredores que unen las cuatro puertas
     * nunca se tapan. Sin esto, un obstaculo aleatorio puede dejar una puerta
     * inalcanzable y la sala se vuelve imposible de cruzar.
     */
    private boolean esZonaProtegida(int c, int f)
    {
        boolean corredorVertical   = (c == cols / 2 || c == cols / 2 - 1);
        boolean corredorHorizontal = (f == filas / 2 || f == filas / 2 - 1);
        return corredorVertical || corredorHorizontal;
    }

    /** Abre el hueco de la puerta en el muro perimetral del lado indicado. */
    public void abrirPuerta(int lado)
    {
        puertas[lado] = true;
        int cm = cols / 2;
        int fm = filas / 2;

        if (lado == ARRIBA)         { muros[cm][0] = false; muros[cm - 1][0] = false; }
        else if (lado == ABAJO)     { muros[cm][filas - 1] = false; muros[cm - 1][filas - 1] = false; }
        else if (lado == IZQUIERDA) { muros[0][fm] = false; muros[0][fm - 1] = false; }
        else                        { muros[cols - 1][fm] = false; muros[cols - 1][fm - 1] = false; }
    }

    public boolean esMuro(int c, int f)
    {
        if (c < 0 || f < 0 || c >= cols || f >= filas) return true;
        return muros[c][f];
    }

    public boolean tienePuerta(int lado) { return puertas[lado]; }

    public int  getTipo()          { return tipo; }
    public int  getCols()          { return cols; }
    public int  getFilas()         { return filas; }
    public boolean fueVisitada()   { return visitada; }
    public void marcarVisitada()   { visitada = true; }
    public boolean estaLimpiada()  { return limpiada; }
    public void marcarLimpiada()   { limpiada = true; }

    public int  getEnemigosPorGenerar()      { return enemigosPorGenerar; }
    public void setEnemigosPorGenerar(int n) { enemigosPorGenerar = n; }
}
