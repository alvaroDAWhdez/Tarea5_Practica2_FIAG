import java.util.Random;

/**
 * Hilo consumidor: extrae elementos del buffer hasta que es interrumpido.
 */
public class Consumidor implements Runnable {

    private final BufferCompartido buffer;
    private final Random random = new Random();

    /**
     * Crea un consumidor asociado a un buffer.
     *
     * @param buffer buffer compartido del que se extraen los elementos
     */
    public Consumidor(BufferCompartido buffer) {
        this.buffer = buffer;
    }

    /**
     * Bucle principal: consume elementos hasta que el hilo es interrumpido.
     */
    @Override
    public void run() {
        String nombre = Thread.currentThread().getName();
        System.out.println("[" + nombre + "] iniciado");
        try {
            while (!Thread.currentThread().isInterrupted()) {
                buffer.consumir(nombre);
                Thread.sleep(200 + random.nextInt(500)); // simula procesamiento
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // restaura el estado de interrupción
        }
        System.out.println("[" + nombre + "] terminado ordenadamente");
    }
}
