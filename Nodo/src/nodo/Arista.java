package nodo;

public class Arista {
    private Nodo origen;
    private Nodo destino;
    private double peso;

    public Arista(Nodo origen, Nodo destino) {
        this(origen, destino, 1.0);
    }

    public Arista(Nodo origen, Nodo destino, double peso) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }

    // Getters
    public Nodo getOrigen() {
        return origen;
    }

    public Nodo getDestino() {
        return destino;
    }

    public double getPeso() {
        return peso;
    }
}