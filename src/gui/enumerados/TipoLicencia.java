package gui.enumerados;

public enum TipoLicencia {

    GPL("GPL"),
    MIT("MIT"),
    APACHE("Apache"),
    BSD("BSD");

    private String valor;

    TipoLicencia(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
