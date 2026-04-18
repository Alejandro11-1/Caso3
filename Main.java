public class Main {
    public static void main(String[] args) {
        try {
            Configuracion configuracion = new Configuracion("config.txt");

            int numeroSensores = configuracion.getNumeroSensores();
            int numeroBaseEventos = configuracion.getNumeroBaseEventos();
            int numeroClasificadores = configuracion.getNumeroClasificadores();
            int numeroServidores = configuracion.getNumeroServidores();
            int capacidadBuzonClasificacion = configuracion.getCapacidadBuzonClasificacion();
            int capacidadBuzonConsolidacion = configuracion.getCapacidadBuzonConsolidacion();
            int totalEventos = configuracion.getTotalEventos();

            BuzonIlimitado buzonEntrada = new BuzonIlimitado("BuzonEntrada");
            BuzonIlimitado buzonAlertas = new BuzonIlimitado("BuzonAlertas");
            BuzonLimitado buzonClasificacion = new BuzonLimitado("BuzonClasificacion", capacidadBuzonClasificacion);
            BuzonLimitado[] buzonesConsolidacion = new BuzonLimitado[numeroServidores];
            for (int i = 0; i < numeroServidores; i++) {
                buzonesConsolidacion[i] = new BuzonLimitado("BuzonServidor" + (i + 1), capacidadBuzonConsolidacion);
            }

            Sensor[] sensores = new Sensor[numeroSensores];
            for (int i = 0; i < numeroSensores; i++) {
                int idSensor = i + 1;
                int cantidadEventos = numeroBaseEventos * idSensor;
                sensores[i] = new Sensor(idSensor, cantidadEventos, numeroServidores, buzonEntrada);
            }

            BrokerAnalizador broker = new BrokerAnalizador(
                    buzonEntrada,
                    buzonAlertas,
                    buzonClasificacion,
                    totalEventos
            );

            Administrador administrador = new Administrador(
                    buzonAlertas,
                    buzonClasificacion,
                    numeroClasificadores
            );

            Clasificador[] clasificadores = new Clasificador[numeroClasificadores];
            for (int i = 0; i < numeroClasificadores; i++) {
                clasificadores[i] = new Clasificador(
                        i + 1,
                        buzonClasificacion,
                        buzonesConsolidacion,
                        numeroClasificadores
                );
            }

            ServidorConsolidacion[] servidores = new ServidorConsolidacion[numeroServidores];
            for (int i = 0; i < numeroServidores; i++) {
                servidores[i] = new ServidorConsolidacion(i + 1, buzonesConsolidacion[i]);
            }

            System.out.println("=== INICIO DEL SISTEMA IoT ===");
            System.out.println("Sensores: " + numeroSensores);
            System.out.println("Clasificadores: " + numeroClasificadores);
            System.out.println("Servidores: " + numeroServidores);
            System.out.println("Total de eventos esperados: " + totalEventos);
            System.out.println();

            for (int i = 0; i < numeroServidores; i++) {
                servidores[i].start();
            }

            for (int i = 0; i < numeroClasificadores; i++) {
                clasificadores[i].start();
            }

            administrador.start();
            broker.start();

            for (int i = 0; i < numeroSensores; i++) {
                sensores[i].start();
            }

            for (int i = 0; i < numeroSensores; i++) {
                sensores[i].join();
            }

            broker.join();
            administrador.join();

            for (int i = 0; i < numeroClasificadores; i++) {
                clasificadores[i].join();
            }

            for (int i = 0; i < numeroServidores; i++) {
                servidores[i].join();
            }

            System.out.println();
            System.out.println("=== SISTEMA FINALIZADO CORRECTAMENTE ===");
            System.out.println("Buzón de entrada vacío: " + buzonEntrada.estaVacio());
            System.out.println("Buzón de alertas vacío: " + buzonAlertas.estaVacio());
            System.out.println("Buzón de clasificación vacío: " + buzonClasificacion.estaVacio());

            for (int i = 0; i < numeroServidores; i++) {
                System.out.println("Buzón de consolidación del servidor " + (i + 1)
                        + " vacío: " + buzonesConsolidacion[i].estaVacio());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}