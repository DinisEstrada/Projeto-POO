import ErrorHandling.FaturaException;

public class Fatura {

    private CasaInteligente casa;
    private int dias;

    public Fatura(){
        this.casa = new CasaInteligente();
        this.dias = 0;
    }

    public Fatura(CasaInteligente casa, int dias) throws FaturaException{
        if(dias<0) throw new FaturaException("Valor de dias negativo");
        this.casa = casa.clone();
        this.dias = dias;
    }

    public Fatura(Fatura f) throws FaturaException {
        if(f.getDias()<0) throw new FaturaException("Valor de dias negativo");
        this.casa = f.getCasa();
        this.dias = f.getDias();
    }

    public CasaInteligente getCasa() {return this.casa.clone();}

    public int getDias() {
        return this.dias;}

    public void setCasa(CasaInteligente casa) throws FaturaException {
        if(casa == null) throw new FaturaException("Casa Inválida");
        this.casa = casa.clone();
    }

    public void setDias(int dias) throws FaturaException{
        if(dias<0) throw new FaturaException("Valor de dias Negativo");
        this.dias = dias;
    }

    public String toString(){
        return "\n### Fatura ###"+
                "\nCasa:" + this.casa.getOwner() +
                "\nFornecedor: " + this.casa.getFornecedor().getName() +
                "\nPeríodo: " + this.dias +
                "\nValor:" + valor_fatura();

    }

    //---------------------------------------------------
    public float valor_fatura(){
        return (float) this.casa.consumoCasa()*dias;
    }
}
