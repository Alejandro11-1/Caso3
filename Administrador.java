import java.util.Random;

public class Administrador extends Thread {
    private BuzonIlimitado buzonAlertas;
    private BuzonLimitado buzonClasificacion;
    private int numeroClasificadores;
    private Random random;

    public Administrador(BuzonIlimitado buzonAlertas, BuzonLimitado buzonClasificacion, int numeroClasificadores) {
        this.buzonAlertas = buzonAlertas;
        this.buzonClasificacion = buzonClasificacion;
        this.numeroClasificadores = numeroClasificadores;
        this.random = new Random();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Evento evento = buzonAlertas.retirar();
                if (evento.esFin()) {
                    System.out.println("[Administrador] Recibió evento de fin.");

                    for (int i = 0; i < numeroClasificadores; i++) {
                        buzonClasificacion.depositar(Evento.crearEventoFin());
                        System.out.println("[Administrador] Envió evento de fin al buzón de clasificación.");
                    }
                    break;
                }
                int numeroAleatorio = random.nextInt(21);
                //multiplo de 4
                if (numeroAleatorio % 4 == 0) {
                    buzonClasificacion.depositar(evento);
                    System.out.println("[Administrador] Evento " + evento.getIdEvento()+ " pasó la inspección y fue enviado al buzón de clasificación.");
                } else {
                    System.out.println("[Administrador] Evento " + evento.getIdEvento()+ " confirmado como malicioso y descartado.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        System.out.println("[Administrador] Finalizó.");
    }
}