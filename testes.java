
import ErrorHandling.*;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Supplier;

public class testes {

    public static void main(String[] args) {

        try {
            Parser hq = new Parser("logs.csv");

            CasaInteligente casa1 = hq.housesConfig().get("Miguel Velho Raposo");

            List<Integer> list = new ArrayList<>();
            casa1.getDevices().values().stream().map(SmartDevice::getID).collect(list);
            System.out.println();
            System.out.println(casa1.faturaCasa(1));



        } catch (Exception e) {
            System.out.println(e);
        }

    }
}