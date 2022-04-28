
public class SmartBulb extends SmartDevice {
    public static final int WARM = 2;
    public static final int NEUTRAL = 1;
    public static final int COLD = 0;
    
    private int tone;
    private float dimensao;
    private float valor_fixo;


    public SmartBulb() {
        super();
        this.tone = NEUTRAL;
        this.dimensao = 0;
        this.valor_fixo = 0;
    }

    public SmartBulb(String id) {
        super(id);
        this.tone = NEUTRAL;
        this.dimensao = 0;
        this.valor_fixo = 0;
    }

    public SmartBulb(String id, boolean state, float custo, int tone, float dimensao, float valor_fixo) {
        super(id,state,custo);

        if (tone>WARM) this.tone = WARM;
        else this.tone = Math.max(tone, COLD);

        this.dimensao = dimensao;
        this.valor_fixo= valor_fixo;

        super.setConsumo(this.valor_fixo+((float)this.tone*0.5f));
    }

    public SmartBulb(SmartBulb smt){
        super(smt.getID(), smt.getOn(),smt.getCusto());

        int t=smt.getTone();
        if (t>WARM) this.tone = WARM;
        else this.tone = Math.max(t, COLD);

        this.dimensao = smt.getDimensao();
        this.valor_fixo= smt.getValorFixo();

        super.setConsumo(this.valor_fixo+((float)this.tone*0.5f));
    }

    public int getTone() {
        return this.tone;
    }

    public float getDimensao(){
        return this.dimensao;
    }

    public float getValorFixo(){ return this.valor_fixo;}

    public void setTone(int t) {
        if (t>WARM) this.tone = WARM;
        else this.tone = Math.max(t, COLD);
    }

    public void setDimensao(float dimensao){
        this.dimensao= dimensao;
    }

    public void setValorFixo(int valor_fixo) {this.valor_fixo=valor_fixo;}

    public SmartBulb clone(){
        return new SmartBulb(this);
    }

    public boolean equals(Object o){
        if (!super.equals(o)) return false;
        if (this==o) return true;
        if (this.getClass()!=o.getClass()) return false;
        SmartBulb smt = (SmartBulb) o;
        return (this.getTone()==smt.getTone() &&
                this.getDimensao()==smt.getDimensao());
    }

    public String toString() {
        return super.toString() +
                "\nType: SmartBulb" +
                "\nTone: " + this.tone +
                "\nDimensão: " + this.dimensao +
                "\nValor Fixo: " + this.valor_fixo + "\n";
    }

}

