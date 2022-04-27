public class FornecedorA extends Fornecedor{

    FornecedorA(){
        super();
    }

    FornecedorA(String name, float valor_base, float imposto, float desconto){
        super(name, valor_base, imposto, desconto);
    }

    FornecedorA(FornecedorA f){
        super(f.getName(),f.getValor_base(),f.getImposto(),f.getDesconto());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n### FornecedorA ###")
                .append("\nFornecedor: ").append(super.getName())
                .append("\nValorBase: ").append(super.getValor_base())
                .append("\nImposto: ").append(super.getImposto())
                .append("\nDesconto: ").append(super.getDesconto())
                .append("\n");

        return sb.toString();
    }

    public FornecedorA clone(){return new FornecedorA(this);}

    public float precodiapordispositivo(SmartDevice smt, CasaInteligente house){
        return (super.getValor_base()*smt.getConsumo()* (1+super.getImposto()/100))*(1-super.getDesconto()/100);
    }
}
