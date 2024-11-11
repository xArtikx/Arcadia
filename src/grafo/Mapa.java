/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grafo;

/**
 *
 * @author andre
 */
import grafo.Ciudad;
import java.util.*;

public class Mapa {
    private Map<Ciudad, List<Arista>> grafo = new HashMap<>();

    public Map<Ciudad, List<Arista>> getGrafo() {
        return grafo;
    }

    public void agregarCiudad(Ciudad ciudad) {
        grafo.putIfAbsent(ciudad, new ArrayList<>());
    }

   public void agregarArista(Ciudad origen, Ciudad destino, int peso) {
    if (!grafo.containsKey(origen) || !grafo.containsKey(destino)) {
        throw new IllegalArgumentException("Una de las ciudades no existe en el grafo.");
    }
    grafo.get(origen).add(new Arista(destino, peso));
    grafo.get(destino).add(new Arista(origen, peso));
}

    public List<Arista> obtenerAristas(Ciudad ciudad) {
        return grafo.get(ciudad);
    }

  public Map<Ciudad, Integer> dijkstra(Ciudad inicio) {
    Map<Ciudad, Integer> distancias = new HashMap<>();
    Map<Ciudad, Ciudad> predecesores = new HashMap<>();
    PriorityQueue<Map.Entry<Ciudad, Integer>> cola = new PriorityQueue<>(Map.Entry.comparingByValue());
    for (Ciudad ciudad : grafo.keySet()) {
        distancias.put(ciudad, Integer.MAX_VALUE);
        predecesores.put(ciudad, null);
    }
    distancias.put(inicio, 0);
    cola.add(new AbstractMap.SimpleEntry<>(inicio, 0));

    while (!cola.isEmpty()) {
        Ciudad actual = cola.poll().getKey();
        for (Arista arista : obtenerAristas(actual)) {
            Ciudad vecino = arista.getDestino();
            int nuevaDistancia = distancias.get(actual) + arista.getPeso();
            if (nuevaDistancia < distancias.get(vecino)) {
                distancias.put(vecino, nuevaDistancia);
                predecesores.put(vecino, actual);
                cola.add(new AbstractMap.SimpleEntry<>(vecino, nuevaDistancia));
            }
        }
    }

    return distancias;
}

public List<Ciudad> reconstruirCamino(Ciudad inicio, Ciudad destino, Map<Ciudad, Ciudad> predecesores) {
    List<Ciudad> camino = new LinkedList<>();
    Ciudad actual = destino;

    while (actual != null && !actual.equals(inicio)) {
        camino.add(0, actual);  // Inserta al inicio para construir el camino en orden
        actual = predecesores.get(actual);
    }

    if (actual != null) {
        camino.add(0, inicio);  // Agrega el inicio si existe un camino
    }
    return camino;
}
public Set<Ciudad> getCiudades() {
    return grafo.keySet();  // Devuelve las ciudades en el grafo
}


}

