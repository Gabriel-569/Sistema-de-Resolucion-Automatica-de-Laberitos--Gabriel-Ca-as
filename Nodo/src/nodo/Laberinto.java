package nodo;

import java.util.HashMap;
import java.util.Map;

public class Laberinto {
    private int filas;
    private int columnas;
    private int[][] matriz;
    private Grafo grafoMatematico;
    private Nodo nodoEntrada;
    private Nodo nodoSalida;

    public Laberinto(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.matriz = new int[filas][columnas]; // Por defecto inicializado en 0 (Pasillo)
        this.grafoMatematico = new Grafo();
    }

    public void setTipoCelda(int fila, int columna, int tipo) {
        if (fila >= 0 && fila < filas && columna >= 0 && columna < columnas) {
            matriz[fila][columna] = tipo;
        }
    }
    
    public int getTipoCelda(int fila, int columna) {
        if (fila >= 0 && fila < filas && columna >= 0 && columna < columnas) {
            return matriz[fila][columna];
        }
        return -1; // Retorna -1 si está fuera de los límites de la matriz
    }
    
    public Grafo generarGrafoDesdeMatriz() {
        grafoMatematico = new Grafo();
        Map<String, Nodo> mapaNodos = new HashMap<>();
        int idContador = 1;

        // 1. Crear nodos para las celdas transitables (diferentes de Pared - 1)
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (matriz[i][j] != 1) {
                    Nodo nuevoNodo = new Nodo(idContador++, i, j);
                    mapaNodos.put(i + "," + j, nuevoNodo);
                    grafoMatematico.agregarNodo(nuevoNodo);

                    // Sincronización: Evalúa tanto por valor numérico (2/3) como por coordenadas guardadas
                    if (matriz[i][j] == 2 || (this.nodoEntrada != null && this.nodoEntrada.getFila() == i && this.nodoEntrada.getColumna() == j)) {
                        this.nodoEntrada = nuevoNodo;
                    } 
                    if (matriz[i][j] == 3 || (this.nodoSalida != null && this.nodoSalida.getFila() == i && this.nodoSalida.getColumna() == j)) {
                        this.nodoSalida = nuevoNodo;
                    }
                }
            }
        }

        // 2. Conectar celdas transitables adyacentes en las 4 direcciones (Derecha, Abajo, Izquierda, Arriba)
        int[][] direcciones = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        for (String clave : mapaNodos.keySet()) {
            Nodo actual = mapaNodos.get(clave);
            for (int[] dir : direcciones) {
                int nuevaFila = actual.getFila() + dir[0];
                int nuevaCol = actual.getColumna() + dir[1];
                String claveVecino = nuevaFila + "," + nuevaCol;

                if (mapaNodos.containsKey(claveVecino)) {
                    Nodo vecino = mapaNodos.get(claveVecino);
                    grafoMatematico.agregarArista(actual, vecino);
                }
            }
        }

        return grafoMatematico;
    }

    public void setPuntoEntrada(int fila, int columna) {
        setTipoCelda(fila, columna, 2);
    }

    public void setPuntoSalida(int fila, int columna) {
        setTipoCelda(fila, columna, 3);
    }

    public Nodo getNodoEntrada() {
        return nodoEntrada;
    }

    public Nodo getNodoSalida() {
        return nodoSalida;
    }
    
    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    // =========================================================================
    // NUEVOS MÉTODOS PARA INTEGRACIÓN CON GESTORARCHIVO (CAPA DE PERSISTENCIA)
    // =========================================================================

    /**
     * Convierte la matriz de enteros interna a una matriz de caracteres 
     * compatible con GestorArchivo para la operación de guardado en .txt.
     */
    public char[][] exportarAMatrizChar() {
        char[][] matrizChar = new char[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                switch (matriz[i][j]) {
                    case 1:
                        matrizChar[i][j] = '1'; // Pared
                        break;
                    case 2:
                        matrizChar[i][j] = 'E'; // Entrada
                        break;
                    case 3:
                        matrizChar[i][j] = 'S'; // Salida
                        break;
                    default:
                        matrizChar[i][j] = '0'; // Pasillo
                        break;
                }
            }
        }
        return matrizChar;
    }

    /**
     * Reconstruye la matriz interna de enteros y limpia las referencias a Entrada/Salida
     * a partir de la matriz de caracteres leída por GestorArchivo desde un .txt.
     */
    public void cargarDesdeMatrizChar(char[][] matrizChar) {
        this.filas = matrizChar.length;
        this.columnas = matrizChar[0].length;
        this.matriz = new int[filas][columnas];
        this.nodoEntrada = null;
        this.nodoSalida = null;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                char simbolo = matrizChar[i][j];
                switch (simbolo) {
                    case '1':
                        matriz[i][j] = 1; // Pared
                        break;
                    case 'E':
                        matriz[i][j] = 2; // Entrada
                        break;
                    case 'S':
                        matriz[i][j] = 3; // Salida
                        break;
                    default:
                        matriz[i][j] = 0; // Pasillo
                        break;
                }
            }
        }
    }
}