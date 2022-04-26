import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * O interpreter recebe um ficheiro config e cria a configuração inicial apartir do mesmo.
 * Esta configuração inicial significa que é criada um hashmap com as keys sendo os nomes da casa associados às suas casa inteligentes
 *
 * @author josefonte
 * @version 0.1
 */

public class Interpreter {
    File configfile;

    public Interpreter(){
        this.configfile =  new File("");
    }

    public Interpreter(String filepath){
        this.configfile =  new File(filepath);
    }

    public Interpreter(Interpreter myfile){
        this.configfile = myfile.getFile();
    }

    public File getFile(){
        return this.configfile;
    }

    public void setFile(File new_file){
        this.configfile = new_file;
    }

    public boolean equals(Object o){
        if (this==o) return true;
        if (this.getClass()!=o.getClass()) return false;
        Interpreter file = (Interpreter) o;
        return true;
        /*try {
           // return FileUtils.contentEquals(configfile, file);
            return true;
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }*/
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        try{
            Scanner sc = new Scanner(this.configfile);
            while (sc.hasNextLine()) {
                sb.append(sc.nextLine());
            }
        }catch (FileNotFoundException e){
            System.out.println("An error occurred. File not located or not open correctly");
        }

        return sb.toString();
    }

    public Interpreter clone(){
        return new Interpreter(this);
    }




    public HashMap<String, CasaInteligente> creator(){

        HashMap<String,CasaInteligente> config = new HashMap<>();

        try {
            Scanner sc = new Scanner(this.configfile);
            String file_line;
            String[] parser;
            while (sc.hasNextLine()){
                file_line= sc.nextLine();
                parser= file_line.split(";");
                if(parser[0].equals("houses-config")) config.put(parser[1], createhouse(parser[1]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. File not located or not open correctly");
        }

        return config;
    }




    public CasaInteligente createhouse(String housename){

        CasaInteligente casa = new CasaInteligente();

        try {
            Scanner sc = new Scanner(this.configfile);
            String file_line;
            String[] parser;

            while (sc.hasNextLine()){
                file_line= sc.nextLine();
                parser= file_line.split(";");

                if(parser[0].equals("houses-config") && parser[1].equals(housename)){
                    casa.setOwner(parser[2]);
                    casa.setNif(parser[3]);
                    String[] parser2 = parser[4].split("-");
                    for (int i=0;i<parser2.length;i++) casa.addRoom(parser2[i]);
                }

                if(parser[0].equals(housename)){
                    String room = parser[1];
                    String[] parser2 = parser[2].split("-");
                    SmartDevice smt = new SmartDevice();

                    switch (parser2[0]) {
                        case "smtblb" -> {
                            boolean state = Boolean.parseBoolean(parser[3]);
                            float custo = Float.parseFloat(parser[4]);
                            int dimensao = Integer.parseInt(parser[6]);
                            float valor_fixo = Float.parseFloat(parser[7]);
                            int tone=0;

                            switch (parser[5]){
                                case "neutral" :  tone = SmartBulb.NEUTRAL;
                                case "warm" :  tone = SmartBulb.WARM;
                                case "cold" :  tone = SmartBulb.COLD;
                            }

                            smt = new SmartBulb(parser[2], state, custo, tone, dimensao, valor_fixo);

                            break;
                        }
                        case "smtspk" -> {
                            boolean state = Boolean.parseBoolean(parser[3]);
                            float custo = Float.parseFloat(parser[4]);
                            int volume = Integer.parseInt(parser[5]);

                            smt = new SmartSpeaker(parser[2], state, custo, volume, parser[6], parser[7]);
                            break;
                        }
                        case "smtcmr" -> {
                            boolean state = Boolean.parseBoolean(parser[3]);
                            float custo = Float.parseFloat(parser[4]);
                            int resolution = Integer.parseInt(parser[5]);
                            int file_size = Integer.parseInt(parser[6]);

                            smt = new SmartCamera(parser[2], state, custo, resolution, file_size);
                            break;
                        }
                    }

                    casa.addDevice(smt);

                    if(casa.hasRoom(parser[1])) casa.addDeviceToRoom(room, smt.getID());
                    else {
                        List<String> devs_ids = new ArrayList<>();
                        devs_ids.add(smt.getID());
                        casa.addRoom(room,devs_ids);
                    }

                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. File not located or not open correctly");
        }

        return casa;
    }


    public HashMap<String, Float> speakerConfig(){
        HashMap<String,Float> speaker_config = new HashMap<>();

        try {
            Scanner sc = new Scanner(this.configfile);
            String file_line;
            String[] parser;
            while (sc.hasNextLine()){
                file_line= sc.nextLine();
                parser= file_line.split(";");
                if(parser[0].equals("speaker-brands-config")) speaker_config.put(parser[1], Float.parseFloat(parser[2]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. File not located or not open correctly");
        }

        return speaker_config;
    }

/*
    public HashMap<String, String> lista_casasEdivisoes(){
        HashMap<String,String> houses_config = new HashMap<>();

        try {
            Scanner sc = new Scanner(this.configfile);
            String file_line;
            String[] parser;
            while (sc.hasNextLine()){
                file_line= sc.nextLine();
                parser= file_line.split(";");
                if(parser[0].equals("houses-config")) houses_config.put(parser[1], Float.parseFloat(parser[2]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. File not located or not open correctly");
        }

        return houses_config;

    }


    public List<String> getHouses(){
        List<String> houses =  new ArrayList<>();



        return houses;

    }

     public CasaInteligente createhouse(String housename){


     }

    public List<SmartDevice> lista_dvs(String Casa){

    }

    public List<SmartDevice> lista_dvs_divisao(String divisao){

    }*/

    public Set<String> casas(){
        return creator().keySet();
    }

    public CasaInteligente getCasaInteligente(String housename){
        return creator().get(housename);
    }



}

