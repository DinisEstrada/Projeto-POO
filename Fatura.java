import ErrorHandling.CasaInteligenteException;
import ErrorHandling.FaturaException;
import ErrorHandling.FornecedorException;
import ErrorHandling.SmartDeviceException;

import java.io.*;

public class Fatura implements Comparable, Serializable {

    private CasaInteligente casa;
    private int periodo;
    private float consumo;
    private float custo;

    public Fatura(){
        this.casa = new CasaInteligente();
        this.periodo = 0;
        this.consumo=0;
        this.custo=0;
    }

    public Fatura(CasaInteligente casa, int periodo) throws FaturaException{
        if(periodo<0) throw new FaturaException("Valor de dias negativo");
        this.casa = casa.clone();
        this.periodo = periodo;
        this.consumo = consumoFatura();
        this.custo= valor_fatura();
    }

    public Fatura(Fatura f) throws FaturaException {
        if(f.getPeriodo()<0 || f.getConsumo()<0 || f.getCusto()<0) throw new FaturaException("Valor de dias negativo");
        this.casa = f.getCasa();
        this.periodo = f.getPeriodo();
        this.consumo = f.getConsumo();
        this.custo = f.getCusto();
    }

    public CasaInteligente getCasa() {return this.casa.clone();}

    public int getPeriodo() {
        return this.periodo;}

    public float getConsumo() {
        return this.consumo;
    }

    public float getCusto() {
        return this.custo;
    }

    public void setCasa(CasaInteligente casa) throws FaturaException {
        if(casa == null) throw new FaturaException("Casa Inválida");
        this.casa = casa.clone();
    }

    public void setPeriodo(int dias) throws FaturaException{
        if(dias<0) throw new FaturaException("Valor de dias Negativo");
        this.periodo = dias;
    }

    public void setConsumo(float consumo) throws FaturaException {
        if(consumo<0) throw new FaturaException("Valor de Consumo Negativo");
        this.consumo = consumo;
    }

    public void setCusto(float custo) throws FaturaException {
        if(custo<0) throw new FaturaException("Valor de Custo Negativo");
        this.custo = custo;
    }

    public Fatura clone(){
        try {
            return new Fatura(this);
        } catch (FaturaException e) {
            throw new RuntimeException(e);
        }
    }

    public String toString(){
        return "\n### Fatura ###"+
                "\nCasa: " + this.casa.getOwner() +
                "\nFornecedor: " + this.casa.getFornecedor().getName() + " | " + this.casa.getFornecedor().getClass().getName() +
                "\nPeríodo: " + this.periodo +
                " dia(s)\nConsumo: " + this.consumo+
                " kWh\nValor: " + this.custo+" €";

    }

    public boolean equals(Object o){
        if (this==o) return true;
        if (o==null || this.getClass()!=o.getClass()) return false;
        Fatura ft = (Fatura) o;
        return (this.casa.equals(ft.getCasa()) &&
                this.periodo == ft.getPeriodo() &&
                this.getConsumo() == this.getConsumo() &&
                this.getCusto() == ft.getCusto());
    }


    public int compareTo(Object o) {
        Fatura ft = (Fatura) o;
        if (ft.getCusto()>this.custo) return 1;
        else if (ft.getCusto()<this.custo) return -1;
        else {
           return ft.getCasa().getOwner().compareTo( this.casa.getOwner());
        }
    }

    public void guardaFatura(String nomeFicheiro) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(nomeFicheiro);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
        fos.close();
    }

    public Fatura carregaFatura(String nomeFicheiro) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fos = new FileInputStream(nomeFicheiro);
        ObjectInputStream oos = new ObjectInputStream(fos);
        Fatura c = (Fatura) oos.readObject();
        oos.close();
        fos.close();
        return c;
    }

    //---------------------------------------------------

    private float consumoFatura(){
        return (float) casa.consumoCasa()*this.periodo;
    }


    public float valor_fatura() {

        return (float) this.casa.custoCasa()*this.periodo;
    }
}
