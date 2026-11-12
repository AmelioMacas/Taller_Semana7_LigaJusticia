package ligajusticia;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Clase que administra la lista de héroes.
 *
 * Estructura de datos elegida: LinkedHashMap<String, Heroe>
 *  - La clave es el ID del héroe, así buscar y modificar por ID es directo (rápido).
 *  - No permite IDs repetidos.
 *  - Conserva el orden en que se registraron los héroes.
 */
public class GestorHeroes {

    private LinkedHashMap<String, Heroe> heroes = new LinkedHashMap<>();

    // Registra un héroe. Devuelve false si el ID ya existe.
    public boolean registrar(Heroe heroe) {
        if (heroes.containsKey(heroe.getId())) {
            return false;
        }
        heroes.put(heroe.getId(), heroe);
        return true;
    }

    // Busca un héroe por su ID. Devuelve null si no existe.
    public Heroe buscar(String id) {
        return heroes.get(id);
    }

    // Modifica los datos del héroe con ese ID. Devuelve false si no existe.
    public boolean modificar(Heroe heroe) {
        if (!heroes.containsKey(heroe.getId())) {
            return false;
        }
        heroes.put(heroe.getId(), heroe);
        return true;
    }

    // Devuelve todos los héroes registrados
    public ArrayList<Heroe> listar() {
        return new ArrayList<>(heroes.values());
    }

    public int cantidad() {
        return heroes.size();
    }
}
