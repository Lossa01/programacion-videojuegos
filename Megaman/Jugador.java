import greenfoot.*;

public class Jugador extends Actor
{
    private enum Estado
    {
        QUIETO,
        CAMINANDO_DERECHA,
        CAMINANDO_IZQUIERDA,
        SALTANDO
    }

    private Estado estado = Estado.QUIETO;

    private GreenfootImage imagenQuieto;
    private GreenfootImage[] caminar;

    private int frameActual = 0;
    private int contadorAnimacion = 0;

    private boolean saltando = false;
    private int tiempoSalto = 0;
    private int yInicial;

    private double velocidadInicial = 12.0;
    private double gravedad = 1.0;

    public Jugador()
    {
        cargarImagenes();
    }

    private void cargarImagenes()
    {
        imagenQuieto = new GreenfootImage("idle.png");

        caminar = new GreenfootImage[4];

        caminar[0] = new GreenfootImage("walk1.png");
        caminar[1] = new GreenfootImage("walk2.png");
        caminar[2] = new GreenfootImage("walk3.png");
        caminar[3] = new GreenfootImage("walk4.png");

        setImage(imagenQuieto);
    }

    public void act()
    {
        controlarMovimientoHorizontal();
        controlarSalto();
        actualizarSalto();
        actualizarAnimacion();
    }

    private void controlarMovimientoHorizontal()
    {
        if (Greenfoot.isKeyDown("right"))
        {
            setLocation(getX() + 4, getY());

            if (!saltando)
            {
                estado = Estado.CAMINANDO_DERECHA;
            }
        }
        else if (Greenfoot.isKeyDown("left"))
        {
            setLocation(getX() - 4, getY());

            if (!saltando)
            {
                estado = Estado.CAMINANDO_IZQUIERDA;
            }
        }
        else
        {
            if (!saltando)
            {
                estado = Estado.QUIETO;
            }
        }
    }

    private void controlarSalto()
    {
        if (Greenfoot.isKeyDown("space") && !saltando)
        {
            iniciarSalto();
        }
    }

    private void iniciarSalto()
    {
        saltando = true;
        tiempoSalto = 0;
        yInicial = getY();

        estado = Estado.SALTANDO;
    }

    private void actualizarSalto()
    {
        if (saltando)
        {
            tiempoSalto++;

            double y =
                yInicial
                - velocidadInicial * tiempoSalto
                + 0.5 * gravedad
                * tiempoSalto * tiempoSalto;

            if (y >= yInicial && tiempoSalto > 1)
            {
                y = yInicial;

                saltando = false;
                estado = Estado.QUIETO;
            }

            setLocation(
                getX(),
                (int) y
            );
        }
    }

    private void actualizarAnimacion()
    {
        if (estado == Estado.QUIETO)
        {
            setImage(imagenQuieto);
        }
        else if (estado == Estado.CAMINANDO_DERECHA)
        {
            animarCaminata(false);
        }
        else if (estado == Estado.CAMINANDO_IZQUIERDA)
        {
            animarCaminata(true);
        }
    }

    private void animarCaminata(boolean izquierda)
    {
        contadorAnimacion++;

        if (contadorAnimacion >= 6)
        {
            frameActual++;

            if (frameActual >= caminar.length)
            {
                frameActual = 0;
            }

            GreenfootImage imagen =
                new GreenfootImage(caminar[frameActual]);

            if (izquierda)
            {
                imagen.mirrorHorizontally();
            }

            setImage(imagen);

            contadorAnimacion = 0;
        }
    }
}