import java.util.*;
import java.io.*;

public class ControllerDispositivo {
        public static void run() {
            
    
            boolean errorMessage = false;
            while(true){
                int opcao = -1;
                while(opcao < 0 || opcao > 7) {
                    opcao = Menu.MenuCarregarEstado();
                }
           
                switch(opcao) {
                    
                    case 1:
                        
                        break;
                        
                    case 2:


                        break;
                        
                    case 3:
                        System.exit(0);
                        break;
                    case 0:
                        System.exit(0);
                        break;
                        
                }
            }
        }
    }