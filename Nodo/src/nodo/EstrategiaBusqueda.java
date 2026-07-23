package nodo;

import java.util.List;

public interface EstrategiaBusqueda {
    List<Nodo> buscarRuta(Grafo grafo, Nodo inicio, Nodo fin);
}
