package interfaz;

import grafo.Ciudad;
import grafo.Mapa;
import grafo.Arista;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class MapaPanel extends JPanel {
 private Image fondo;
    private Image fondoPredeterminado;
    private Ciudad ciudadSeleccionada = null;
    private Mapa mapa;
    private Map<Ciudad, Point> posiciones;
    private Map<Ciudad, Integer> distancias;
    private final JButton cambiarFondo;
    private final JButton regresarMenu;
    private final JButton descargarMapa;
    private JFrame framePrincipal;

    public MapaPanel(Mapa mapa, JFrame frame) {
        this.mapa = mapa;
        this.framePrincipal = frame;
        this.posiciones = new HashMap<>();
        this.distancias = new HashMap<>();
        setLayout(null); // Para colocar los botones manualmente
        cambiarFondo = new JButton("Cambiar Fondo");
        // Botón "Descargar Mapa"
        descargarMapa = new JButton("Descargar Mapa");
        add(descargarMapa);

        // Acción para el botón de "Descargar Mapa"
        descargarMapa.addActionListener(e -> descargarMapaComoImagen());

        // Cargar la imagen de fondo
        try {
            fondoPredeterminado = ImageIO.read(new File("src/recursos/mapa.jpg"));
             fondo = fondoPredeterminado;
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        add(cambiarFondo);
        cambiarFondo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Seleccionar Imagen de Fondo");
                fileChooser.setFileFilter(new FileNameExtensionFilter("Imágenes", "jpg", "png", "jpeg"));

                int seleccion = fileChooser.showOpenDialog(null);
                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    File archivoSeleccionado = fileChooser.getSelectedFile();
                    try {
                        fondo = ImageIO.read(archivoSeleccionado);
                        repaint();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error al cargar la imagen seleccionada.");
                        fondo = fondoPredeterminado;
                    }
                } else {
                    fondo = fondoPredeterminado;
                }
            }
        });

        // Botón "Regresar al Menú"
        regresarMenu = new JButton("Regresar al Menú");
        add(regresarMenu);

        // Acción para el botón de regresar al menú
        regresarMenu.addActionListener(e -> {
            framePrincipal.getContentPane().removeAll();
            framePrincipal.add(new MenuPanel(framePrincipal));  // Vuelve al menú
            framePrincipal.revalidate();
            framePrincipal.repaint();
        });

        // Listener para agregar y conectar ciudades
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point clickPoint = e.getPoint();
                Ciudad ciudadClickeada = getCiudadAtPoint(clickPoint);

                if (ciudadClickeada != null) {
                    if (ciudadSeleccionada == null) {
                        ciudadSeleccionada = ciudadClickeada;
                        JOptionPane.showMessageDialog(null, "Ciudad seleccionada: " + ciudadClickeada.getNombre());
                    } else {
                        int opcion = JOptionPane.showConfirmDialog(null, "¿Desea calcular las distancias desde " + ciudadSeleccionada.getNombre() + "?", "Calcular Distancias", JOptionPane.YES_NO_OPTION);

                        if (opcion == JOptionPane.YES_OPTION) {
                            calcularDistanciasDijkstra(ciudadSeleccionada);
                            mostrarDistancias();
                        } else {
                            String pesoStr = JOptionPane.showInputDialog("Ingrese el peso de la conexión entre " + ciudadSeleccionada.getNombre() + " y " + ciudadClickeada.getNombre());
                            try {
                                int peso = Integer.parseInt(pesoStr);
                                if (peso > 0) {
                                    mapa.agregarArista(ciudadSeleccionada, ciudadClickeada, peso);
                                    repaint();
                                } else {
                                    JOptionPane.showMessageDialog(null, "El peso debe ser un número positivo.");
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "El peso debe ser un número válido.");
                            }
                        }
                        ciudadSeleccionada = null;
                    }
                } else {
                    String ciudadNombre = JOptionPane.showInputDialog("Ingrese el nombre de la ciudad:");
                    if (ciudadNombre != null && !ciudadNombre.trim().isEmpty()) {
                        Ciudad nuevaCiudad = new Ciudad(ciudadNombre, clickPoint.x, clickPoint.y);
                        if (!mapa.getGrafo().containsKey(nuevaCiudad)) {
                            mapa.agregarCiudad(nuevaCiudad);
                            posiciones.put(nuevaCiudad, clickPoint);
                            repaint();
                        } else {
                            JOptionPane.showMessageDialog(null, "Ya existe una ciudad con ese nombre.");
                        }
                    }
                }
            }
        });
    }

    public Ciudad getCiudadAtPoint(Point point) {
        for (Ciudad ciudad : mapa.getCiudades()) {
            int x = ciudad.getPosX();
            int y = ciudad.getPosY();
            int radius = 10;
            if (point.distance(x, y) <= radius) {
                return ciudad;
            }
        }
        return null;
    }

    public void calcularDistanciasDijkstra(Ciudad ciudadOrigen) {
        distancias.clear();
        Map<Ciudad, Integer> dist = new HashMap<>();
        Map<Ciudad, Ciudad> prev = new HashMap<>();
        PriorityQueue<Ciudad> pq = new PriorityQueue<>(Comparator.comparingInt(dist::get));

        for (Ciudad ciudad : mapa.getCiudades()) {
            dist.put(ciudad, Integer.MAX_VALUE);
            prev.put(ciudad, null);
        }
        dist.put(ciudadOrigen, 0);
        pq.add(ciudadOrigen);

        while (!pq.isEmpty()) {
            Ciudad ciudadActual = pq.poll();
            for (Arista arista : mapa.obtenerAristas(ciudadActual)) {
                Ciudad vecino = arista.getDestino();
                int nuevaDistancia = dist.get(ciudadActual) + arista.getPeso();
                if (nuevaDistancia < dist.get(vecino)) {
                    dist.put(vecino, nuevaDistancia);
                    prev.put(vecino, ciudadActual);
                    pq.add(vecino);
                }
            }
        }
        distancias.putAll(dist);
    }

    public void mostrarDistancias() {
        StringBuilder distanciasText = new StringBuilder("Distancias desde " + ciudadSeleccionada.getNombre() + ":\n");
        for (Map.Entry<Ciudad, Integer> entry : distancias.entrySet()) {
            distanciasText.append(entry.getKey().getNombre()).append(": ").append(entry.getValue()).append(" unidades\n");
        }
        JOptionPane.showMessageDialog(null, distanciasText.toString());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (fondo != null) {
            g2.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        }

        g2.setColor(Color.RED);
        for (Ciudad origen : mapa.getGrafo().keySet()) {
            if (posiciones.containsKey(origen)) {
                for (Arista arista : mapa.obtenerAristas(origen)) {
                    if (posiciones.containsKey(arista.getDestino())) {
                        Point p1 = posiciones.get(origen);
                        Point p2 = posiciones.get(arista.getDestino());
                        g2.drawLine(p1.x, p1.y, p2.x, p2.y);

                        g2.setColor(Color.GREEN);
                        g2.drawString(String.valueOf(arista.getPeso()), (p1.x + p2.x) / 2, (p1.y + p2.y) / 2);

                        g2.setColor(Color.RED);
                    }
                }
            }
        }

        g2.setColor(Color.BLUE);
        for (Ciudad ciudad : mapa.getCiudades()) {
            if (posiciones.containsKey(ciudad)) {
                Point p = posiciones.get(ciudad);
                g2.fillOval(p.x - 5, p.y - 5, 10, 10);
                g2.setColor(Color.WHITE);
                g2.drawString(ciudad.getNombre(), p.x + 10, p.y + 5);
                g2.setColor(Color.BLUE);
            }
        }

        // Actualizar las posiciones de los botones dentro de paintComponent
        regresarMenu.setBounds(getWidth() - 180, getHeight() - 60, 160, 40);
        descargarMapa.setBounds(20, getHeight() - 60, 160, 40);
         cambiarFondo.setBounds((getWidth() - 180)/2, getHeight() - 60, 160, 40);
    }

    private void descargarMapaComoImagen() {
         BufferedImage imagenMapa = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
    Graphics2D g2 = imagenMapa.createGraphics();
    
    // Pintar el mapa en el BufferedImage
    paint(g2);  // Llama al método paintComponent
    g2.dispose();

    // Guardar la imagen en la carpeta de Descargas
    try {
        String userHome = System.getProperty("user.home");
        String carpetaDescargas = userHome + File.separator + "Downloads";
        File archivoSalida = new File(carpetaDescargas, "MapaDescargado.png");
        ImageIO.write(imagenMapa, "png", archivoSalida);
        JOptionPane.showMessageDialog(null, "Mapa descargado en " + archivoSalida.getAbsolutePath());
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error al descargar el mapa. Verifique el fondo seleccionado.");
    }
    }
}
