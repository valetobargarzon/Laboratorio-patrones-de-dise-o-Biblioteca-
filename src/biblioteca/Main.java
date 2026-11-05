
package biblioteca;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // ESTACION 2: MOSTRAR LAS SALAS

        List<Sala> salas = List.of(
                new Sala("A", "Sala A", 2),
                new Sala("B", "Sala B", 4),
                new Sala("C", "Sala C", 6),
                new Sala("D", "Sala D", 8)
        );

        System.out.println("=== SALAS DE LA BIBLIOTECA ===");
        salas.forEach(System.out::println);


        // ESTACION 3: PRUEBA POSITIVA DE BUILDER

        System.out.println("\n=== PRUEBA POSITIVA DE BUILDER ===");

        Reserva reserva = new ReservaBuilder()
                .estudiante(new Estudiante("E001", "Laura"))
                .fecha(LocalDate.now().plusDays(7))
                .horaInicio(LocalTime.of(14, 0))
                .duracionHoras(2)
                .cantidadPersonas(4)
                .sala(new Sala("B", "Sala B", 4))
                .build();

        System.out.println(reserva);


        // ESTACION 7: CONFIRMAR UNA RESERVA PENDIENTE

        System.out.println("\n=== PRUEBA DE CONFIRMACION ===");

        System.out.println("Estado antes: " + reserva.getNombreEstado());

        reserva.confirmar();

        System.out.println("Estado despues: " + reserva.getNombreEstado());


        // ESTACION 7: CANCELAR UNA RESERVA CONFIRMADA

        System.out.println("\n=== PRUEBA DE CANCELACION ===");

        System.out.println("Estado antes: " + reserva.getNombreEstado());

        reserva.cancelar();

        System.out.println("Estado despues: " + reserva.getNombreEstado());


        // ESTACION 7: INTENTAR CONFIRMAR UNA RESERVA CANCELADA

        System.out.println("\n=== PRUEBA DE TRANSICION INVALIDA ===");

        System.out.println("Estado antes: " + reserva.getNombreEstado());

        try {
            reserva.confirmar();
        } catch (IllegalStateException ex) {
            System.out.println("Operacion rechazada: " + ex.getMessage());
        }

        System.out.println("Estado despues: " + reserva.getNombreEstado());


        // ESTACION 3: PRUEBA NEGATIVA DE BUILDER

        System.out.println("\n=== PRUEBA NEGATIVA DE BUILDER ===");

        try {
            Reserva reservaIncorrecta = new ReservaBuilder()
                    .estudiante(new Estudiante("E002", "Andres"))
                    .fecha(LocalDate.now().plusDays(7))
                    .horaInicio(LocalTime.of(10, 0))
                    .duracionHoras(2)
                    .cantidadPersonas(4)
                    .sala(new Sala("A", "Sala A", 2))
                    .build();

            System.out.println(reservaIncorrecta);

        } catch (IllegalStateException ex) {
            System.out.println("Reserva rechazada: " + ex.getMessage());
        }


        // ESTACION 4: PRUEBAS DE STRATEGY

        System.out.println("\n=== PRUEBAS DE STRATEGY ===");

        EstrategiaSeleccionSala porCapacidad =
                new SeleccionPorCapacidad();

        System.out.println("Para 4 personas: "
                + porCapacidad.seleccionar(salas, 4).orElse(null));

        EstrategiaSeleccionSala preferida =
                new SeleccionSalaPreferida("C");

        System.out.println("Preferida C: "
                + preferida.seleccionar(salas, 4).orElse(null));

        EstrategiaSeleccionSala preferidaNoAdecuada =
                new SeleccionSalaPreferida("B");

        System.out.println("Preferida B para 7: "
                + preferidaNoAdecuada.seleccionar(salas, 7).orElse(null));


        // ESTACION 7: PRUEBA DE FINALIZACION

        System.out.println("\n=== PRUEBA DE FINALIZACION ===");

        Reserva otra = new ReservaBuilder()
                .estudiante(new Estudiante("E004", "Samuel"))
                .fecha(LocalDate.now().plusDays(10))
                .horaInicio(LocalTime.of(11, 0))
                .duracionHoras(1)
                .cantidadPersonas(2)
                .sala(new Sala("A", "Sala A", 2))
                .build();

        System.out.println("Estado inicial: " + otra.getNombreEstado());


        // ESTACION 6: REGISTRAR LOS OBSERVADORES
        // Deben registrarse ANTES de confirmar la reserva.

        otra.agregarObservador(new NotificadorEstudiante());
        otra.agregarObservador(new RegistroEventos());


        // CAMBIO DE PENDIENTE A CONFIRMADA

        otra.confirmar();

        System.out.println("Estado despues de confirmar: "
                + otra.getNombreEstado());


        // CAMBIO DE CONFIRMADA A FINALIZADA

        otra.finalizar();

        System.out.println("Estado despues de finalizar: "
                + otra.getNombreEstado());


        // ESTACION 7: INTENTAR CANCELAR UNA RESERVA FINALIZADA

        System.out.println("\n=== PRUEBA DE CANCELACION INVALIDA ===");

        System.out.println("Estado antes: " + otra.getNombreEstado());

        try {
            otra.cancelar();
        } catch (IllegalStateException ex) {
            System.out.println("Operacion rechazada: " + ex.getMessage());
        }

        System.out.println("Estado despues: " + otra.getNombreEstado());

    }
}
