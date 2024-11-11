/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grafo;

import grafo.Ciudad;

/**
 *
 * @author andre
 */
public class Arista {
    private Ciudad destino;
    private int peso;

    public Arista(Ciudad destino, int peso) {
        this.destino = destino;
        this.peso = peso;
    }

    public Ciudad getDestino() {
        return destino;
    }

    public int getPeso() {
        return peso;
    }
}

