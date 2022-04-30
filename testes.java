
import ErrorHandling.*;

import java.io.FileNotFoundException;
import java.util.*;

public class testes {

    public static void main(String[] args) {

        try {
            Parser hq = new Parser("logs.csv");

            CasaInteligente casa1 = new CasaInteligente(hq.housesConfig().get("casa1"));
            CasaInteligente casa1_cp = casa1.clone();

            FornecedorA f1 = new FornecedorA("Galp",1,10,15);
            FornecedorA f2 = new FornecedorA("EDP",2,15,15);
            FornecedorA f3 = new FornecedorA("Iberdrola",3,20,15);

            Set<Fornecedor> fornecedors = new HashSet<>();
            fornecedors.add(f1);
            fornecedors.add(f2);
            fornecedors.add(f3);

            fornecedors.stream().filter(f -> f.getName().equals("Galp")).forEach(System.out::println);

            System.out.println();



        } catch (Exception e) {
            System.out.println(e);
        }

    }
}