package nodo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BusquedaBFS implements EstrategiaBusqueda {

    @Override
    public List<Nodo> buscarRuta(Grafo grafo, Nodo inicio, Nodo fin) {
        if (inicio == null || fin == null) {
            return new ArrayList<>();
        }

        grafo.limpiarEstados();

        Queue<Nodo> cola = new LinkedList<>();
        inicio.setEstado("FRONTERA");
        cola.add(inicio);

        boolean encontrado = false;

        while (!cola.isEmpty()) {
            Nodo actual = cola.poll();
            actual.setEstado("VISITADO");

            if (actual.getId() == fin.getId()) {
                encontrado = true;
                break;
            }

            for (Nodo vecino : grafo.obtenerVecinos(actual)) {
                if (vecino.getEstado().equals("NO_DESCUBIERTO")) {
                    vecino.setEstado("FRONTERA");
                    vecino.setPadre(actual);
                    cola.add(vecino);
                }
            }
        }

        if (encontrado) {
            return reconstruirRuta(fin);
        }

        return new ArrayList<>(); // Retorna lista vacía si no existe camino
    }

    private List<Nodo> reconstruirRuta(Nodo destino) {
        List<Nodo> ruta = new ArrayList<>();
        Nodo actual = destino;

        while (actual != null) {
            ruta.add(actual);
            actual = actual.getPadre();
        }

        Collections.reverse(ruta);
        return ruta;
    }
}
