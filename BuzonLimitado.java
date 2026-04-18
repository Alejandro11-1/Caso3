public class BuzonLimitado extends Buzon {
    private int capacidadMaxima;

    public BuzonLimitado(String nombre, int capacidadMaxima) {
        super(nombre);
        this.capacidadMaxima = capacidadMaxima;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    @Override
    public synchronized void depositar(Evento evento) throws InterruptedException {
        while (cola.size() == capacidadMaxima) {
            wait();
        }
        cola.addLast(evento);
        System.out.println("[" + nombre + "] Se depositó " + evento);
        notifyAll();
    }
    @Override
    public synchronized Evento retirar() throws InterruptedException {
        while (cola.isEmpty()) {
            wait();
        }
        Evento evento = cola.removeFirst();
        System.out.println("[" + nombre + "] Se retiró " + evento);
        notifyAll();
        return evento;
    }
}