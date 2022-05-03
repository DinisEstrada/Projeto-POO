import java.util.*;

import ErrorHandling.SmartBulbException;

import java.io.*;

public class ControllerCarregarDados {
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
                        CasaInteligente casa = Menu.MenuCriarCasa();
                        break;
                        
                    case 2:
                        
                        AbstractMap.SimpleEntry<String, String> dados = Menu.menuDispositivo();
                        
                            
                            DeviceType devicetype = null;
                            while(devicetype == null) devicetype = Menu.menuDispositivo2();
                        
                            if(devicetype.equals(DeviceType.SmartBulb)) {
                                SmartBulb bulb = Menu.menuSmartBulb();
                                
                            }
                            else if (devicetype.equals(DeviceType.SmartCamera)) {
                                SmartCamera camera = Menu.menuSmartCamera();
                            }
                            else {
                                SmartSpeaker speaker = Menu.menuSmartSpeaker();
                            }
                        
                        break;
                        
                    case 3:
                        System.exit(0);
                        break;
                    case 0:
                        exit = true;
                        Menu.clearWindow();
                        break;
                        
                }
            }
        }
    }