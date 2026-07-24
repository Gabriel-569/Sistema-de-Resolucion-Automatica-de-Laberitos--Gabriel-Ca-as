package nodo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestorArchivo {

    public static void guardarLaberinto(Laberinto laberinto, File archivo) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            int filas = laberinto.getFilas();
            int columnas = laberinto.getColumnas();

            for (int i = 0; i < filas; i++) {
                StringBuilder linea = new StringBuilder();
                for (int j = 0; j < columnas; j++) {
                    int tipo = laberinto.getTipoCelda(i, j);
                    if (tipo == 1) {
                        linea.append('1'); // Pared
                    } else if (tipo == 2) {
                        linea.append('E'); // Entrada
                    } else if (tipo == 3) {
                        linea.append('S'); // Salida
                    } else {
                        linea.append('0'); // Pasillo
                    }
                }
                bw.write(linea.toString());
                if (i < filas - 1) {
                    bw.newLine();
                }
            }
        }
    }

    public static Laberinto cargarLaberinto(File archivo) throws IOException {
        List<String> lineas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    lineas.add(linea.trim());
                }
            }
        }

        if (lineas.isEmpty()) {
            throw new IOException("El archivo está vacío.");
        }

        int filas = lineas.size();
        int columnas = lineas.get(0).length();

        Laberinto nuevoLaberinto = new Laberinto(filas, columnas);

        for (int i = 0; i < filas; i++) {
            String filaTexto = lineas.get(i);
            for (int j = 0; j < Math.min(columnas, filaTexto.length()); j++) {
                char caracter = filaTexto.charAt(j);
                if (caracter == '1') {
                    nuevoLaberinto.setTipoCelda(i, j, 1);
                } else if (caracter == 'E' || caracter == 'e') {
                    nuevoLaberinto.setPuntoEntrada(i, j);
                } else if (caracter == 'S' || caracter == 's') {
                    nuevoLaberinto.setPuntoSalida(i, j);
                } else {
                    nuevoLaberinto.setTipoCelda(i, j, 0);
                }
            }
        }

        return nuevoLaberinto;
    }
}