import java.util.*;
import java.text.ParseException;
import ErrorHandling.*;

import java.io.*;

public class ControllerCarregarDados {
        public static void run(Estado estado) {
            
            boolean exit = false;
            boolean errorMessage = false;
            while(!exit){
                int opcao = -1;
                while(opcao < 0 || opcao > 7) {
                    opcao = Menu.MenuCarregarEstado();
                }
           
                switch(opcao) {
                    
                    case 1:
                        HashMap<String, Fornecedor> fornecedores = estado.getFornecedores();
                        
                        if(fornecedores.isEmpty()) {
                            System.out.println("Não existem fornecedores disponiveis");
                            Menu.pressEnter();
                            Menu.clearWindow();
                        }
                        else {
                            CasaInteligente casa = Menu.MenuCriarCasa(estado);    
                            Menu.escolherFornecedor(estado, casa);
                            estado.adicionaCasa(casa);
                        }
                        break;

                    case 2:
                        
                        AbstractMap.SimpleEntry<String, String> dados = Menu.menuDispositivo();
                        
                        if(estado.getCasas().containsKey(dados.getKey()) && estado.getCasas().get(dados.getKey()).hasRoom(dados.getValue())) {
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
                        }

                        else {
                            System.out.println("\nAs informações sobre a casa e/ou a sala são inválidas");
                            Menu.pressEnter();
                            Menu.clearWindow();
                        }
                        
                        break;
                    
                    case 3:
                        Menu.menuCriaFornecedor(estado);    
                        break;
                    
                    case 4:
                        try {estado = Estado.carregaEstado("logs.csv");}
                        catch (FileNotFoundException e) {System.out.print(e + "\n");}
                        catch (IOException e) {System.out.print(e + "\n");}
                        catch (ClassNotFoundException e) {System.out.print(e + "\n");}
                        Menu.pressEnter();
                        
                        break;
                    
                    case 0:
                        exit = true;
                        Menu.clearWindow();
                        break;
                        
                }
            }
        }
    }