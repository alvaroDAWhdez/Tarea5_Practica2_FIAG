import java.util.ArrayList;
import java.util.List;

/**
 * Programa principal: lanza 2 productores y 3 consumidores, los deja
 * funcionar 10 segundos y los detiene de forma ordenada mediante interrupción.
 */
public class Main {

    private static final int NUM_PRODUCTORES = 2;
    private static final int NUM_CONSUMIDORES = 3;
    private static final long DURACION_MS = 10_000;

    /**
     * Punto de entrada del programa.
     *
     * @param args argumentos de línea de comandos (no se usan)
     * @throws InterruptedException si el hilo principal es interrumpido
     */
    public static void main(String[] args) throws InterruptedException {
        BufferCompartido buffer = new BufferCompartido();
        List<Thread> hilos = new ArrayList<>();

        for (int i = 1; i <= NUM_PRODUCTORES; i++) {
            hilos.add(new Thread(new Productor(buffer), "Productor-" + i));
        }
        for (int i = 1; i <= NUM_CONSUMIDORES; i++) {
            hilos.add(new Thread(new Consumidor(buffer), "Consumidor-" + i));
        }

        System.out.println("=== Arrancando hilos ===");
        hilos.forEach(Thread::start);

        Thread.sleep(DURACION_MS);

        System.out.println("=== Solicitando parada ordenada (interrupt) ===");
        hilos.forEach(Thread::interrupt);

        for (Thread h : hilos) {
            h.join();
        }
        System.out.println("=== Todos los hilos han terminado ===");
    }
}
