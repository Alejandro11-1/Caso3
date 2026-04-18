public class BuzonIlimitado extends Buzon {

    public BuzonIlimitado(String nombre) {
        super(nombre);
    }
    @Override
    public synchronized void depositar(Evento evento) throws InterruptedException {
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