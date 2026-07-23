package nodo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;
import javax.swing.JPanel;

public class PanelLaberinto extends JPanel {

    private int tamanoCelda;
    private Laberinto laberintoRef;
    private List<Nodo> rutaCaminoOptimo;

    public PanelLaberinto(Laberinto laberinto, int tamanoCelda) {
        this.laberintoRef = laberinto;
        this.tamanoCelda = tamanoCelda;
        this.rutaCaminoOptimo = null;
        
        // Configurar el tamaño preferido del panel según el tamaño del laberinto
        if (laberintoRef != null) {
            int ancho = 10 * tamanoCelda; // Valor inicial por defecto o dinámico
            int alto = 10 * tamanoCelda;
            setPreferredSize(new Dimension(ancho, alto));
        }
        setBackground(Color.WHITE);
    }

    public void setLaberintoRef(Laberinto laberinto) {
        this.laberintoRef = laberinto;
        actualizarLienzo();
    }

    public void setRutaCaminoOptimo(List<Nodo> ruta) {
        this.rutaCaminoOptimo = ruta;
        actualizarLienzo();
    }

    public void actualizarLienzo() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (laberintoRef == null) {
            return;
        }

        int filas = laberintoRef.getFilas();      
        int columnas = laberintoRef.getColumnas(); 
        
        // 1. PRIMERA PASADA: Recorrer la matriz completa (10x10) para renderizar Paredes y Pasillos
        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {
                int x = columna * tamanoCelda;
                int y = fila * tamanoCelda;

                // Si la celda es Pared (1), se pinta NEGRO. De lo contrario, Blanco.
                if (laberintoRef.getTipoCelda(fila, columna) == 1) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(x, y, tamanoCelda, tamanoCelda);

                // Dibujar el borde de la cuadrícula
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(x, y, tamanoCelda, tamanoCelda);
            }
        }

        // 2. SEGUNDA PASADA: Renderizar sobre el tablero los estados del Grafo (Frontera, Visitado, Ruta, E/S)
        Grafo grafo = laberintoRef.generarGrafoDesdeMatriz();
        Nodo entrada = laberintoRef.getNodoEntrada();
        Nodo salida = laberintoRef.getNodoSalida();

        for (Nodo nodo : grafo.getNodos()) {
            int x = nodo.getColumna() * tamanoCelda;
            int y = nodo.getFila() * tamanoCelda;

            // Colores por estado de exploración (Frontera / Visitado)
            if ("FRONTERA".equalsIgnoreCase(nodo.getEstado())) {
                g.setColor(new Color(255, 235, 59)); // Amarillo
                g.fillRect(x, y, tamanoCelda, tamanoCelda);
            } else if ("VISITADO".equalsIgnoreCase(nodo.getEstado())) {
                g.setColor(new Color(179, 229, 252)); // Azul claro
                g.fillRect(x, y, tamanoCelda, tamanoCelda);
            }

    // Camino Óptimo (Validación por coordenadas)
            boolean esParteDeRuta = false;
            if (rutaCaminoOptimo != null) {
                for (Nodo nRuta : rutaCaminoOptimo) {
                    if (nRuta.getFila() == nodo.getFila() && nRuta.getColumna() == nodo.getColumna()) {
                        esParteDeRuta = true;
                        break;
                    }
                }
            }

            if (esParteDeRuta) {
                g.setColor(new Color(76, 175, 80)); // Verde brillante
                g.fillRect(x, y, tamanoCelda, tamanoCelda);
            }

            // Resaltar Entrada y Salida (comprando coordenadas para evitar fallos de instancia)
            if (entrada != null && nodo.getFila() == entrada.getFila() && nodo.getColumna() == entrada.getColumna()) {
                g.setColor(new Color(33, 150, 243)); // Azul oscuro (Entrada)
                g.fillRect(x, y, tamanoCelda, tamanoCelda);
            } else if (salida != null && nodo.getFila() == salida.getFila() && nodo.getColumna() == salida.getColumna()) {
                g.setColor(new Color(244, 67, 54)); // Rojo (Salida)
                g.fillRect(x, y, tamanoCelda, tamanoCelda);
            }

            // Mantener bordes limpios en celdas transitables
            g.setColor(Color.LIGHT_GRAY);
            g.drawRect(x, y, tamanoCelda, tamanoCelda);
        }
    }

    public int getTamanoCelda() {
        return tamanoCelda;
    }
    
}