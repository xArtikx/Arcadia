/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;


import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 *
 * @author andre
 */
public class Menu {
    public static void main(String[] args) {
       
       
       JFrame frame = new JFrame("Arcadia");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        Image icono = Toolkit.getDefaultToolkit().getImage("src/recursos/icono.jpg");
        frame.setIconImage(icono);
        // Iniciar con el MenuPanel
        MenuPanel menuPanel = new MenuPanel(frame);
        frame.add(menuPanel);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
