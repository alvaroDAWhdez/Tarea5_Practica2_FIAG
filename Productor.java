import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Hilo productor: genera números enteros únicos y los deposita en el buffer
 * hasta que es interrumpido.
 */
public class Productor implements Runnable {

    private static final AtomicInteger contador = new AtomicInteger(1);

    private final BufferCompartido buffer;
    private final Random random = new Random();

    /**
     * Crea un productor asociado a un buffer.
     *
     * @param buffer buffer compartido donde se depositan los elementos
     */
    public Productor(BufferCompartido buffer) {
        this.buffer = buffer;
    }

    /**
     * Bucle principal: produce elementos hasta que el hilo es interrumpido.
     */
    @Override
    public void run() {
        String nombre = Thread.currentThread().getName();
        System.out.println("[" + nombre + "] iniciado");
        try {
            while (!Thread.currentThread().isInterrupted()) {
                int elemento = contador.getAndIncrement();
                buffer.producir(elemento, nombre);
                Thread.sleep(100 + random.nextInt(300)); // simula trabajo
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // restaura el estado de interrupción
        }
        System.out.println("[" + nombre + "] terminado ordenadamente");
    }
}
