//lê o ficheiro de config
/*Metodos
* - lista de casas
* - lista de devices por casa
* - lista de deviçoes por casa
* - lista de devices por divisão
*
* - criar hashmap dos speakers
* -
*
*
*/
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;


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

        try {
            return FileUtils.contentEquals(configfile, file);
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
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

    /*public List<CasaInteligente> lista_casas(){

    }

    public List<SmartDevice> lista_dvs(String Casa){

    }

    public List<SmartDevice> lista_dvs_divisao(String divisao){

    }*/


}
