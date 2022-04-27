public class SmartBulb extends SmartDevice {
    public static final int WARM = 2;
    public static final int NEUTRAL = 1;
    public static final int COLD = 0;
    
    private int tone;
    private int dimensao;
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

    public SmartBulb(String id, boolean state, float custo, int tone, int dimensao, float valor_fixo) {
        super(id,state,custo);

        if (tone>WARM) this.tone = WARM;
        else if (tone<COLD) this.tone = COLD;
        else this.tone = tone;

        this.dimensao = dimensao;
        this.valor_fixo= valor_fixo;

        super.setConsumo(this.valor_fixo+((float)this.tone*0.5f));
    }

    public SmartBulb(SmartBulb smt){
        super(smt.getID(), smt.getOn(),smt.getCusto());

        int t=smt.getTone();
        if (t>WARM) this.tone = WARM;
        else if (t<COLD) this.tone = COLD;
        else this.tone = t;

        this.dimensao = smt.getDimensao();
        this.valor_fixo= smt.getValorFixo();

        super.setConsumo(this.valor_fixo+((float)this.tone*0.5f));
    }

    public int getTone() {
        return this.tone;
    }

    public int getDimensao(){
        return this.dimensao;
    }

    public float getValorFixo(){ return this.valor_fixo;}

    public void setTone(int t) {
        if (t>WARM) this.tone = WARM;
        else if (t<COLD) this.tone = COLD;
        else this.tone = t;
    }

    public void setDimensão(int dimensao){
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
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString())
                .append("\nType: SmartBulb")
                .append("\nTone: ").append(this.tone)
                .append("\nDimensão: ").append(this.dimensao)
                .append("\nValor Fixo: ").append(this.valor_fixo).append("\n");

        return sb.toString();
    }

}
