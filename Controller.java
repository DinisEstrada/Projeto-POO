import java.util.*;

import java.io.*;

public class Controller {
    public static void run() {
        
        Estado estado = new Estado();
        boolean errorMessage = false;
        
        while(true){
            int opcao = -1;
            while(opcao < 0 || opcao > 7) {
                opcao = Menu.MenuInicial();
            }
       
            switch(opcao) {
                
                case 1:
                    System.exit(0);
                    break;
                    
                case 2:
                    ControllerCarregarDados.run(estado);
                    break;
                    
                case 3:
                    
                    break;
                case 0:
                    System.exit(0);
                    break;
                    
            }
        }
    }
}