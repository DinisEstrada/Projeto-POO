import java.util.*;
import java.text.ParseException;
import ErrorHandling.*;

import java.io.*;

public class ControllerCriarDados {
        public static void run(Estado estado) {
            
            boolean exit = false;
            //boolean errorMessage = false;
            while(!exit){
                int opcao = -1;
                while(opcao < 0 || opcao > 7) {
                    opcao = Menu.MenuCriarEstado();
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
                            Fornecedor forn = Menu.escolherFornecedor(estado, casa);
                            casa.setFornecedor(forn);
                            
                            try {estado.adicionaCasa(casa);}
                            catch(EstadoException e) {System.out.println(e + "\n");}
                        }
                        break;

                    case 2: 

                    if (!estado.getCasas().isEmpty()) {
                        String casa_teste = Menu.menuDispositivo(estado);

                        if(estado.getCasas().containsKey(casa_teste)) {
                            CasaInteligente casa = estado.getCasas().get(casa_teste);
                            String room = Menu.menuDispositivo2(casa);
                            
                            if(casa.getLocations().containsKey(room)) {
                                DeviceType devicetype = null;

                                while(devicetype == null) devicetype = Menu.menuDispositivo3();
                            
                                if(devicetype.equals(DeviceType.SmartBulb)) {
                                        SmartBulb bulb = Menu.menuSmartBulb();
                                        try {casa.addDevice(bulb,room);}
                                        catch (CasaInteligenteException e) {System.out.print(e + "\n");}
                                }
                                else if (devicetype.equals(DeviceType.SmartCamera)) {
                                    SmartCamera camera = Menu.menuSmartCamera(); 
                                    try {casa.addDevice(camera,room);}
                                    catch (CasaInteligenteException e) {System.out.print(e + "\n");}
                                }
                                else {
                                    SmartSpeaker speaker = Menu.menuSmartSpeaker();
                                    try {casa.addDevice(speaker,room);}
                                    catch (CasaInteligenteException e) {System.out.print(e + "\n");}
                                }
                            }
                            else {
                                System.out.println("\nA divisão é inválida");
                                Menu.pressEnter();
                                Menu.clearWindow();
                            }
                        }

                        else {
                            System.out.println("\nO ID é inválido");
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
                        Menu.clearWindow();
                        System.out.print("-----------Menu Criar Fornecedor -----------\n\n");

                        Fornecedor forn = null;
                
                        Random rand = new Random();
                            int upperbound = 3;
                            int int_rand = rand.nextInt(upperbound);
                
                            switch(int_rand) {
                                case 1:
                                    forn = new FornecedorB();
                                    break;
                
                                case 2:
                                    forn = new FornecedorC();
                                    break;
                
                                case 0:
                                    forn = new FornecedorA();
                                    break;
                            }

                        Menu.menuCriaAlteraFornecedor(estado, forn, true);
                        
                        try{estado.adicionaFornecedor(forn);}
                        catch(EstadoException e) {System.out.println(e + "\n");}
                        
                        break;
                    
                    case 4:
                        Scanner scanner = new Scanner(System.in);
                        System.out.println("Introduza o nome do ficheiro csv que pretende carregar: ");
                        String filename = scanner.nextLine();

                        Parser file = new Parser(filename);
                        HashMap<String,CasaInteligente> house;
                        HashMap<String,Fornecedor> fornecedor;
                        
                        try {
                            house = file.housesConfig(); 
                            try {estado.setCasas(house);}
                            catch (EstadoException e) {System.out.print(e + "\n");}
                        }
                        catch(CasaInteligenteException | SmartDeviceException | FornecedorException | SmartBulbException | 
                            ResolutionException | SmartCameraException | 
                            SmartSpeakerException | FileNotFoundException e) {System.out.print(e + "\n");}

                        try {
                            fornecedor = file.energyConfig(); 
                            try {estado.setFornecedores(fornecedor);}
                            catch (EstadoException e) {System.out.print(e + "\n");}
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