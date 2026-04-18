public class Clasificador extends Thread {
    private int idClasificador;
    private BuzonLimitado buzonClasificacion;
    private BuzonLimitado[] buzonesConsolidacion;
    private int numeroClasificadores;

    private static int clasificadoresTerminados = 0;

    public Clasificador(int idClasificador, BuzonLimitado buzonClasificacion,
                        BuzonLimitado[] buzonesConsolidacion, int numeroClasificadores) {
        this.idClasificador = idClasificador;
        this.buzonClasificacion = buzonClasificacion;
        this.buzonesConsolidacion = buzonesConsolidacion;
        this.numeroClasificadores = numeroClasificadores;
    }
    private static synchronized boolean registrarTerminacion(int numeroClasificadores) {
        clasificadoresTerminados++;
        return clasificadoresTerminados == numeroClasificadores;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Evento evento = buzonClasificacion.retirar();
                if (evento.esFin()) {
                    System.out.println("[Clasificador " + idClasificador + "] Recibió evento de fin.");

                    boolean ultimo= registrarTerminacion(numeroClasificadores);

                    if (ultimo == true) {
                        for (int i = 0; i < buzonesConsolidacion.length; i++) {
                            buzonesConsolidacion[i].depositar(Evento.crearEventoFin());
                            System.out.println("[Clasificador " + idClasificador+ "] Envió evento de fin al servidor " + (i + 1) + ".");
                        }
                    }
                    break;
                }

                int servidorDestino = evento.getTipo() - 1;
                buzonesConsolidacion[servidorDestino].depositar(evento);

                System.out.println("[Clasificador " + idClasificador + "] Envió evento "
                        + evento.getIdEvento() + " al servidor " + evento.getTipo() + ".");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        System.out.println("[Clasificador " + idClasificador + "] Finalizó.");
    }
}