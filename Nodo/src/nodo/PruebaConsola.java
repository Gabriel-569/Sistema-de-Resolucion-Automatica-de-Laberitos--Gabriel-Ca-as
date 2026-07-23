package nodo;

import java.util.List;

public class PruebaConsola {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   PRUEBA DE CONSOLA: SISTEMA DE LABERINTOS (MVC) ");
        System.out.println("==================================================\n");

        // 1. Crear un laberinto de 5x5
        // 0 = Pasillo, 1 = Pared, 2 = Entrada, 3 = Salida
        Laberinto laberinto = new Laberinto(5, 5);

        // Diseñar un laberinto sencillo con algunas paredes (1)
        /*
            Entrada (2)  Pasillo(0)  Pared(1)    Pasillo(0)  Pasillo(0)
            Pared(1)     Pasillo(0)  Pared(1)    Pasillo(0)  Pared(1)
            Pasillo(0)   Pasillo(0)  Pasillo(0)  Pasillo(0)  Pasillo(0)
            Pared(1)     Pared(1)    Pasillo(0)  Pared(1)    Pared(1)
            Pasillo(0)   Pasillo(0)  Pasillo(0)  Pasillo(0)  Salida (3)
        */
        
        // Bloqueos/Paredes
        laberinto.setTipoCelda(0, 2, 1);
        laberinto.setTipoCelda(1, 0, 1);
        laberinto.setTipoCelda(1, 2, 1);
        laberinto.setTipoCelda(1, 4, 1);
        laberinto.setTipoCelda(3, 0, 1);
        laberinto.setTipoCelda(3, 1, 1);
        laberinto.setTipoCelda(3, 3, 1);
        laberinto.setTipoCelda(3, 4, 1);

        // Definir Entrada (0,0) y Salida (4,4)
        laberinto.setPuntoEntrada(0, 0);
        laberinto.setPuntoSalida(4, 4);

        // 2. Abstraer la matriz a Grafo Matemático
        Grafo grafo = laberinto.generarGrafoDesdeMatriz();
        Nodo inicio = laberinto.getNodoEntrada();
        Nodo fin = laberinto.getNodoSalida();

        System.out.println("-> Grafo generado exitosamente.");
        System.out.println("   Total de nodos transitables (vértices): " + grafo.getNodos().size());
        System.out.println("   Nodo Inicio: ID " + inicio.getId() + " [" + inicio.getFila() + "," + inicio.getColumna() + "]");
        System.out.println("   Nodo Fin:    ID " + fin.getId() + " [" + fin.getFila() + "," + fin.getColumna() + "]\n");

        // 3. Probar Algoritmo BFS
        System.out.println("--------------------------------------------------");
        System.out.println("EJECUTANDO BÚSQUEDA EN ANCHURA (BFS)...");
        EstrategiaBusqueda bfs = new BusquedaBFS();
        List<Nodo> rutaBFS = bfs.buscarRuta(grafo, inicio, fin);

        mostrarRuta("BFS", rutaBFS);

        // 4. Probar Algoritmo DFS
        System.out.println("--------------------------------------------------");
        System.out.println("EJECUTANDO BÚSQUEDA EN PROFUNDIDAD (DFS)...");
        EstrategiaBusqueda dfs = new BusquedaDFS();
        List<Nodo> rutaDFS = dfs.buscarRuta(grafo, inicio, fin);

        mostrarRuta("DFS", rutaDFS);
        
        System.out.println("==================================================");
    }

    private static void mostrarRuta(String nombreAlgoritmo, List<Nodo> ruta) {
        if (ruta.isEmpty()) {
            System.out.println("Resultados " + nombreAlgoritmo + ": NO se encontró un camino válido.");
        } else {
            System.out.println("Resultados " + nombreAlgoritmo + ": Ruta encontrada con " + ruta.size() + " pasos.");
            System.out.print("Camino: ");
            for (int i = 0; i < ruta.size(); i++) {
                Nodo n = ruta.get(i);
                System.out.print("[" + n.getFila() + "," + n.getColumna() + "]");
                if (i < ruta.size() - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println("\n");
        }
    }
}
