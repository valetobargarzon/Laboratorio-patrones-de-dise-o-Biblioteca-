
package biblioteca;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        // ESTACION 2: Crear y mostrar las salas

        List<Sala> salas = List.of(
                new Sala("A", "Sala A", 2),
                new Sala("B", "Sala B", 4),
                new Sala("C", "Sala C", 6),
                new Sala("D", "Sala D", 8)
        );

        System.out.println("Salas de la biblioteca:");
        salas.forEach(System.out::println);


        // ESTACION 3: Prueba de Builder
        // Se ejecutara cuando tengamos las clases de estado.

        /*
        Reserva reserva = new ReservaBuilder()
                .estudiante(new Estudiante("E001", "Laura"))
                .fecha(java.time.LocalDate.now().plusDays(7))
                .horaInicio(java.time.LocalTime.of(14, 0))
                .duracionHoras(2)
                .cantidadPersonas(4)
                .sala(new Sala("B", "Sala B", 4))
                .build();

        System.out.println(reserva);
        */


        // ESTACION 4: Pruebas de Strategy

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
    }
}
