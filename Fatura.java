import ErrorHandling.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Fatura implements Comparable, Serializable {

    SimpleDateFormat sdf
            = new SimpleDateFormat(
            "dd-MM-yyyy");

    private CasaInteligente casa;

    private String inicio_faturacao;
    private String fim_faturacao;

    private float consumo;
    private float custo;

    public Fatura(){
        this.casa = new CasaInteligente();
        this.inicio_faturacao = "";
        this.fim_faturacao = "";
        this.consumo=0;
        this.custo=0;
    }

    public Fatura(CasaInteligente casa, String inicio, String fim) throws FaturaException, ParseException {
        if (perido_fat(inicio,fim)<0) throw new FaturaException("Data de ínicio posterior à Data de fim");

        this.casa = casa.clone();
        this.inicio_faturacao = inicio;
        this.fim_faturacao = fim;
        this.consumo = consumoFatura();
        this.custo= valor_fatura();
    }

    public Fatura(Fatura f) throws FaturaException, ParseException {
        if (perido_fat(f.getInicio_faturacao(),f.getFim_faturacao())<0) throw new FaturaException("Data de ínicio posterior à Data de fim");
        if(f.getConsumo()<0 || f.getCusto()<0) throw new FaturaException("Valor de dias negativo");

        this.inicio_faturacao = f.getInicio_faturacao();
        this.fim_faturacao = f.getFim_faturacao();
        this.casa = f.getCasa();
        this.consumo = f.getConsumo();
        this.custo = f.getCusto();
    }

    public CasaInteligente getCasa() {return this.casa.clone();}

    public String getInicio_faturacao() {
        return this.inicio_faturacao;
    }

    public String getFim_faturacao() {
        return this.fim_faturacao;
    }
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

    public void setInicio_faturacao(String inicio_faturacao) {
        this.inicio_faturacao = inicio_faturacao;
    }

    public void setFim_faturacao(String fim_faturacao) {
        this.fim_faturacao = fim_faturacao;
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
        } catch (FaturaException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public String toString(){
        try {
            return "\n###### Fatura ######"+
                    "\nCasa: " + this.casa.getOwner() +
                    "\nFornecedor: " + this.casa.getFornecedor().getName() + " | " + this.casa.getFornecedor().getClass().getName() +
                     "\nInicio: " + this.inicio_faturacao + " | Fim: " + this.fim_faturacao + " | Período: " + perido_fat(this.inicio_faturacao,this.fim_faturacao) +
                    " dia(s)\nConsumo: " + this.consumo+
                    " kWh\nValor: " + this.custo+" €";
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean equals(Object o){
        if (this==o) return true;
        if (o==null || this.getClass()!=o.getClass()) return false;
        Fatura ft = (Fatura) o;
        return (this.casa.equals(ft.getCasa()) &&
                this.inicio_faturacao == ft.getInicio_faturacao() &&
                this.fim_faturacao == ft.getFim_faturacao() &&
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

    private float consumoFatura() throws ParseException {
        return (float) casa.consumoCasa()*perido_fat(this.inicio_faturacao, this.fim_faturacao);
    }


    public float valor_fatura() throws ParseException {

        return (float) this.casa.custoCasa()*perido_fat(this.inicio_faturacao, this.fim_faturacao);
    }


    public long difTime(String inicio, String fim) throws ParseException {
        Date d1 = sdf.parse(inicio);
        Date d2 = sdf.parse(fim);
        return d2.getTime() - d1.getTime() ;
    }

    public long perido_fat(String inicio, String fim) throws ParseException {

        return (difTime(inicio,fim) / (1000 * 60 * 60 * 24)) % 365;
    }
}
