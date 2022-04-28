public class SmartSpeaker extends SmartDevice {
    public static final int MAX = 100;
    private int volume;
    private String channel;
    private String brand;

    private float brand_comsuption;

    public SmartSpeaker() {
        super();
        this.volume = MAX/2;
        this.channel = "";
        this.brand = "";
        this.brand_comsuption = 0;
    }

    public SmartSpeaker(String id) {
        super(id);
        this.volume = MAX/2;
        this.channel = "";
        this.brand = "";
        this.brand_comsuption = 0;
    }

    public SmartSpeaker(String id, boolean state, float cost, int vol, String channel, String brand,float bd_comp) {
        super(id,state,cost);

        this.volume = Math.min(vol, MAX);

        this.channel = channel;
        this.brand = brand;
        this.brand_comsuption = bd_comp;

        super.setConsumo(this.brand_comsuption * (float) this.volume);
    }

    public SmartSpeaker(SmartSpeaker smt){
        super(smt.getID(), smt.getOn(),smt.getCusto());
        super.setConsumo(smt.getConsumo());

        this.volume = Math.min(smt.getVolume(), MAX);

        this.channel = smt.getChannel();
        this.brand = smt.getBrand();
        this.brand_comsuption = smt.getBrand_comsuption();
    }


    public int getVolume() {return this.volume;}
    
    public String getChannel() {return this.channel;}

    public String getBrand(){
        return this.brand;
    }

    public float getBrand_comsuption() {return brand_comsuption;}

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void setChannel(String c) {this.channel=c;}

    public void setBrand(String c) {this.brand=c;}

    public void setBrand_comsuption(float brand_comsuption) {this.brand_comsuption = brand_comsuption;}

    public SmartSpeaker clone(){
        return new SmartSpeaker(this);
    }

    public boolean equals(Object o){
        if (!super.equals(o)) return false;
        if (this==o) return true;
        if (this.getClass()!=o.getClass()) return false;
        SmartSpeaker smt = (SmartSpeaker) o;
        return (this.volume==smt.getVolume() &&
                this.getChannel().equals(smt.getChannel()) &&
                this.getBrand().equals(smt.getBrand()))&&
                this.brand_comsuption == smt.getBrand_comsuption();
    }

    public String toString() {
        return super.toString() +
                "\nType: SmartSpeaker" +
                "\nVolume: " + this.volume +
                "\nChannel: " + this.channel +
                "\nBrand: " + this.brand + "\n";
    }


    public void volumeUp() {
        if (this.volume<MAX) this.volume++;
    }

    public void volumeDown() {
        if (this.volume>0) this.volume--;
    }

}

