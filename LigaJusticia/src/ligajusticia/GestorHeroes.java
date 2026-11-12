package ligajusticia;

import java.util.LinkedList;

/**
 * Clase que administra la lista de héroes.
 *
 * Estructura de datos elegida: LinkedList<Heroe> (lista enlazada)
 *  - Crece y se reduce según se necesite: no hay que definir un tamaño máximo.
 *  - Agregar un héroe al final es inmediato (no se mueven los demás elementos).
 *  - Conserva el orden en que se registraron los héroes.
 *  - La búsqueda por ID es secuencial: se recorre la lista nodo por nodo.
 */
public class GestorHeroes {

    private LinkedList<Heroe> heroes = new LinkedList<>();

    // Registra un héroe al final de la lista. Devuelve false si el ID ya existe.
    public boolean registrar(Heroe heroe) {
        if (buscar(heroe.getId()) != null) {
            return false; // ID repetido
        }
        heroes.addLast(heroe);
        return true;
    }

    // Búsqueda secuencial: recorre la lista hasta encontrar el ID.
    // Devuelve el héroe encontrado, o null si no existe.
    public Heroe buscar(String id) {
        for (Heroe h : heroes) {
            if (h.getId().equalsIgnoreCase(id)) {
                return h;
            }
        }
        return null;
    }

    // Modifica los datos del héroe que tiene ese ID. Devuelve false si no existe.
    // El héroe mantiene su misma posición en la lista.
    public boolean modificar(Heroe datosNuevos) {
        Heroe existente = buscar(datosNuevos.getId());
        if (existente == null) {
            return false;
        }
        existente.setNombre(datosNuevos.getNombre());
        existente.setSuperpoder(datosNuevos.getSuperpoder());
        existente.setMision(datosNuevos.getMision());
        existente.setNivelDificultad(datosNuevos.getNivelDificultad());
        existente.setPagoMensual(datosNuevos.getPagoMensual());
        return true;
    }

    // Devuelve todos los héroes registrados, en orden de registro
    public LinkedList<Heroe> listar() {
        return heroes;
    }

    public int cantidad() {
        return heroes.size();
    }
}
