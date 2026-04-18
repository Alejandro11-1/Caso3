import java.util.Random;

public class BrokerAnalizador extends Thread {
    private BuzonIlimitado buzonEntrada;
    private BuzonIlimitado buzonAlertas;
    private BuzonLimitado buzonClasificacion;
    private int totalEventos;
    private Random random;

    public BrokerAnalizador(BuzonIlimitado buzonEntrada, BuzonIlimitado buzonAlertas, BuzonLimitado buzonClasificacion, int totalEventos){
        this.buzonEntrada= buzonEntrada;
        this.buzonAlertas = buzonAlertas;
        this.buzonClasificacion= buzonClasificacion;
        this.totalEventos = totalEventos;
        this.random = new Random();
    }
    
    @Override
    public void run() {
        int eventosProcesados = 0;
        while (eventosProcesados < totalEventos) {
            try {
                Evento evento = buzonEntrada.retirar();
                eventosProcesados++;
                int numeroAleatorio = random.nextInt(201);

                //multiplo de 8
                if (numeroAleatorio % 8 == 0) {
                    buzonAlertas.depositar(evento);
                    System.out.println("[Broker] Evento " + evento.getIdEvento()+ " con número " + numeroAleatorio +" marcado como sospechoso y enviado al buzón de alertas.");
                } else {
                    buzonClasificacion.depositar(evento);
                    System.out.println("[Broker] Evento " + evento.getIdEvento()+ " con número " + numeroAleatorio +" considerado normal y enviado al buzón de clasificación.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        try {
            Evento eventoFin = Evento.crearEventoFin();
            buzonAlertas.depositar(eventoFin);
            System.out.println("[Broker] Envió evento de fin al Administrador.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        System.out.println("[Broker] Finalizó.");
    }
}