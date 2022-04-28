import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * O interpreter recebe um ficheiro config.csv e cria a configuração inicial a partir do mesmo.
 * Esta configuração inicial significa que através do metodo houseConfig é criada um hashmap com as keys sendo os nomes da casa associados às suas casas inteligentes
 *
 * @author josefonte
 * @version 0.1
 */

public class Parser {
    private  File configfile;

    public Parser(){
        this.configfile =  new File("");
    }

    public Parser(String filepath){
        this.configfile =  new File(filepath);
    }

    public Parser(Parser myfile){
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
        Parser file = (Parser) o;
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

    public Parser clone(){
        return new Parser(this);
    }

    //------------------------------------------------------------------------------------------

    public HashMap<String,CasaInteligente> housesConfig(){
        List<String> linhas = lerFicheiro();
        String[] linhaPartida;

        HashMap<String,Fornecedor> fornecedores = new HashMap<>();
        HashMap<String,CasaInteligente> lista_casas = new HashMap<>();

        CasaInteligente casaMaisRecente = null;
        String divisao = null;
        Random num= new Random();
        int cont=0;

        for (String linha : linhas) {
            linhaPartida = linha.split(":", 2);
            switch(linhaPartida[0]){
                case "Fornecedor" -> {
                    if (!fornecedores.containsKey(linhaPartida[1])) {

                        Fornecedor fn = null;
                        String nome_fornecedor = linhaPartida[1];
                        int num_random = num.nextInt(3);

                        switch (num_random){
                            case(0) -> fn = new FornecedorA(nome_fornecedor,1,10,5);

                            case(1) -> fn = new FornecedorB(nome_fornecedor,2,15,10);

                            case(2) -> fn = new FornecedorC(nome_fornecedor,3,20,15);
                        }
                        fornecedores.put(nome_fornecedor,fn);
                    }
                }
                case "Casa" -> {
                    String key = "casa" + cont;

                    cont ++;
                    casaMaisRecente = createCasa(linhaPartida[1], fornecedores);
                    lista_casas.put(key, casaMaisRecente);
                }
                case "Divisao" -> {
                    if (casaMaisRecente == null) System.out.println("Divisão - Linha inválida.");
                    divisao = linhaPartida[1];
                    if (!casaMaisRecente.hasRoom(divisao))
                        casaMaisRecente.addRoom(divisao);
                }
                case "SmartBulb" -> {
                    if (divisao == null) System.out.println("SmartBulb - Linha inválida.");
                    String[] campos = linhaPartida[1].split(",");
                    StringBuilder nome = new StringBuilder();
                    int rand_num = num.nextInt(999999);
                    nome.append("smtblb-").append(rand_num);

                    int tone = -1;
                    switch (campos[0]){
                        case "Warm" -> tone=2;
                        case "Neutral" -> tone=1;
                        case "Cold" -> tone=0;
                    }
                    float valor_fixo = 1.4f;
                    float dimensao = Float.parseFloat(campos[1]);

                    SmartDevice sd = new SmartBulb(nome.toString(), false, 1.0f, tone, dimensao, valor_fixo);

                    casaMaisRecente.addDevice(sd, divisao);
                }
                case "SmartCamera" -> {
                    if (divisao == null) System.out.println("SmartCamera - Linha inválida.");

                    String[] campos = linhaPartida[1].split("[(,x)]");

                    StringBuilder nomesb = new StringBuilder();
                    int rand_num = num.nextInt(999999);
                    nomesb.append("smtcmr-").append(rand_num);
                    String nome = nomesb.toString();

                    float width = Float.parseFloat(campos[1]);
                    float heigth = Float.parseFloat(campos[2]);

                    Resolution res = new Resolution(width,heigth);

                    float file_size = Float.parseFloat(campos[4]);
                    float compresao = Float.parseFloat(campos[5]);

                    SmartDevice sd = new SmartCamera(nome, false, 2.0f,res,file_size,compresao);

                    casaMaisRecente.addDevice(sd, divisao);
                }
                case "SmartSpeaker" -> {
                    if (divisao == null) System.out.println("SmartSpeaker - Linha inválida.");

                    String[] campos = linhaPartida[1].split(",");

                    StringBuilder nomesb = new StringBuilder();
                    int rand_num = num.nextInt(999999);
                    nomesb.append("smtspk-").append(rand_num);
                    String nome = nomesb.toString();

                    int volume = Integer.parseInt(campos[0]);
                    String channel = campos[1];
                    String brand = campos[2];

                    SmartDevice sd = new SmartSpeaker(nome, false, 3.0f, volume, channel, brand, 1.5f);

                    casaMaisRecente.addDevice(sd, divisao);
                }

                default -> System.out.println("Linha Inválida/Vazia");
            }
        }
        //lista_casas.remove("casa0");
        return lista_casas;
    }

    public CasaInteligente createCasa(String input, HashMap<String,Fornecedor> forns){
        String[] campos = input.split(",");

        String nome = campos[0];
        int nif = Integer.parseInt(campos[1]);
        Fornecedor fornecedor = forns.get(campos[2]);

        return new CasaInteligente(nome, nif,fornecedor);
    }

    public HashMap<String,Fornecedor> energyConfig() {

        HashMap<String, Fornecedor> lista_fns = new HashMap<>();
        List<String> file_lines = lerFicheiro();
        String[] linhaPartida;
        Fornecedor fn = null;

        for (String linha : file_lines) {

            linhaPartida = linha.split(":", 2);

            if(linhaPartida[0].equals("Fornecedor")){
                if (!lista_fns.containsKey(linhaPartida[1])) {
                    Random num= new Random();
                    int num_random = num.nextInt(3);
                    switch (num_random){
                        case(0) ->fn = new FornecedorA(linhaPartida[1],1,10,5);
                        case(1) ->fn = new FornecedorB(linhaPartida[1],2,15,10);
                        case(2) ->fn = new FornecedorC(linhaPartida[1],3,20,15);
                    }
                }
                lista_fns.put(linhaPartida[1],fn);
            }
        }
        return lista_fns;
    }

    public List<String> lerFicheiro() {
        List<String> lines = new ArrayList<>();
        try {
            Scanner sc = new Scanner(this.configfile);
            String file_line;
            while (sc.hasNextLine()){
                file_line= sc.nextLine();
                lines.add(file_line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. File not located or not open correctly");
        }
        return lines;
    }

    public Set<String> casas(){return housesConfig().keySet();}

    public CasaInteligente getCasaInteligente(String housename){
        return housesConfig().get(housename).clone();
    }

    public Fornecedor getFornecedor(String housename){
        return energyConfig().get(housename);
    }

    public int getNumberCasas(){
        return housesConfig().keySet().size();
    }

}
