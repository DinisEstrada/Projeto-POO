import ErrorHandling.CasaInteligenteException;
import ErrorHandling.FaturaException;
import ErrorHandling.FornecedorException;
import ErrorHandling.SmartDeviceException;

public class Fatura {

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

    public String toString(){
        return "\n### Fatura ###"+
                "\nCasa:" + this.casa.getOwner() +
                "\nFornecedor: " + this.casa.getFornecedor().getName() +
                "\nPeríodo: " + this.periodo +
                "\nConsumo" + this.consumo+
                " kWh\nValor:" + this.custo+" €";

    }

    //---------------------------------------------------

    private float consumoFatura(){
        return (float) casa.consumoCasa()*this.periodo;
    }

    public float valor_fatura(){

        return (float) this.casa.getDevices().values().stream()
                .filter(SmartDevice::getOn).mapToDouble(a-> {
                    try {
                        return this.casa.getFornecedor().formulaPreco(a,this.casa)*this.periodo;
                    } catch (SmartDeviceException | CasaInteligenteException | FornecedorException e) {
                        throw new RuntimeException(e);
                    }}).sum() +
                (float) this.casa.getDevices().values().stream().mapToDouble(SmartDevice::getCusto).sum();
    }

}
