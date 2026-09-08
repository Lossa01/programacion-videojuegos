/**
 * ===================================================================
 *  CLASE DE FRANCO  -  ESTA VERSION ES SOLO UN PROVISORIO
 * ===================================================================
 *
 * Por ahora no suena nada: el metodo recibe el evento y no hace nada.
 *
 * Los .wav ya estan generados en la carpeta sounds/ del proyecto:
 *   disparo.wav, golpe.wav, moneda.wav, muerte.wav, puerta.wav
 *
 * Lo que falta implementar (ver Guia-para-Franco.md):
 *   - reproducir el archivo que corresponde a cada evento
 *   - evitar que se solapen 20 disparos en el mismo frame
 *   - poder silenciar todo con una tecla
 *
 * No es un Actor: es una clase normal que JuegoWorld crea una sola vez.
 */
public class GestorSonido
{
    private boolean activo = true;

    /**
     * Evento puede ser: "disparo", "golpe", "moneda", "muerte", "puerta".
     */
    public void reproducir(String evento)
    {
        if (!activo) return;

        // TODO (Franco): Greenfoot.playSound(evento + ".wav") con control
        // de repeticion, para que no se sature al disparar la escopeta.
    }

    public void setActivo(boolean valor) { activo = valor; }

    public boolean estaActivo() { return activo; }
}
