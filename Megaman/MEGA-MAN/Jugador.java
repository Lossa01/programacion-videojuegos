import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot y MouseInfo)

/**
 * Personaje jugable (Mega Man).
 *
 * Relación general del diseño:
 *   Entrada (teclado) -> Estado (enum) -> Comportamiento (movimiento) -> Representación visual (sprite)
 *
 * Drivers y concerns abordados:
 *  - Jugabilidad: la lectura del teclado, el movimiento y la animación están separados en métodos.
 *  - Respuesta visual: cada estado se asocia a uno o más sprites.
 *  - Movimiento natural: el salto se modela con una ecuación cuadrática dependiente del tiempo.
 *  - Mantenibilidad: se usa un enum para los estados en vez de números mágicos.
 *  - Fluidez: un contador controla cada cuántos ciclos cambia el frame de la caminata.
 *  - Extensibilidad: separar SALTANDO y CAYENDO (Desafío 2) permite animaciones distintas.
 */
public class Jugador extends Actor
{
    // ---- Estados posibles del jugador (Desafío 2: se incluye CAYENDO) ----
    private enum Estado
    {
        QUIETO,
        CAMINANDO_DERECHA,
        CAMINANDO_IZQUIERDA,
        SALTANDO,
        CAYENDO
    }

    private Estado estado = Estado.QUIETO;

    // ---- Imágenes / sprites ----
    private GreenfootImage imagenQuieto;
    private GreenfootImage imagenSalto;
    private GreenfootImage imagenCaida;
    private GreenfootImage[] caminar;

    // ---- Control de la animación de caminata ----
    private int frameActual = 0;
    private int contadorAnimacion = 0;
    private final int VELOCIDAD_ANIMACION = 6; // 3 = rápida, 6 = media, 10 = lenta

    // ---- Variables del salto (movimiento vertical) ----
    private boolean saltando = false;
    private int tiempoSalto = 0;
    private int yInicial;

    private double velocidadInicial = 12.0; // intensidad inicial del salto
    private double gravedad = 1.0;          // rapidez con que vuelve al suelo

    // ---- Velocidad horizontal ----
    private final int VELOCIDAD_X = 4;

    public Jugador()
    {
        cargarImagenes();
    }

    /**
     * Carga todos los sprites desde la carpeta images/.
     */
    private void cargarImagenes()
    {
        imagenQuieto = new GreenfootImage("idle.png");
        imagenSalto  = new GreenfootImage("jump.png");
        imagenCaida  = new GreenfootImage("fall.png");

        caminar = new GreenfootImage[4];
        caminar[0] = new GreenfootImage("walk1.png");
        caminar[1] = new GreenfootImage("walk2.png");
        caminar[2] = new GreenfootImage("walk3.png");
        caminar[3] = new GreenfootImage("walk4.png");

        setImage(imagenQuieto);
    }

    /**
     * Ciclo principal: se ejecuta en cada frame del juego.
     * Entrada -> Estado -> Movimiento -> Animación
     */
    public void act()
    {
        controlarMovimientoHorizontal();
        controlarSalto();
        actualizarSalto();
        actualizarAnimacion();
    }

    /**
     * Lee las flechas y mueve al jugador en el eje X.
     * Solo cambia el estado de caminata si NO está saltando.
     */
    private void controlarMovimientoHorizontal()
    {
        if (Greenfoot.isKeyDown("right"))
        {
            setLocation(getX() + VELOCIDAD_X, getY());
            if (!saltando)
            {
                estado = Estado.CAMINANDO_DERECHA;
            }
        }
        else if (Greenfoot.isKeyDown("left"))
        {
            setLocation(getX() - VELOCIDAD_X, getY());
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

    /**
     * Inicia el salto al presionar espacio (solo si no está ya saltando).
     */
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

    /**
     * Calcula la posición vertical con la ecuación cuadrática:
     *   y(t) = yInicial - v0*t + 0.5*g*t^2
     *
     * Desafío 2: mientras t < v0/g el personaje sube (SALTANDO);
     * después baja (CAYENDO). El punto más alto ocurre en t = v0/g.
     */
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

            // Distinguir subida de bajada
            if (tiempoSalto < velocidadInicial / gravedad)
            {
                estado = Estado.SALTANDO;
            }
            else
            {
                estado = Estado.CAYENDO;
            }

            // Aterrizaje: cuando vuelve (o pasa) la altura inicial
            if (y >= yInicial && tiempoSalto > 1)
            {
                y = yInicial;
                saltando = false;
                estado = Estado.QUIETO;
            }

            setLocation(getX(), (int) y);
        }
    }

    /**
     * Asocia cada estado con su sprite correspondiente.
     * Estado -> Sprite
     */
    private void actualizarAnimacion()
    {
        if (estado == Estado.QUIETO)
        {
            setImage(imagenQuieto);
        }
        else if (estado == Estado.CAMINANDO_DERECHA)
        {
            // Los sprites originales miran a la IZQUIERDA,
            // así que al ir a la derecha los espejamos.
            animarCaminata(true);
        }
        else if (estado == Estado.CAMINANDO_IZQUIERDA)
        {
            animarCaminata(false);
        }
        else if (estado == Estado.SALTANDO)
        {
            setImage(imagenSalto);
        }
        else if (estado == Estado.CAYENDO)
        {
            setImage(imagenCaida);
        }
    }

    /**
     * Avanza la animación de caminata a una velocidad controlada por un contador.
     * @param espejar si es true, voltea el sprite horizontalmente.
     */
    private void animarCaminata(boolean espejar)
    {
        contadorAnimacion++;

        if (contadorAnimacion >= VELOCIDAD_ANIMACION)
        {
            frameActual++;

            if (frameActual >= caminar.length)
            {
                frameActual = 0;
            }

            GreenfootImage imagen =
                new GreenfootImage(caminar[frameActual]);

            if (espejar)
            {
                imagen.mirrorHorizontally();
            }

            setImage(imagen);
            contadorAnimacion = 0;
        }
    }
}
