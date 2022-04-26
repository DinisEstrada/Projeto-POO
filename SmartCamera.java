
public class SmartCamera extends SmartDevice {


    private int resolution;
    private int file_size;


    public SmartCamera() {
        super();
        this.resolution = 1080;
        this.file_size = 0;
    }

    public SmartCamera(String id) {
        super(id);
        this.resolution = 1080;
        this.file_size = 0;
    }

    public SmartCamera(String id, boolean state, float custo, int resolution, int file_size) {
        super(id,state,custo);
        this.resolution = resolution;
        this.file_size = file_size;

        super.setConsumo((float)this.resolution*this.file_size);
    }

    public SmartCamera(SmartCamera smt){
        super(smt.getID(), smt.getOn(),smt.getCusto());

        this.resolution = smt.getResolution();
        this.file_size = smt.getFileSize();
        super.setConsumo((float)smt.getResolution()*smt.getFileSize());
    }

    public int getResolution() {
        return this.resolution;
    }

    public int getFileSize() { return this.file_size; }

    public void setResolution(int res) { this.resolution = res; }

    public void setFileSize(int file_size) { this.file_size = file_size; }

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
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString())
                .append("\nType: SmartBulb")
                .append("\nResolution: ").append(this.resolution)
                .append("\nFile Size: ").append(this.file_size);

        return sb.toString();
    }
}


