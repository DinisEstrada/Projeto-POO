
import ErrorHandling.*;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Supplier;

public class testes {

    public static void main(String[] args) {

        try {
            Parser hq = new Parser("logs.csv");

            Estado state = new Estado(hq.housesConfig(),hq.energyConfig());

            System.out.println(state.casaMaisGastou().faturaCasa(1));
            System.out.println(state.casaMaisGastou().faturaCasa(30));

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}