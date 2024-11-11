package interfaz;

import grafo.Mapa;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MenuPanel extends JPanel {
    private Image fondo;
    private ModernButton inicio;
    private ModernButton acercaDe;
    private ModernButton salir;

    public MenuPanel(JFrame framePrincipal) {
        
        setLayout(null);

        try {
            fondo = ImageIO.read(new File("src/recursos/fondo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Configurar botones modernos
        inicio = new ModernButton("Iniciar");
        acercaDe = new ModernButton("Acerca de");
        salir = new ModernButton("Salir");

        inicio.setBounds(150, 300, 200, 40);
        acercaDe.setBounds(150, 360, 200, 40);
        salir.setBounds(150, 420, 200, 40);

        add(inicio);
        add(acercaDe);
        add(salir);

        inicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                framePrincipal.getContentPane().removeAll();
                framePrincipal.add(new MapaPanel(new Mapa(), framePrincipal));
                framePrincipal.revalidate();
                framePrincipal.repaint();
            }
        });

        acercaDe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(framePrincipal, "Este es un programa de visualización de mapas", "Acerca de", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fondo != null) {
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        }

        // Título personalizado
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 28));
        g.drawString("Creador de Mapas", 140, 250);
    }

    // Clase de botón personalizado
    class ModernButton extends JButton {
        public ModernButton(String text) {
            super(text);
            setFocusPainted(false);
            setFont(new Font("Arial", Font.PLAIN, 16));
            setBackground(new Color(60, 179, 113));
            setForeground(Color.WHITE);
            setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            setContentAreaFilled(false);
            setOpaque(true);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(new Color(46, 139, 87));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(new Color(60, 179, 113));
                }
            });
        }
    }
}
