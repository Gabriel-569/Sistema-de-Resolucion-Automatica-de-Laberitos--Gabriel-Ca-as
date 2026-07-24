package nodo;

import javax.swing.SwingUtilities;

public class MainGUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 1. Instanciar Modelo
            Laberinto modelo = new Laberinto(10, 10);

            // 2. Instanciar Vista
            VentanaPrincipal vista = new VentanaPrincipal(modelo);

            // 3. Instanciar Controlador (Une Vista y Modelo)
            ControladorLaberinto controlador = new ControladorLaberinto(modelo, vista);

            // 4. Mostrar Ventana
            vista.setVisible(true);
        });
    }
}