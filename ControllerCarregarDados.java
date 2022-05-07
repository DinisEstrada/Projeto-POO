import java.util.*;
import java.text.ParseException;
import ErrorHandling.*;

import java.io.*;

public class ControllerCarregarDados {
        public static void run(Estado estado) {
            
            boolean exit = false;
            //boolean errorMessage = false;
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
                            boolean i;
                            do {
                                CasaInteligente casa = Menu.MenuCriarCasa(estado);    
                                Menu.escolherFornecedor(estado, casa);
                                try {estado.adicionaCasa(casa); i=false;}
                                catch(EstadoException e) {System.out.println(e + "\n"); i=true;}
                            } while(i);
                        }
                        break;

                    case 2:
                        

                    if (!estado.getCasas().isEmpty()) {
                        AbstractMap.SimpleEntry<String, String> dados = Menu.menuDispositivo(estado);

                        if(estado.getCasas().containsKey(dados.getKey()) && estado.getCasas().get(dados.getKey()).hasRoom(dados.getValue())) {
                            
                            CasaInteligente casa = estado.getCasas().get(dados.getKey());
                            String room = dados.getValue();

                            DeviceType devicetype = null;
                            boolean i;

                            while(devicetype == null) devicetype = Menu.menuDispositivo2();
                            
                            if(devicetype.equals(DeviceType.SmartBulb)) {
                                do {
                                    SmartBulb bulb = Menu.menuSmartBulb();
                                    try {casa.addDevice(bulb,room); i=false;}
                                    catch (CasaInteligenteException e) {System.out.print(e + "\n"); i=true;}
                                } while(i);
                            }
                            else if (devicetype.equals(DeviceType.SmartCamera)) {
                                do {
                                    SmartCamera camera = Menu.menuSmartCamera(); 
                                    try {casa.addDevice(camera,room); i=false;}
                                    catch (CasaInteligenteException e) {System.out.print(e + "\n"); i=true;}
                                } while(i); 
                            }
                            else {
                                do {
                                    SmartSpeaker speaker = Menu.menuSmartSpeaker();
                                    try {casa.addDevice(speaker,room); i=false;}
                                    catch (CasaInteligenteException e) {System.out.print(e + "\n"); i=true;}
                                } while(i); 
                            }
                        }

                        else {
                            System.out.println("\nAs informações sobre a casa e/ou a sala são inválidas");
                            Menu.pressEnter();
                            Menu.clearWindow();
                        }
                    }

                    else {
                        System.out.println("\nNão existem casas");
                        Menu.pressEnter();
                        Menu.clearWindow();
                    }
                        
                        break;
                    
                    case 3:
                        Menu.menuCriaFornecedor(estado);    
                        break;
                    
                    case 4:
                        
                        Parser file = new Parser("logs.csv");
                        HashMap<String,CasaInteligente> house;
                        HashMap<String,Fornecedor> fornecedor;
                        
                        try {
                            house = file.housesConfig();
                            for(String name: house.keySet()) {
                                CasaInteligente casa = house.get(name);
                                try {estado.adicionaCasa(casa);}
                                catch (EstadoException e) {System.out.print(e + "\n");}
                            }
                        }
                        catch(CasaInteligenteException | SmartDeviceException | FornecedorException | SmartBulbException | 
                            ResolutionException | SmartCameraException | 
                            SmartSpeakerException | FileNotFoundException e) {System.out.print(e + "\n");}

                        try {
                            fornecedor = file.energyConfig();
                            for(String forn: fornecedor.keySet()) {
                                Fornecedor forns = fornecedor.get(forn);
                                try {estado.adicionaFornecedor(forns);}
                                catch (EstadoException e) {System.out.print(e + "\n");}
                            }
                        }
                        catch(FileNotFoundException | FornecedorException e) {System.out.print(e + "\n");}
     
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