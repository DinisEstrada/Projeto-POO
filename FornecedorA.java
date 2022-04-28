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
        return  "\n### FornecedorA ###" +
                "\nFornecedor: " + super.getName() +
                "\nValorBase: " + super.getValor_base() +
                "\nImposto: " + super.getImposto() +
                "\nDesconto: " + super.getDesconto() +
                "\n";
    }

    public FornecedorA clone(){return new FornecedorA(this);}

    public float formulaPreco(SmartDevice smt, CasaInteligente house){
        return (super.getValor_base()*smt.getConsumo()* (1+super.getImposto()/100))*(1-super.getDesconto()/100);
    }
}
