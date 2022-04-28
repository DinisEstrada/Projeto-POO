public class SmartCamera extends SmartDevice {


    private Resolution resolution;
    private float file_size;
    private float compression;


    public SmartCamera() {
        super();
        this.resolution = new Resolution();
        this.file_size = 0;
        this.compression = 0;
    }

    public SmartCamera(String id) {
        super(id);
        this.resolution = new Resolution();
        this.file_size = 0;
        this.compression = 0;
    }

    public SmartCamera(String id, boolean state, float custo, Resolution resolution, float file_size, float compression) {
        super(id,state,custo);
        this.resolution = resolution;
        this.file_size = file_size;
        this.compression = compression;

        float consumo = (this.resolution.getRes()*this.file_size) * this.compression;
        super.setConsumo(consumo);
    }

    public SmartCamera(SmartCamera smt){
        super(smt.getID(), smt.getOn(),smt.getCusto());

        this.resolution = smt.getResolution();
        this.file_size = smt.getFileSize();
        this.compression = smt.getCompression();

        float consumo = (smt.getResolution().getRes()*smt.getFileSize()) * smt.getCompression();

        super.setConsumo(consumo);
    }

    public Resolution getResolution() {
        return this.resolution;
    }

    public float getFileSize() { return this.file_size; }

    public float getCompression() {return this.compression;}

    public void setResolution(Resolution res) { this.resolution = res; }

    public void setFileSize(float file_size) { this.file_size = file_size; }

    public void setCompression(float compression) {this.compression = compression;}

    public SmartCamera clone(){
        return new SmartCamera(this);
    }

    public boolean equals(Object o){
        if (!super.equals(o)) return false;
        if (this==o) return true;
        if (this.getClass()!=o.getClass()) return false;
        SmartCamera smt = (SmartCamera) o;
        return (this.getResolution()==smt.getResolution() &&
                this.getFileSize()==smt.getFileSize());
    }

    public String toString() {
        return super.toString() +
                "\nType: SmartBulb" +
                this.resolution +
                "\nFile Size: " + this.file_size + "\n";
    }
}


