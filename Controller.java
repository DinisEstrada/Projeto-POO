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
                    if(!estado.getCasas().isEmpty() && !estado.getFornecedores().isEmpty()) ControllerSimulacao.run(estado);
                    else {
                        System.out.println("Estado inválido, por favor carregue um novo estado");
                        Menu.pressEnter();
                        Menu.clearWindow();
                    }          
                    break;
                    
                case 2:
                    ControllerCarregarDados.run(estado);
                    break;
                    
                case 3:
                    try{estado = estado.carregaEstado();System.out.println("Ficheiros carregados com sucesso!!!\n");}
                    catch (FileNotFoundException e) {System.out.println("Ficheiro não encontrado");}
                    catch (IOException e) {System.out.println("Não foi possivel carregar o Estado");}
                    catch(ClassNotFoundException e) {System.out.println("Erro ao ler para as estruturas de dados");}
                    Menu.pressEnter();
                    break;
                
                case 4:
                    try{estado.guardaEstado();System.out.println("Ficheiros salvos com sucesso!!!\n");}
                    catch (FileNotFoundException e) {System.out.println("Ficheiro não encontrado");}
                    catch (IOException e) {System.out.println("Não foi possivel guardar o Estado");}
                    Menu.pressEnter();
                    break;

                case 0:
                    System.exit(0);
                    break;
                    
            }
        }
    }
}