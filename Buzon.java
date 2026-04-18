import java.util.LinkedList;

public abstract class Buzon {
    protected LinkedList<Evento> cola;
    protected String nombre;

    public Buzon(String nombre) {
        this.nombre = nombre;
        this.cola = new LinkedList<Evento>();
    }
    public String getNombre() {
        return nombre;
    }
    public synchronized boolean estaVacio() {
        return cola.isEmpty();
    }
    public synchronized int cantidadEventos() {
        return cola.size();
    }

    public abstract void depositar(Evento evento) throws InterruptedException;

    public abstract Evento retirar() throws InterruptedException;
}