import greenfoot.*;

/** Cinco perdigones en abanico: mucho dano de cerca, cara en energia. */
public class Escopeta extends Arma
{
    private static final int PERDIGONES = 5;
    private static final double APERTURA = 9;   // grados entre perdigones

    public Escopeta()
    {
        super(32, 9, 8, 13);
    }

    protected void crearProyectiles(JuegoWorld mundo, int x, int y,
                                    double angulo, boolean deJugador)
    {
        double inicio = angulo - (APERTURA * (PERDIGONES - 1)) / 2.0;

        for (int i = 0; i < PERDIGONES; i++)
        {
            double a = inicio + i * APERTURA;
            Proyectil p = new Proyectil(a, velocidadBala, danio, deJugador);
            p.setAlcance(45);   // los perdigones no llegan tan lejos
            mundo.addObject(p, x, y);
        }
    }

    public String getNombre() { return "Escopeta"; }

    public GreenfootImage getImagen() { return FabricaImagenes.escopeta(); }
}
