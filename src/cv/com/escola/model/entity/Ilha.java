package cv.com.escola.model.entity;

public enum Ilha {
    SANTO_ANTAO("SANTO ANTÃO"), 
    SAO_VICENTE("SÂO VICENTE"),
    SAO_NICOLAU("SÂO NICOLAU"),
    SAL("SAL"),
    BOA_VISTA("BOA VISTA"),
    MAIO("MAIO"),
    SANTIAGO("SANTIAGO"),
    FOGO("FOGO"),
    BRAVA("BRAVA");
    
    private final String nome;
    
    Ilha(String valor){
        this.nome = valor;
    }

    public String getIlha() {
        return nome;
    }
}
