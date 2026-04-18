import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Configuracion {
    private int numeroSensores;
    private int numeroBaseEventos;
    private int numeroClasificadores;
    private int numeroServidores;
    private int capacidadBuzonClasificacion;
    private int capacidadBuzonConsolidacion;

    public Configuracion(String nombreArchivo) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
        numeroSensores = Integer.parseInt(br.readLine());
        numeroBaseEventos = Integer.parseInt(br.readLine());
        numeroClasificadores = Integer.parseInt(br.readLine());
        numeroServidores = Integer.parseInt(br.readLine());
        capacidadBuzonClasificacion = Integer.parseInt(br.readLine());
        capacidadBuzonConsolidacion = Integer.parseInt(br.readLine());
        br.close();
    }

    public int getNumeroSensores() {
        return numeroSensores;
    }
    public int getNumeroBaseEventos() {
        return numeroBaseEventos;
    }
    public int getNumeroClasificadores(){
        return numeroClasificadores;
    }

    public int getNumeroServidores() {
        return numeroServidores;
    }

    public int getCapacidadBuzonClasificacion(){
        return capacidadBuzonClasificacion;
    }

    public int getCapacidadBuzonConsolidacion() {
        return capacidadBuzonConsolidacion;
    }

    public int getTotalEventos() {
        int total = 0;
        for (int i = 1; i <= numeroSensores; i++) {
            total += numeroBaseEventos * i;
        }
        return total;
    }
}