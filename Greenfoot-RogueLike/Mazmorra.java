import greenfoot.*;

/**
 * Genera y guarda el piso completo: una grilla de salas conectadas por puertas.
 *
 * La generacion es un "camino aleatorio": partimos del centro y vamos
 * caminando a salas vecinas hasta colocar la cantidad pedida. Despues abrimos
 * puertas entre cada par de salas que quedaron pegadas, marcamos la mas lejana
 * como sala del jefe y una sin salida como sala del cofre.
 */
public class Mazmorra
{
    public static final int LADO = 5;   // grilla maxima de salas: 5x5

    private Sala[][] salas = new Sala[LADO][LADO];
    private int colActual;
    private int filaActual;
    private int piso;
    private int colsSala;
    private int filasSala;

    public Mazmorra(int piso, int colsSala, int filasSala)
    {
        this.piso = piso;
        this.colsSala = colsSala;
        this.filasSala = filasSala;
        generar();
    }

    private void generar()
    {
        int objetivo = Math.min(6 + piso, 11);

        int c = LADO / 2;
        int f = LADO / 2;
        colActual = c;
        filaActual = f;

        salas[c][f] = new Sala(Sala.INICIO, colsSala, filasSala);
        salas[c][f].marcarLimpiada();   // en la de inicio no aparecen enemigos
        int puestas = 1;

        // camino aleatorio
        int intentos = 0;
        while (puestas < objetivo && intentos < 500)
        {
            intentos++;
            int dir = Greenfoot.getRandomNumber(4);
            int nc = c;
            int nf = f;

            if (dir == Sala.ARRIBA)         nf--;
            else if (dir == Sala.ABAJO)     nf++;
            else if (dir == Sala.IZQUIERDA) nc--;
            else                            nc++;

            if (nc < 0 || nf < 0 || nc >= LADO || nf >= LADO) continue;

            c = nc;
            f = nf;

            if (salas[c][f] == null)
            {
                salas[c][f] = new Sala(Sala.NORMAL, colsSala, filasSala);
                salas[c][f].setEnemigosPorGenerar(3 + Greenfoot.getRandomNumber(3) + piso);
                puestas++;
            }
        }

        marcarSalasEspeciales();
        conectarPuertas();
    }

    /** La sala mas lejana del inicio es la del jefe; otra lejana lleva cofre. */
    private void marcarSalasEspeciales()
    {
        int ci = LADO / 2;
        int fi = LADO / 2;

        int mejorC = -1, mejorF = -1, mejorDist = -1;
        int segC = -1, segF = -1, segDist = -1;

        for (int c = 0; c < LADO; c++)
        {
            for (int f = 0; f < LADO; f++)
            {
                if (salas[c][f] == null || salas[c][f].getTipo() == Sala.INICIO) continue;

                int dist = Math.abs(c - ci) + Math.abs(f - fi);
                if (dist > mejorDist)
                {
                    segC = mejorC; segF = mejorF; segDist = mejorDist;
                    mejorC = c; mejorF = f; mejorDist = dist;
                }
                else if (dist > segDist)
                {
                    segC = c; segF = f; segDist = dist;
                }
            }
        }

        if (mejorC >= 0)
        {
            Sala jefe = new Sala(Sala.JEFE, colsSala, filasSala);
            jefe.setEnemigosPorGenerar(4 + piso);
            salas[mejorC][mejorF] = jefe;
        }
        if (segC >= 0)
        {
            Sala cofre = new Sala(Sala.COFRE, colsSala, filasSala);
            cofre.setEnemigosPorGenerar(2 + Greenfoot.getRandomNumber(2));
            salas[segC][segF] = cofre;
        }
    }

    /** Abre puertas entre cada par de salas contiguas que existan. */
    private void conectarPuertas()
    {
        for (int c = 0; c < LADO; c++)
        {
            for (int f = 0; f < LADO; f++)
            {
                if (salas[c][f] == null) continue;

                if (f > 0        && salas[c][f - 1] != null) salas[c][f].abrirPuerta(Sala.ARRIBA);
                if (f < LADO - 1 && salas[c][f + 1] != null) salas[c][f].abrirPuerta(Sala.ABAJO);
                if (c > 0        && salas[c - 1][f] != null) salas[c][f].abrirPuerta(Sala.IZQUIERDA);
                if (c < LADO - 1 && salas[c + 1][f] != null) salas[c][f].abrirPuerta(Sala.DERECHA);
            }
        }
    }

    /** Intenta moverse a la sala vecina. Devuelve true si se pudo. */
    public boolean mover(int lado)
    {
        int c = colActual;
        int f = filaActual;

        if (lado == Sala.ARRIBA)         f--;
        else if (lado == Sala.ABAJO)     f++;
        else if (lado == Sala.IZQUIERDA) c--;
        else                             c++;

        if (c < 0 || f < 0 || c >= LADO || f >= LADO) return false;
        if (salas[c][f] == null) return false;

        colActual = c;
        filaActual = f;
        return true;
    }

    public Sala getSalaActual()      { return salas[colActual][filaActual]; }
    public Sala getSala(int c, int f)
    {
        if (c < 0 || f < 0 || c >= LADO || f >= LADO) return null;
        return salas[c][f];
    }

    public int getColActual()  { return colActual; }
    public int getFilaActual() { return filaActual; }
    public int getPiso()       { return piso; }

    /** Cuenta cuantas salas del piso ya fueron limpiadas (para el HUD). */
    public int salasLimpiadas()
    {
        int n = 0;
        for (int c = 0; c < LADO; c++)
            for (int f = 0; f < LADO; f++)
                if (salas[c][f] != null && salas[c][f].estaLimpiada()) n++;
        return n;
    }

    public int totalSalas()
    {
        int n = 0;
        for (int c = 0; c < LADO; c++)
            for (int f = 0; f < LADO; f++)
                if (salas[c][f] != null) n++;
        return n;
    }
}
