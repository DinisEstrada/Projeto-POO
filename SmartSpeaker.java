import java.io.File;  // Import the File class
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SmartSpeaker extends SmartDevice {
    public static final int MAX = 20;
    private int volume;
    private String channel;
    private String brand;


    public SmartSpeaker() {
        super();
        this.volume = 10;
        this.channel = "";
        this.brand = "";
    }

    public SmartSpeaker(String id) {
        super(id);
        this.volume = 10;
        this.channel = "";
        this.brand = "";
    }

    public SmartSpeaker(String id, boolean state, float cost, int vol, String channel, String brand) {
        super(id,state,cost);

        if(vol>MAX) this.volume = MAX;
        else this.volume=vol;

        this.channel = channel;

        if(isBrandinFile(brand)) {
            this.brand = brand;
            super.setConsumo(this.getConsumoBrand(brand) * (float) this.volume);
        }
        else{
            System.out.println("Brand not found in config file.");
            super.setConsumo(0);
            this.brand="";
        }
    }

    public SmartSpeaker(SmartSpeaker smt){
        super(smt.getID(), smt.getOn(),smt.getCusto());
        super.setConsumo(smt.getConsumo());

        if(smt.getVolume()>MAX) this.volume = MAX;
        else this.volume=smt.getVolume();

        this.channel = smt.getChannel();
        this.brand = smt.getBrand();
    }


    public int getVolume() {return this.volume;}
    
    public String getChannel() {return this.channel;}

    public String getBrand(){
        return this.brand;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void setChannel(String c) {this.channel=c;}

    public void setBrand(String c) {this.brand=c;}

    public SmartSpeaker clone(){
        return new SmartSpeaker(this);
    }

    public boolean equals(Object o){
        if (!super.equals(o)) return false;
        if (this==o) return true;
        if (this.getClass()!=o.getClass()) return false;
        SmartSpeaker smt = (SmartSpeaker) o;
        return (this.getVolume()==smt.getVolume() &&
                this.getChannel().equals(smt.getChannel()) &&
                this.getBrand().equals(smt.getBrand()));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString())
                .append("\nType: SmartSpeaker")
                .append("\nVolume: ").append(this.volume)
                .append("\nChannel: ").append(this.channel)
                .append("\nBrand: ").append(this.brand);

        return sb.toString();
    }


    public void volumeUp() {
        if (this.volume<MAX) this.volume++;
    }

    public void volumeDown() {
        if (this.volume>0) this.volume--;
    }



    public float getConsumoBrand(String brand){
        try{
            File myFile = new File("brands.txt");
            Scanner sc = new Scanner(myFile);
            while (sc.hasNextLine()) {
                String file_line = sc.nextLine();
                if(file_line.toUpperCase().contains(brand.toUpperCase())){
                    Scanner scanner = new Scanner(file_line);
                    scanner.useDelimiter(";");
                    int cont=0;
                    while (scanner.hasNext()) {
                        cont++;
                        String line = scanner.next();
                        if(cont==2) {
                            return Float.parseFloat(line);
                        }
                    }
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. File not located or not open correctly");
        }
        return 0;
    }

    public boolean isBrandinFile(String brand){
       try{
        File myFile = new File("brands.txt");
        Scanner sc = new Scanner(myFile);
        while (sc.hasNextLine()) {
            String file_line = sc.nextLine();
            if(file_line.toUpperCase().contains(brand.toUpperCase())) return true;
        }
           sc.close();
        } catch (FileNotFoundException e) {
           System.out.println("An error occurred. File not located");
           return false;
       }
       return false;
    }
}

