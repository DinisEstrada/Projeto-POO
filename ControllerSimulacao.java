import java.util.*;

import ErrorHandling.SmartBulbException;

import java.io.*;

public class ControllerSimulacao {
        public static void run() {
            
            boolean exit = false;
            boolean errorMessage = false;
            while(!exit){
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