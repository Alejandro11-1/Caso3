import java.util.Random;

public class Sensor extends Thread {
    private int idSensor;
    private int cantidadEventos;
    private int numeroServidores;
    private BuzonIlimitado buzonEntrada;
    private Random random;

    public Sensor(int idSensor, int cantidadEventos, int numeroServidores, BuzonIlimitado buzonEntrada) {
        this.idSensor = idSensor;
        this.cantidadEventos = cantidadEventos;
        this.numeroServidores = numeroServidores;
        this.buzonEntrada = buzonEntrada;
        this.random = new Random();
    }
    @Override
    public void run() {
        for (int i = 1; i <= cantidadEventos; i++) {
            String idEvento = "S" + idSensor+"-E" + i;
            int tipo = random.nextInt(numeroServidores) + 1;
            Evento evento = new Evento(idEvento, idSensor, i, tipo, false);

            try {
                buzonEntrada.depositar(evento);
                System.out.println("[Sensor " + idSensor + "] Generó evento " +
                 evento.getIdEvento() +" con tipo " + evento.getTipo());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        System.out.println("[Sensor " + idSensor + "] Finalizó.");
    }
}