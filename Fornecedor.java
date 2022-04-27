abstract class Fornecedor {

    private String name;
    private float valor_base;
    private float imposto;
    private float desconto;


    public Fornecedor(){
        this.name = "";
        this.valor_base = 0;
        this.imposto = 0;
        this.desconto = 0;
    }

    public Fornecedor(String name){
        this.name = name;
        this.valor_base = 0;
        this.imposto = 0;
        this.desconto = 0;
    }
    public Fornecedor(String name, float valor_base, float imposto, float desconto){
        this.name = name;
        this.valor_base = valor_base;
        this.imposto= imposto;
        this.desconto = desconto;
    }

    public Fornecedor(Fornecedor sup){
        this.name = sup.getName();
        this.valor_base = sup.getValor_base();
        this.imposto = sup.getImposto();
        this.desconto = sup.getDesconto();
    }

    public String getName() {return this.name;}

    public float getValor_base() {return this.valor_base;}

    public float getImposto() {return this.imposto;}

    public float getDesconto() {return this.desconto;}

    public void setName(String name) {this.name = name;}

    public void setValor_base(float valor_base) {this.valor_base = valor_base;}

    public void setImposto(float imposto) {this.imposto = imposto;}

    public void setDesconto(float desconto) {this.desconto = desconto;}

    public boolean equals(Object o) {
        if (this==o) return true;
        if (o==null || this.getClass()!=o.getClass()) return false;
        Fornecedor sup = (Fornecedor) o;
        return (this.name.equals(sup.getName()) &&
                this.valor_base == sup.getValor_base()) &&
                this.imposto == sup.getImposto() &&
                this.desconto == sup.getDesconto();
    }

    public abstract  String toString();
    public abstract Fornecedor clone();

    //------------------------------------------------

    public String getType(){
        if (this instanceof FornecedorA) return "FornecedorA";
        else if (this instanceof FornecedorB) return "FornecedorB";
        else if (this instanceof FornecedorC) return "FornecedorC";
        else return "Fornecedor";
    }

    public abstract float precodiapordispositivo(SmartDevice smt, CasaInteligente house);
}
