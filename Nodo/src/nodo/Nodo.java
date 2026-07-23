package nodo;

public class Nodo {
    private int id;
    private int fila;
    private int columna;
    private String estado; // "NO_DESCUBIERTO", "FRONTERA", "VISITADO"
    private Nodo padre;

    public Nodo(int id, int fila, int columna) {
        this.id = id;
        this.fila = fila;
        this.columna = columna;
        this.estado = "NO_DESCUBIERTO";
        this.padre = null;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Nodo getPadre() {
        return padre;
    }

    public void setPadre(Nodo padre) {
        this.padre = padre;
    }
}