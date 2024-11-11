package grafo;

import java.awt.Rectangle;

public class Ciudad {
    private String nombre;
    private int posX, posY;

    public Ciudad(String nombre, int posX, int posY) {
        this.nombre = nombre;
        this.posX = posX;
        this.posY = posY;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    // Método para obtener un área rectangular donde se encuentra la ciudad
   public Rectangle getBounds() {
    // Suponiendo que las ciudades son representadas como puntos en el mapa
    int width = 20;  // Ejemplo de tamaño de la ciudad (ajusta según sea necesario)
    int height = 20; // Ejemplo de tamaño de la ciudad (ajusta según sea necesario)
    return new Rectangle(posX - width / 2, posY - height / 2, width, height);
}

}




