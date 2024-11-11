package interfaz;

import grafo.Mapa;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapaVisual {

    public static void main(String[] args) {
        // Inicializa el mapa y las ciudades
        Mapa mapa = new Mapa();

        // Crea el JFrame
        // Crea el JFrame
        JFrame frame = new JFrame("Mapa de Ciudades");

// Crear el panel y pasarlo con el mapa y el JFrame
        MapaPanel panel = new MapaPanel(mapa, frame);  // Agrega 'frame' como segundo argumento

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);

        // Crear el panel con los apartados adicionales (para agregar conexiones y mostrar distancias)
        JPanel opcionesPanel = new JPanel();
        opcionesPanel.setLayout(new BorderLayout());  // Para colocar el botón en la esquina inferior derecha

        // Crear el botón "Regresar al Menú"
        JButton botonRegresar = new JButton("Regresar al Menú");
        botonRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí puedes cerrar la ventana actual y abrir el menú principal
                frame.dispose();  // Cierra el JFrame de MapaVisual
                mostrarMenuPrincipal();  // Método para mostrar el menú principal
            }
        });

        // Agregar el botón en la esquina inferior derecha
        opcionesPanel.add(botonRegresar, BorderLayout.EAST);

        // Añadir el panel de opciones en la parte inferior del JFrame
        frame.add(opcionesPanel, BorderLayout.SOUTH);

        // Configuración del JFrame
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);  // Centra el JFrame en la pantalla
        frame.setVisible(true);
    }

    // Método para mostrar el menú principal
    private static void mostrarMenuPrincipal() {
        // Aquí puedes implementar la lógica para mostrar el menú principal
        // Ejemplo: abrir un nuevo JFrame con el menú inicial
        JFrame menuFrame = new JFrame("Menú Principal");
        menuFrame.setSize(400, 300);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        menuFrame.setVisible(true);
    }
}
