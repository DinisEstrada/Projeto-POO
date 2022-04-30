import ErrorHandling.CasaInteligenteException;
import ErrorHandling.FornecedorException;
import ErrorHandling.SmartDeviceException;

import java.io.*;

abstract class Fornecedor implements Serializable {

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

    public Fornecedor(String name) throws FornecedorException{
        if (name.equals("")) throw new FornecedorException("Sem Nome");
        this.name = name;
        this.valor_base = 0;
        this.imposto = 0;
        this.desconto = 0;
    }
    public Fornecedor(String name, float valor_base, float imposto, float desconto) throws FornecedorException {
        if (name.equals("") || valor_base<0 || imposto<0 || desconto<0) throw new FornecedorException("Sem Nome/Valores Negativos");
        this.name = name;
        this.valor_base = valor_base;
        this.imposto= imposto;
        this.desconto = desconto;
    }

    public Fornecedor(Fornecedor sup) throws FornecedorException{
        if (sup.getName().equals("") || sup.getValor_base()<0 || sup.getImposto()<0 || sup.getDesconto()<0) throw new FornecedorException("Sem Nome/Valores Negativos");
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

    public void setValor_base(float valor_base) throws FornecedorException{
        if(valor_base<0) throw new FornecedorException("Valor Base Negativo");
        this.valor_base = valor_base;}

    public void setImposto(float imposto) throws FornecedorException {
        if(imposto<0) throw new FornecedorException("Imposto Negativo");
        this.imposto = imposto;}

    public void setDesconto(float desconto) throws FornecedorException{
        if(desconto<0) throw new FornecedorException("Desconto Negativo");
        this.desconto = desconto;}

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
    public abstract Fornecedor clone() ;

    public void guardaFornecedor(String nomeFicheiro) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(nomeFicheiro);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
        fos.close();
    }

    public Fornecedor carregaFornecedor(String nomeFicheiro) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fos = new FileInputStream(nomeFicheiro);
        ObjectInputStream oos = new ObjectInputStream(fos);
        Fornecedor f = (Fornecedor) oos.readObject();
        oos.close();
        fos.close();
        return f;
    }

    //------------------------------------------------

    public String getType(){
        if (this instanceof FornecedorA) return "FornecedorA";
        else if (this instanceof FornecedorB) return "FornecedorB";
        else if (this instanceof FornecedorC) return "FornecedorC";
        else return "Fornecedor";
    }

    public abstract float formulaPreco(SmartDevice smt, CasaInteligente house) throws SmartDeviceException, CasaInteligenteException, FornecedorException;
}
