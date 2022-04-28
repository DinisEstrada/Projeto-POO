public class FornecedorB extends Fornecedor{

    FornecedorB(){
        super();
    }

    FornecedorB(String name, float valor_base, float imposto, float desconto){
        super(name, valor_base, imposto, desconto);
    }

    FornecedorB(FornecedorB f){
        super(f.getName(),f.getValor_base(),f.getImposto(),f.getDesconto());
    }

    public String toString() {
        return  "\n### FornecedorB ###" +
                "\nFornecedor: " + super.getName() +
                "\nValorBase: " + super.getValor_base() +
                "\nImposto: " + super.getImposto() +
                "\nDesconto: " + super.getDesconto() +
                "\n";
    }

    public FornecedorB clone(){return new FornecedorB(this); }

    public float formulaPreco(SmartDevice smt, CasaInteligente casa){
        if(casa.numberDevices()<10)  return (super.getValor_base()*smt.getConsumo()* (1+super.getImposto()/100))*(1 - super.getDesconto()/100);
        else return (super.getValor_base()*smt.getConsumo()* (1+super.getImposto()/100))*(1 - (2*super.getDesconto())/100);
    }
}
