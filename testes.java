
import ErrorHandling.*;

import java.io.FileNotFoundException;
import java.util.*;

public class testes {

    public static void main(String[] args) {

        try {
            Parser hq = new Parser("logs.csv");

            CasaInteligente casa1og= new CasaInteligente(hq.housesConfig().get("casa0"));
            casa1og.guardaCasa("casa1");

            //System.out.println(casa12);

            CasaInteligente casa1_file = new CasaInteligente();
            CasaInteligente casa1= new CasaInteligente(casa1_file.carregaCasa("casa1"));

            //System.out.println(casa1og);
            //System.out.println(casa1);

            System.out.println(casa1og.getDevices().keySet());
            System.out.println(casa1.getDevices().keySet());
            //System.out.println(casa1og.getDevices().equals(casa1.getDevices()));

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}