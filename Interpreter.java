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
    private  File configfile;

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
                sb.append(sc.nextLine()).append("\n");
            }
        }catch (FileNotFoundException e){
            System.out.println("An error occurred. File not located or not open correctly");
        }

        return sb.toString();
    }

    public Interpreter clone(){
        return new Interpreter(this);
    }

    //------------------------------------------------------------------------------------------
    public HashMap<String, CasaInteligente> housesConfig(){

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
            sc.close();
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

            casa.setFornecedor(getFornecedor(housename));

            while (sc.hasNextLine()){
                file_line= sc.nextLine();
                parser= file_line.split(";");

                if(parser[0].equals("houses-config") && parser[1].equals(housename)){
                    casa.setOwner(parser[2]);
                    casa.setNif(parser[3]);
                } //adiciona owner e nif

                if(parser[0].equals(housename)){ //Adiciona os devices
                    String room = parser[1];
                    String[] parser2 = parser[2].split("-");
                    SmartDevice smt = null;

                    switch (parser2[0]) {
                        case "smtblb" -> {
                            boolean state = Boolean.parseBoolean(parser[3]);
                            float custo = Float.parseFloat(parser[4]);
                            int dimensao = Integer.parseInt(parser[6]);
                            float valor_fixo = Float.parseFloat(parser[7]);
                            int tone = 1;

                            switch (parser[5]){
                                case "warm" -> {tone = 2;}
                                case "neutral" -> {tone = 1;}
                                case "cold" -> {tone = 0;}
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

                    if(!casa.existsDevice(smt.getID()))
                        if(casa.hasRoom(parser[1]))
                            casa.addDevice(smt,parser[1]);
                        else {
                            casa.addRoom(parser[1]);
                            casa.addDevice(smt,parser[1]);
                        }
                    else System.out.println("Device "+smt.getID()+ " already exists");
                } //adicionaDevices e adicionaLocations
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. File not located or not open correctly");
        }

        return casa;
    }


    public HashMap<String,Fornecedor> energyConfig(){

        HashMap<String,Fornecedor> lista_fns = new HashMap<>();
        List<Float> lista = new ArrayList<>();

        try {
            Scanner sc = new Scanner(this.configfile);
            String file_line;
            String[] parser;

            while (sc.hasNextLine()){
                file_line= sc.nextLine();
                parser= file_line.split(";");

                if(parser[0].equals("energy-suppliers-config")) {
                    float valor_base = Float.parseFloat(parser[1]);
                    float imposto =  Float.parseFloat(parser[2]);
                    lista.add(valor_base);
                    lista.add(imposto);
                }

                if(parser[0].equals("energy-supplier")){
                    float desconto= Float.parseFloat(parser[3]);
                    Fornecedor fornecedor = null;
                    switch (parser[1]){
                        case "fornecedorA" ->  {
                            fornecedor = new FornecedorA(parser[2],lista.get(0),lista.get(1),desconto);
                        }
                        case "fornecedorB" ->  {
                            fornecedor = new FornecedorB(parser[2],lista.get(0),lista.get(1),desconto);
                        }
                        case "fornecedorC" ->  {
                            fornecedor = new FornecedorC(parser[2],lista.get(0),lista.get(1),desconto);
                        }
                    }
                    String[] parser2;
                    parser2 = parser[4].split("-");
                    for(int i=0; i<parser2.length; i++) lista_fns.put(parser2[i],fornecedor);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. File not located or not open correctly");
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println(lista);
        }

        return lista_fns;
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


    public Set<String> casas(){
        return housesConfig().keySet();
    }

    public CasaInteligente getCasaInteligente(String housename){
        return housesConfig().get(housename).clone();
    }

    public Fornecedor getFornecedor(String housename){
        return energyConfig().get(housename);
    }
}

