package nodo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class BusquedaDFS implements EstrategiaBusqueda {

    @Override
    public List<Nodo> buscarRuta(Grafo grafo, Nodo inicio, Nodo fin) {
        if (inicio == null || fin == null) {
            return new ArrayList<>();
        }

        grafo.limpiarEstados();

        Stack<Nodo> pila = new Stack<>();
        pila.push(inicio);

        boolean encontrado = false;

        while (!pila.isEmpty()) {
            Nodo actual = pila.pop();

            if (actual.getEstado().equals("NO_DESCUBIERTO")) {
                actual.setEstado("VISITADO");

                if (actual.getId() == fin.getId()) {
                    encontrado = true;
                    break;
                }

                for (Nodo vecino : grafo.obtenerVecinos(actual)) {
                    if (vecino.getEstado().equals("NO_DESCUBIERTO")) {
                        vecino.setPadre(actual);
                        pila.push(vecino);
                    }
                }
            }
        }

        if (encontrado) {
            return reconstruirRuta(fin);
        }

        return new ArrayList<>();
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
