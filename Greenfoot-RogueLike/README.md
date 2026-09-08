# Greenfoot RogueLike

Rogue-like top-down estilo Soul Knight hecho en Greenfoot (Java) para el curso
de Programacion de Videojuegos.

Todo el arte esta dibujado por codigo en `FabricaImagenes` y los efectos de
sonido de `sounds/` fueron sintetizados por codigo. No se usa ningun asset de
terceros.

## Como jugar

- **WASD** mover (8 direcciones)
- **Mouse** apuntar
- **Click o ESPACIO** disparar
- **R** reiniciar cuando aparece GAME OVER

Cada sala se cierra hasta que matas a todos los enemigos. Cuando la limpias las
puertas se ponen verdes y podes pasar a la sala siguiente caminando hacia ellas.

## Como abrirlo

`File -> Open...` en Greenfoot y elegir esta carpeta. Despues `Compile`, click
derecho en `JuegoWorld` -> `new JuegoWorld()` -> `Run`.

## Arquitectura

| Clase | Responsabilidad |
|---|---|
| `JuegoWorld` | Mundo principal: dibuja la sala, resuelve colisiones contra muros, cambia de sala |
| `Mazmorra` | Genera el piso completo (grilla de salas) y sabe en cual estas |
| `Sala` | Datos de una sala: grilla de muros, puertas, tipo, si esta limpiada |
| `Personaje` | Base de jugador y enemigos: vida, escudo regenerable, invulnerabilidad |
| `Jugador` | Movimiento, apuntado con mouse, disparo, energia |
| `Enemigo` | Base de enemigos con su IA comun |
| `EnemigoMelee` / `EnemigoTirador` | Dos comportamientos distintos |
| `Arma` + `Pistola` / `Escopeta` | Sistema de armas: agregar una nueva es solo una subclase |
| `Proyectil` | Balas, tanto del jugador como de los enemigos |
| `ArmaVista` | El arma que se ve girando alrededor del jugador |
| `FabricaImagenes` | Todo el arte dibujado por codigo |
| `HUD` | Barras de estado en pantalla |
| `Minimapa` | Mapa de salas del piso |
| `GestorSonido` | Reproduccion de efectos |

### Decisiones que vale la pena explicar

**Los muros no son actores.** Cada sala tiene 600 tiles. Si cada muro fuera un
`Actor`, Greenfoot tendria que preguntarle a cientos de objetos si chocan en
cada frame. En vez de eso los muros se dibujan sobre el fondo del mundo y la
colision se resuelve consultando una grilla `boolean[][]`, que es una
comparacion directa en memoria.

**El movimiento se resuelve por eje separado.** Se intenta mover en X y despues
en Y de forma independiente. Asi, si chocas en diagonal contra una pared, igual
te deslizas por el eje que quedo libre en vez de quedarte pegado.

**El jugador no rota, rota el arma.** Un sprite humanoide girando 360 grados se
ve mal en top-down. Por eso `ArmaVista` es un actor aparte que gira y se espeja
segun hacia donde apuntas.

**Las balas guardan su posicion en `double`.** Greenfoot posiciona actores en
enteros; si acumularamos el movimiento diagonal en `int`, el error de redondeo
desviaria el tiro.

## Quien hace que

- **Camilo**: mundo, mazmorra, jugador, enemigos, armas, proyectiles, arte
- **Franco**: `HUD`, `Minimapa`, `GestorSonido` (ver `Guia-para-Franco.md`)
