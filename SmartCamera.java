import ErrorHandling.ResolutionException;
import ErrorHandling.SmartBulbException;
import ErrorHandling.SmartCameraException;
import ErrorHandling.SmartDeviceException;

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

    public SmartCamera(String id, boolean state, float custo, Resolution resolution, float file_size, float compression) throws SmartDeviceException, SmartCameraException, ResolutionException {
        super(id,state,custo);

        if (resolution.getHeight()<0 || resolution.getWidth()<0) throw new ResolutionException(id+ " Resolution : Valor(es) Negativo(s)");
        if (file_size<0 || compression<0) throw new SmartCameraException("Valores Negativos");

        this.resolution = resolution;
        this.file_size = file_size;
        this.compression = compression;

        float consumo = (float) Math.sqrt((double) this.resolution.getRes()*this.file_size) * this.compression/100;
        super.setConsumo(consumo);

    }

    public SmartCamera(SmartCamera smt)  throws SmartDeviceException, SmartCameraException, ResolutionException {
        super(smt.getID(), smt.getOn(),smt.getCusto());

        if (smt.getResolution().getHeight()<0 || smt.getResolution().getWidth()<0) throw new ResolutionException(smt.getID()+" Resolution : Valor(es) Negativos");
        if (file_size<0 || compression<0) throw new SmartCameraException("Valores Negativos");

        this.resolution = smt.getResolution();
        this.file_size = smt.getFileSize();
        this.compression = smt.getCompression();

        float consumo = (float) Math.sqrt((double)smt.getResolution().getRes()*smt.getFileSize()) * smt.getCompression()/100;

        super.setConsumo(consumo);
    }

    public Resolution getResolution() {
        return this.resolution;
    }

    public float getFileSize() { return this.file_size; }

    public float getCompression() {return this.compression;}

    public void setResolution(Resolution res) throws ResolutionException{
        if (resolution.getHeight()<0 || resolution.getWidth()<0) throw new ResolutionException(" Atribuição de Valor(es) Negativo de Resolução");
        this.resolution = res;
    }

    public void setFileSize(float file_size) throws SmartCameraException {
        if(file_size<0) throw new SmartCameraException(" Atribuição de Valor Negativo de Tamanho de Ficheiro");
        this.file_size = file_size; }

    public void setCompression(float compression) throws SmartCameraException {
        if(compression<0) throw new SmartCameraException(" Atribuição de Valor Negativo de Compressão");
        this.compression = compression;
    }

    public SmartCamera clone(){
        try {
            return new SmartCamera(this);
        } catch (SmartDeviceException | SmartCameraException | ResolutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object o){
        if (!super.equals(o)) return false;
        if (this==o) return true;
        if (!(o instanceof SmartCamera)) return false;
        SmartCamera smt = (SmartCamera) o;
        return (this.resolution.equals(smt.getResolution()) &&
                this.file_size==smt.getFileSize() &&
                this.compression == smt.getCompression());
    }

    public String toString() {
        return super.toString() +
                "\nType: SmartBulb" +
                this.resolution +
                "\nFile Size: " + this.file_size + "\n";
    }
}


