public class SmartDevice {

    private String id;
    private boolean on;
    private float custo_inst;
    private float consumo_diario;


    public SmartDevice() {
        this.id = "";
        this.on = false;
        this.custo_inst=0;
        this.consumo_diario = 0;
    }

    public SmartDevice(String id) {
        this.id = id;
        this.on = false;
        this.custo_inst = 0;
        this.consumo_diario = 0;
    }

    public SmartDevice(String id, boolean state, float custo) {
        this.id = id;
        this.on = state;
        this.custo_inst = custo;
        this.consumo_diario = 0;
    }

    public SmartDevice(SmartDevice smt){
        this.id = smt.getID();
        this.on = smt.getOn();
        this.custo_inst = smt.getCusto();
        this.consumo_diario= smt.getConsumo();
    }

    public String getID() {
        return this.id;
    }

    public boolean getOn() {
        return this.on;
    }

    public float getCusto(){
        return this.custo_inst;
    }

    public float getConsumo(){
        return this.consumo_diario;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOn(boolean b) {
        this.on=b;
    }

    public void setCusto_inst(float custo_inst) {
        this.custo_inst = custo_inst;
    }

    public void setConsumo(float c){
        this.consumo_diario = c;
    }

    public SmartDevice clone(){
        return new SmartDevice(this);
    }

    public boolean equals(Object o){
        if (this==o) return true;
        if (o==null | this.getClass()!=o.getClass()) return false;
        SmartDevice smt = (SmartDevice) o;
        return (this.id.equals(smt.getID()) &&
                (this.on == smt.getOn()) &&
                this.consumo_diario== smt.getConsumo() &&
                this.custo_inst == smt.getCusto());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n### SmartDevice ###")
                .append("\nID: ").append(this.id)
                .append("\nLigado: ").append(this.on)
                .append("\nCusto de Instalação: ").append(this.custo_inst)
                .append("\nConsumo Diário: ").append(this.consumo_diario);

        return sb.toString();
    }

    public void turnOn() {
        this.on = true;
    }

    public void turnOff() {
        this.on = false;
    }

}


