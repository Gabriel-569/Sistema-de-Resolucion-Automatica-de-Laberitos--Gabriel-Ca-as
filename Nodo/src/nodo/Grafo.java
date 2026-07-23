package nodo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grafo {
    private List<Nodo> listaNodos;
    private Map<Nodo, List<Arista>> listaAdyacencia;

    public Grafo() {
        this.listaNodos = new ArrayList<>();
        this.listaAdyacencia = new HashMap<>();
    }

    public void agregarNodo(Nodo nodo) {
        if (!listaNodos.contains(nodo)) {
            listaNodos.add(nodo);
            listaAdyacencia.putIfAbsent(nodo, new ArrayList<>());
        }
    }

    public void agregarArista(Nodo origen, Nodo destino) {
        agregarNodo(origen);
        agregarNodo(destino);

        Arista aristaDirecta = new Arista(origen, destino);
        Arista aristaInversa = new Arista(destino, origen);

        listaAdyacencia.get(origen).add(aristaDirecta);
        listaAdyacencia.get(destino).add(aristaInversa);
    }

    public List<Nodo> getNodos() {
        return listaNodos;
    }

    public List<Nodo> obtenerVecinos(Nodo nodo) {
        List<Nodo> vecinos = new ArrayList<>();
        List<Arista> aristas = listaAdyacencia.get(nodo);
        if (aristas != null) {
            for (Arista arista : aristas) {
                vecinos.add(arista.getDestino());
            }
        }
        return vecinos;
    }

    public void limpiarEstados() {
        for (Nodo nodo : listaNodos) {
            nodo.setEstado("NO_DESCUBIERTO");
            nodo.setPadre(null);
        }
    }
}