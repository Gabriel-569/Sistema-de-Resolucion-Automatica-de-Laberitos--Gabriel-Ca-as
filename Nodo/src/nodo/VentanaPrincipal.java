package nodo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class VentanaPrincipal extends JFrame {

    private PanelLaberinto panelTablero;
    private JComboBox<String> selectorAlgoritmo;
    private JButton btnIniciar, btnPausar, btnReset, btnSalir;
    private JLabel lblEstado;
    private JRadioButton rbtnDibujarPared, rbtnEstablecerNodos;
    private ButtonGroup grupoEdicion;

    public VentanaPrincipal(Laberinto laberinto) {
        setTitle("Sistema de Búsqueda en Laberintos - Algoritmos Grafo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // 1. Inicializar el panel de tablero central (Lienzo)
        panelTablero = new PanelLaberinto(laberinto, 40);

        // 2. Panel Superior: Selección de Algoritmo y Estado
        JPanel panelNorte = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelNorte.add(new JLabel("Algoritmo:"));
             
        selectorAlgoritmo = new JComboBox<>(new String[]{"BFS (Anchura)", "DFS (Profundidad)"});
        panelNorte.add(selectorAlgoritmo);

        lblEstado = new JLabel("Estado: Listo para iniciar");
        lblEstado.setFont(new Font("SansSerif", Font.BOLD, 12));
        panelNorte.add(lblEstado);

        // 3. Panel Lateral Derecho: Modo de Edición e Interacción
        JPanel panelEste = new JPanel(new GridLayout(6, 1, 5, 10));
        panelEste.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lblEdicion = new JLabel("Modo Edición:", SwingConstants.CENTER);
        lblEdicion.setFont(new Font("SansSerif", Font.BOLD, 12));
        
        rbtnDibujarPared = new JRadioButton("Dibujar Paredes", true);
        rbtnEstablecerNodos = new JRadioButton("Establecer E/S");

        grupoEdicion = new ButtonGroup();
        grupoEdicion.add(rbtnDibujarPared);
        grupoEdicion.add(rbtnEstablecerNodos);

        panelEste.add(lblEdicion);
        panelEste.add(rbtnDibujarPared);
        panelEste.add(rbtnEstablecerNodos);

        // 4. Panel Inferior: Botones de Control de Ejecución
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        
        btnIniciar = new JButton("Iniciar");
        btnPausar = new JButton("Pausar");
        btnReset = new JButton("Reset");
        btnSalir = new JButton("Salir");

        panelSur.add(btnIniciar);
        panelSur.add(btnPausar);
        panelSur.add(btnReset);
        panelSur.add(btnSalir);

        // Disposición global en la ventana
        add(panelNorte, BorderLayout.NORTH);
        add(panelTablero, BorderLayout.CENTER);
        add(panelEste, BorderLayout.EAST);
        add(panelSur, BorderLayout.SOUTH);
    }

    public String obtenerAlgoritmoSeleccionado() {
        return (String) selectorAlgoritmo.getSelectedItem();
    }

    // Vincula los eventos de la vista con los manejadores (ActionListeners)
    public void suscritoAEventos(ActionListener listener) {
        btnIniciar.addActionListener(listener);
        btnPausar.addActionListener(listener);
        btnReset.addActionListener(listener);
        btnSalir.addActionListener(listener);
        selectorAlgoritmo.addActionListener(listener);
    }
    
    public void setMensajeEstado(String mensaje) {
    if (lblEstado != null) {
        lblEstado.setText("Estado: " + mensaje);
    }
}
    // Getters para actualización de estado y paneles desde fuera
    public PanelLaberinto getPanelTablero() {
        return panelTablero;
    }

    public boolean isModoDibujarPared() {
        return rbtnDibujarPared.isSelected();
    }
}
