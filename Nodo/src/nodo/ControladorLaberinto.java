package nodo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ControladorLaberinto implements ActionListener, MouseListener {

    private Laberinto modeloLaberinto;
    private VentanaPrincipal vistaVentana;
    private EstrategiaBusqueda algoritmoActivo;
        
    private javax.swing.Timer timerSimulacion;
    private boolean enPausa = false;
    private int pasoActual = 0;
    private List<Nodo> rutaCompleta;

    public ControladorLaberinto(Laberinto modeloLaberinto, VentanaPrincipal vistaVentana) {
        this.modeloLaberinto = modeloLaberinto;
        this.vistaVentana = vistaVentana;

        // Registro de escuchadores de eventos (View -> Controller)
        this.vistaVentana.suscritoAEventos(this);
        this.vistaVentana.getPanelTablero().addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand().toLowerCase(); // Convertimos a minúsculas para comparar con seguridad

        if (comando.contains("iniciar")) {
            ejecutarBusqueda();
        } else if (comando.contains("pausar")) {
            pausarBusqueda();
        } else if (comando.contains("reset")) {
            resetearTablero();
        } else if (comando.contains("guardar")) { // Detecta "Guardar TXT", "Guardar", etc.
            guardarLaberinto();
        } else if (comando.contains("cargar")) {  // Detecta "Cargar TXT", "Cargar", etc.
            cargarLaberinto();
        } else if (comando.contains("salir")) {
            System.exit(0);
        }
    }

    private void ejecutarBusqueda() {
        // 0. Si la animación estaba pausada, se reanuda
        if (enPausa && timerSimulacion != null) {
            timerSimulacion.start();
            enPausa = false;
            vistaVentana.setMensajeEstado("Simulación reanudada.");
            return;
        }

        // Detener animación previa si existía
        if (timerSimulacion != null && timerSimulacion.isRunning()) {
            timerSimulacion.stop();
        }

        // 1. Evaluar el selector de algoritmo
        String seleccion = vistaVentana.obtenerAlgoritmoSeleccionado();

        if (seleccion.contains("BFS")) {
            algoritmoActivo = new BusquedaBFS();
        } else if (seleccion.contains("DFS")) {
            algoritmoActivo = new BusquedaDFS();
        }

        // 2. Obtener puntos críticos de entrada y salida
        Nodo inicioModelo = modeloLaberinto.getNodoEntrada();
        Nodo finModelo = modeloLaberinto.getNodoSalida();

        if (inicioModelo == null || finModelo == null) {
            vistaVentana.setMensajeEstado("Error: Establezca Entrada y Salida antes de iniciar.");
            return;
        }

        // 3. Generar la abstracción a Grafo
        Grafo grafo = modeloLaberinto.generarGrafoDesdeMatriz();

        // Buscar las referencias reales dentro del Grafo
        Nodo inicio = null;
        Nodo fin = null;

        for (Nodo n : grafo.getNodos()) {
            if (n.getFila() == inicioModelo.getFila() && n.getColumna() == inicioModelo.getColumna()) {
                inicio = n;
            }
            if (n.getFila() == finModelo.getFila() && n.getColumna() == finModelo.getColumna()) {
                fin = n;
            }
        }

        // Validar que los nodos existan en el grafo
        if (inicio == null || fin == null) {
            vistaVentana.setMensajeEstado("Resultado: No existe un camino válido.");
            return;
        }

        vistaVentana.setMensajeEstado("Ejecutando " + seleccion + "...");

        // Usar tu método original de la interfaz EstrategiaBusqueda
        rutaCompleta = algoritmoActivo.buscarRuta(grafo, inicio, fin);

        // 4. Evaluar resultado y animar el trazado
        if (rutaCompleta.isEmpty()) {
            vistaVentana.setMensajeEstado("Resultado: No existe un camino válido.");
            vistaVentana.getPanelTablero().setRutaCaminoOptimo(null);
            vistaVentana.getPanelTablero().actualizarLienzo();
            return;
        }

        // Configuración del Timer para revelar la ruta verde paso a paso (100 ms por casilla)
        pasoActual = 1;
        enPausa = false;

        timerSimulacion = new javax.swing.Timer(100, e -> {
            if (pasoActual <= rutaCompleta.size()) {
                // Sublista con el tramo trazado hasta el paso actual
                List<Nodo> subRuta = rutaCompleta.subList(0, pasoActual);
                vistaVentana.getPanelTablero().setRutaCaminoOptimo(subRuta);
                vistaVentana.getPanelTablero().actualizarLienzo();
                pasoActual++;
            } else {
                timerSimulacion.stop();
                vistaVentana.setMensajeEstado("Resultado: Ruta encontrada con " + rutaCompleta.size() + " pasos.");
            }
        });

        timerSimulacion.start();
    }

    private void pausarBusqueda() {
        if (timerSimulacion != null && timerSimulacion.isRunning()) {
            timerSimulacion.stop();
            enPausa = true;
            vistaVentana.setMensajeEstado("Simulación pausada.");
        }
    }

    private void resetearTablero() {
        if (timerSimulacion != null) {
            timerSimulacion.stop();
        }
        enPausa = false;
        pasoActual = 0;
        rutaCompleta = null;
        vistaVentana.getPanelTablero().setRutaCaminoOptimo(null);
        vistaVentana.setMensajeEstado("Tablero reiniciado.");
        vistaVentana.getPanelTablero().actualizarLienzo();
    }

    // --- NUEVAS FUNCIONALIDADES PARTE 4: PERSISTENCIA (GUARDAR / CARGAR) ---

private void guardarLaberinto() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Guardar Laberinto");
    fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de Texto (*.txt)", "txt"));

    int seleccion = fileChooser.showSaveDialog(vistaVentana);

    if (seleccion == JFileChooser.APPROVE_OPTION) {
        File archivo = fileChooser.getSelectedFile();
        
        String ruta = archivo.getAbsolutePath();
        if (!ruta.toLowerCase().endsWith(".txt")) {
            archivo = new File(ruta + ".txt");
        }

        try {
            // Se pasa el objeto modeloLaberinto y el objeto File 'archivo'
            GestorArchivo.guardarLaberinto(modeloLaberinto, archivo);
            vistaVentana.setMensajeEstado("Laberinto guardado exitosamente en: " + archivo.getName());
            JOptionPane.showMessageDialog(vistaVentana, "Laberinto guardado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            vistaVentana.setMensajeEstado("Error al guardar el archivo.");
            JOptionPane.showMessageDialog(vistaVentana, "Error al guardar el laberinto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

private void cargarLaberinto() {
    if (timerSimulacion != null && timerSimulacion.isRunning()) {
        timerSimulacion.stop();
    }
    enPausa = false;
    pasoActual = 0;
    rutaCompleta = null;

    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Cargar Laberinto");
    fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de Texto (*.txt)", "txt"));

    int seleccion = fileChooser.showOpenDialog(vistaVentana);

    if (seleccion == JFileChooser.APPROVE_OPTION) {
        File archivo = fileChooser.getSelectedFile();

        try {
            // GestorArchivo retorna directamente un objeto de tipo Laberinto
            Laberinto nuevoLaberinto = GestorArchivo.cargarLaberinto(archivo);

            if (nuevoLaberinto != null) {
                this.modeloLaberinto = nuevoLaberinto;

                // Se actualiza el lienzo con el nuevo laberinto
                vistaVentana.getPanelTablero().setLaberintoRef(this.modeloLaberinto);
                vistaVentana.getPanelTablero().setRutaCaminoOptimo(null);

                // Se reconstruye el grafo y repinta
                this.modeloLaberinto.generarGrafoDesdeMatriz();
                vistaVentana.getPanelTablero().actualizarLienzo();

                vistaVentana.setMensajeEstado("Laberinto cargado exitosamente desde: " + archivo.getName());
                JOptionPane.showMessageDialog(vistaVentana, "Laberinto cargado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            vistaVentana.setMensajeEstado("Error al cargar el archivo.");
            JOptionPane.showMessageDialog(vistaVentana, "Error al cargar el archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        int tamanoCelda = vistaVentana.getPanelTablero().getTamanoCelda();

        // Traducir coordenadas (X, Y) del pixel a índice [fila][columna] de la matriz
        int columna = x / tamanoCelda;
        int fila = y / tamanoCelda;

        int filas = modeloLaberinto.getFilas();
        int columnas = modeloLaberinto.getColumnas();
        
        // Validar que el clic esté dentro de los límites del laberinto
        if (fila >= 0 && fila < filas && columna >= 0 && columna < columnas) { // Matriz base
            
            if (vistaVentana.isModoDibujarPared()) {
                // Conmuta celda entre Pasillo (0) y Pared (1)
                int tipoActual = modeloLaberinto.getTipoCelda(fila, columna);
                int nuevoTipo = (tipoActual == 1) ? 0 : 1;
                modeloLaberinto.setTipoCelda(fila, columna, nuevoTipo);
            } else {
                // Modo Establecer Nodos (Entrada / Salida)
                Nodo entradaActual = modeloLaberinto.getNodoEntrada();
                
                if (entradaActual == null) {
                    modeloLaberinto.setPuntoEntrada(fila, columna);
                    vistaVentana.setMensajeEstado("Entrada ([I]) definida en [" + fila + "," + columna + "]");
                } else if (entradaActual.getFila() == fila && entradaActual.getColumna() == columna) {
                    // Si se vuelve a hacer clic en la entrada, se cambia a salida
                    modeloLaberinto.setPuntoSalida(fila, columna);
                    vistaVentana.setMensajeEstado("Salida ([F]) definida en [" + fila + "," + columna + "]");
                } else {
                    modeloLaberinto.setPuntoSalida(fila, columna);
                    vistaVentana.setMensajeEstado("Salida ([F]) definida en [" + fila + "," + columna + "]");
                }
            }

            vistaVentana.getPanelTablero().actualizarLienzo();
        }
    }

    // Métodos requeridos por la interfaz MouseListener
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}