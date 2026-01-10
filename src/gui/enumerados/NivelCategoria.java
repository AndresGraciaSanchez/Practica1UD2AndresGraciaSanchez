package gui.enumerados;

public enum NivelCategoria {

    BASICO("Básico"),
    INTERMEDIO("Intermedio"),
    AVANZADO("Avanzado");

    private String valor;

    NivelCategoria(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
