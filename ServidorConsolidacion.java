import java.util.Random;

public class ServidorConsolidacion extends Thread {
    private int idServidor;
    private BuzonLimitado buzonConsolidacion;
    private Random random;

    public ServidorConsolidacion(int idServidor, BuzonLimitado buzonConsolidacion) {
        this.idServidor = idServidor;
        this.buzonConsolidacion = buzonConsolidacion;
        this.random = new Random();
    }
    @Override
    public void run() {
        while (true) {
            try {
                Evento evento = buzonConsolidacion.retirar();

                if (evento.esFin()) {
                    System.out.println("[Servidor " + idServidor + "] Recibió evento de fin.");
                    break;
                }
                int tiempoProcesamiento = random.nextInt(901) + 100;
                System.out.println("[Servidor " + idServidor + "] Procesando evento "
                + evento.getIdEvento() + " del sensor " + evento.getIdSensor()+ 
                " durante " + tiempoProcesamiento + " ms.");

                Thread.sleep(tiempoProcesamiento);

                System.out.println("[Servidor " + idServidor + "] Terminó de procesar evento "+ evento.getIdEvento());

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        System.out.println("[Servidor " + idServidor + "] Finalizó.");
    }
}