import greenfoot.*;

/**
 * El personaje que maneja el jugador.
 *
 * WASD para moverse en 8 direcciones, mouse para apuntar, click o espacio para
 * disparar. El sprite NO rota: quien rota es el arma (ver ArmaVista), igual que
 * en Soul Knight. Un monito humanoide girando entero se ve pesimo.
 */
public class Jugador extends Personaje
{
    private static final int RADIO = 11;

    private int energiaMax = 100;
    private int energia = 100;
    private int tickEnergia = 0;
    private int esperaEnergia = 0;

    private Arma arma = new Pistola();
    private double angulo = 0;

    // ultima posicion conocida del mouse: getMouseInfo() devuelve null en los
    // frames en que el mouse no se movio, asi que hay que recordarla
    private int mouseX = 0;
    private int mouseY = 0;
    private boolean botonAbajo = false;

    public Jugador()
    {
        super(100, 40, 4);
        // copia propia: el parpadeo al recibir dano cambia la transparencia,
        // y si compartieramos la imagen parpadearian todos a la vez
        setImage(new GreenfootImage(FabricaImagenes.jugador()));
    }

    protected void addedToWorld(World mundo)
    {
        mouseX = getX() + 50;
        mouseY = getY();
        mundo.addObject(new ArmaVista(this), getX(), getY());
    }

    public void act()
    {
        actualizarEstado();
        actualizarPuntero();
        mover();
        arma.actualizar();
        disparar();
        regenerarEnergia();
    }

    private void actualizarPuntero()
    {
        MouseInfo info = Greenfoot.getMouseInfo();
        if (info != null)
        {
            mouseX = info.getX();
            mouseY = info.getY();
        }

        // Greenfoot no tiene "boton mantenido", asi que lo aproximamos:
        // se presiona -> queda en true, se suelta (click) -> vuelve a false
        if (Greenfoot.mousePressed(null)) botonAbajo = true;
        if (Greenfoot.mouseClicked(null)) botonAbajo = false;

        angulo = Math.toDegrees(Math.atan2(mouseY - getY(), mouseX - getX()));
    }

    private void mover()
    {
        int dx = 0;
        int dy = 0;

        if (Greenfoot.isKeyDown("w")) dy -= 1;
        if (Greenfoot.isKeyDown("s")) dy += 1;
        if (Greenfoot.isKeyDown("a")) dx -= 1;
        if (Greenfoot.isKeyDown("d")) dx += 1;

        if (dx == 0 && dy == 0) return;

        // en diagonal se avanzaria 1.41 veces mas rapido: lo corregimos
        double factor = (dx != 0 && dy != 0) ? 0.7071 : 1.0;
        int mx = (int) Math.round(dx * velocidad * factor);
        int my = (int) Math.round(dy * velocidad * factor);

        moverCon(mx, my, RADIO);
    }

    private void disparar()
    {
        boolean quiereDisparar = botonAbajo || Greenfoot.isKeyDown("space");
        if (!quiereDisparar) return;
        if (!arma.listo()) return;
        if (energia < arma.getCostoEnergia()) return;

        JuegoWorld mundo = (JuegoWorld) getWorld();
        if (mundo == null) return;

        if (arma.disparar(mundo, getX(), getY(), angulo, true))
        {
            energia -= arma.getCostoEnergia();
            esperaEnergia = 45;
            mundo.getSonido().reproducir("disparo");
        }
    }

    private void regenerarEnergia()
    {
        if (esperaEnergia > 0)
        {
            esperaEnergia--;
            return;
        }
        if (energia >= energiaMax) return;

        tickEnergia++;
        if (tickEnergia >= 8)
        {
            tickEnergia = 0;
            energia = Math.min(energiaMax, energia + 1);
        }
    }

    protected void morir()
    {
        JuegoWorld mundo = (JuegoWorld) getWorld();
        if (mundo != null) mundo.gameOver();
        super.morir();
    }

    public double getAngulo()   { return angulo; }
    public Arma   getArma()     { return arma; }
    public void   setArma(Arma a) { arma = a; }
    public int    getEnergia()  { return energia; }
    public int    getEnergiaMax(){ return energiaMax; }
}
