import java.util.*;

import ErrorHandling.SmartBulbException;

import java.io.*;

public class ControllerSimulacao {
        public static void run(Estado estado) {
            
            Estado old_estado = estado;
            Estado new_estado = estado.clone();

            boolean exit = false;
            boolean errorMessage = false;
            while(!exit){
                int opcao = -1;
                while(opcao < 0 || opcao > 7) {
                    opcao = Menu.MenuSimulacao();
                }
           
                switch(opcao) {
                    
                    case 1:
                        Menu.avancarTempo(new_estado);
                        if(new_estado.getDate().isAfter(old_estado.getDate())) {
                            //calcular consumo de cada casa
                            
                        }
                        else {
                            System.out.print("Data inválida");
                        }
                        
                        break;
                        
                    case 2:
                        ControllerMudarEstado.run(new_estado);
                        break;

                    case 3:
                        
                        break;
                    
                    case 4:

                    
                    case 0:
                        exit = true;
                        Menu.clearWindow();
                        break;
                        
                }
            }
        }
    }