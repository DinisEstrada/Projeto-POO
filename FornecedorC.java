public class FornecedorC extends Fornecedor{

    FornecedorC(){
        super();
    }

    FornecedorC(String name, float valor_base, float imposto, float desconto){
        super(name, valor_base, imposto, desconto);
    }

    FornecedorC(FornecedorC f){
        super(f.getName(),f.getValor_base(),f.getImposto(),f.getDesconto());
    }

    public String toString() {
        return  "\n### FornecedorC ###" +
                "\nFornecedor: " + super.getName() +
                "\nValorBase: " + super.getValor_base() +
                "\nImposto: " + super.getImposto() +
                "\nDesconto: " + super.getDesconto() +
                "\n";
    }

    public FornecedorC clone(){return new FornecedorC(this);}

    public float formulaPreco(SmartDevice smt,CasaInteligente casa){
        if(casa.numberDevices()<10)  return (super.getValor_base()*smt.getConsumo()* (1+super.getImposto()/100))*(1-(super.getDesconto())/100);
        else if(casa.numberDevices()<20)  return (super.getValor_base()*smt.getConsumo()* (1+super.getImposto()/100))*(1-(2*super.getDesconto())/100);
        else return (super.getValor_base()*smt.getConsumo()* (1+super.getImposto()/100))*(1-(3*super.getDesconto())/100);
    }
}
