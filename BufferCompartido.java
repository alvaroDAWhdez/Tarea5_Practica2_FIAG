import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Buffer acotado compartido (versión 2) basado en BlockingQueue.
 * La sincronización la gestiona la propia cola: no hay synchronized,
 * wait() ni notifyAll() escritos a mano.
 */
public class BufferCompartido {

    /** Capacidad máxima del buffer. */
    public static final int CAPACIDAD = 5;

    private final BlockingQueue<Integer> cola = new ArrayBlockingQueue<>(CAPACIDAD);

    /**
     * Inserta un elemento. Si el buffer está lleno, el hilo se bloquea en put().
     *
     * @param elemento elemento a insertar
     * @param hilo     nombre del hilo productor (para trazas)
     * @throws InterruptedException si el hilo es interrumpido mientras espera
     */
    public void producir(int elemento, String hilo) throws InterruptedException {
        if (!cola.offer(elemento)) { // no cabe: avisamos y nos bloqueamos
            System.out.println("[" + hilo + "] BLOQUEADO: buffer lleno (" + cola.size() + "/" + CAPACIDAD + ")");
            cola.put(elemento);
        }
        System.out.println("[" + hilo + "] produce " + elemento + " -> buffer " + cola);
    }

    /**
     * Extrae un elemento. Si el buffer está vacío, el hilo se bloquea en take().
     *
     * @param hilo nombre del hilo consumidor (para trazas)
     * @return el elemento extraído
     * @throws InterruptedException si el hilo es interrumpido mientras espera
     */
    public int consumir(String hilo) throws InterruptedException {
        Integer elemento = cola.poll();
        if (elemento == null) { // vacío: avisamos y nos bloqueamos
            System.out.println("[" + hilo + "] BLOQUEADO: buffer vacío (0/" + CAPACIDAD + ")");
            elemento = cola.take();
        }
        System.out.println("[" + hilo + "] consume " + elemento + " <- buffer " + cola);
        return elemento;
    }
}