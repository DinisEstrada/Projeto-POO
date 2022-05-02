
import ErrorHandling.*;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class testes {

    public static void main(String[] args) {

        try {
            Parser hq = new Parser("logs.csv");

            Estado state = new Estado(hq.housesConfig(),hq.energyConfig());

            state.casasMaiorConsumo(5);
            //System.out.println(state.casaMaisGastou().faturaCasa(5));
            //System.out.println(state.casasMaiorConsumo(5));

            for(CasaInteligente casa : state.casasMaiorConsumo(5)){
                System.out.println(casa.faturaCasa(5));
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}