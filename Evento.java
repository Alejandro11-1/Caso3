public class Evento {
    private String idEvento;
    private int idSensor;
    private int secuencial;
    private int tipo;
    private boolean fin;

    public Evento(String idEvento, int idSensor, int secuencial, int tipo, boolean fin){
        this.idEvento = idEvento;
        this.idSensor = idSensor;
        this.secuencial = secuencial;
        this.tipo = tipo;
        this.fin = fin;
    }
    public String getIdEvento(){
        return idEvento;
    }
    public int getIdSensor() {
        return idSensor;
    }
    public int getSecuencial() {
        return secuencial;
    }

    public int getTipo() {
        return tipo;
    }

    public boolean esFin() {
        return fin;
    }
    public static Evento crearEventoFin() {
        return new Evento("FIN", -1, -1, -1, true);
    }
}