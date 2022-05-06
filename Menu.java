import java.util.AbstractMap;
import java.util.Scanner;

import ErrorHandling.*;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDateTime;
import java.util.Random;

public class Menu {
    public static int MenuInicial() {
        clearWindow();
        StringBuilder sb = new StringBuilder("-----------MENU INICIAL-----------\n\n");
        sb.append("1) Simulação.\n");
        sb.append("2) Carregar Estado.\n");
        sb.append("3) Guardar Estado.\n");
        sb.append("0) Sair.\n\n");
        sb.append("Selecione a opção pretendida: ");
        System.out.println(sb.toString());
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
    
    public static String pressEnter(){
        System.out.println("Pressione enter para continuar...");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    //double to TimeString
    public static String time(double d){
        int hour = (int) d;
        int min = (int)((d-hour)*60);
        return (hour+":"+ min+" H");
    }
    
    //Tentar encontrar outra forma mais elegante
    public static void clearWindow() {
        
        for (int i = 0;i<100;i++){
            System.out.println();
        }
    }


    public static int MenuCarregarEstado() {
        clearWindow();
        StringBuilder sb = new StringBuilder("-----------Menu Carregar Estado -----------\n\n");
        sb.append("1) Criar nova Casa.\n");
        sb.append("2) Criar novo Dispositivo.\n");
        sb.append("3) Criar novo Fornecedor.\n");
        sb.append("4) Carregar logs.\n");
        sb.append("0) Retroceder.\n\n");
        sb.append("Selecione a opção pretendida: ");
        System.out.println(sb.toString());
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static CasaInteligente MenuCriarCasa(Estado estado) {
        clearWindow();
        
        CasaInteligente smarthouse = new CasaInteligente();
        

        System.out.print("-----------Menu Criar Casa -----------\n\n");
                
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Defina o proprietário da casa: ");
        String owner = scanner.nextLine();
        smarthouse.setOwner(owner);

        boolean i;
        do {
            System.out.print("Defina o NIF do proprietário: ");
            int nif = scanner.nextInt();
        
            try { smarthouse.setNif(nif); i=false;}
            catch (CasaInteligenteException e) {System.out.print("e + \n"); i = true;}
        } while(i);
        
        boolean continuar = true;    

        while(continuar) {
            String room = scanner.nextLine();
            System.out.println("Escolha a divisão que pretende criar: ");
            room = scanner.nextLine();

            do {
                try {smarthouse.addRoom(room); i = false;}
                catch (CasaInteligenteException e) {System.out.print("e + \n"); i = true;}
            } while(i);
            
            System.out.println("Pretende criar mais divisões?");
            System.out.println("1) Sim");
            System.out.println("2) Não");
            int opcao = scanner.nextInt();

            if(opcao == 2) continuar = false;
        }
            return smarthouse;
    }

    public static void escolherFornecedor(Estado estado, CasaInteligente smarthouse) {
        
        System.out.print("Lista de Fornecedores: \n");      
 
        Scanner scanner = new Scanner(System.in);

        HashMap<String,Fornecedor> l = estado.getFornecedores();

        for(String name: l.keySet()) {
            String key = name.toString();
            System.out.println(" - " + key);
        }

        System.out.println("Escreva o nome do Fornecedor pretendido: ");
        String option = scanner.nextLine();
            
        smarthouse.setFornecedor(l.get(option));   
    
    }
    
    public static void menuCriaFornecedor(Estado estado) {
        clearWindow();
        
        System.out.print("-----------Menu Criar Fornecedor -----------\n\n");

        Fornecedor forn = null;
        boolean i;
        Scanner scanner = new Scanner(System.in);

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
            do {
                do {
                    System.out.print("Introduza o nome do Fornecedor: ");
                    String name = scanner.nextLine();
        
                    try { forn.setName(name); i=false;}
                    catch (FornecedorException e) {System.out.print(e + "\n"); i = true;}
                }   while(i); 

                do {
                    System.out.print("Defina o valor base: ");
                    Float valor_base = scanner.nextFloat();
        
                    try { forn.setValor_base(valor_base); i=false;}
                    catch (FornecedorException e) {System.out.print(e + "\n"); i = true;}
                } while(i);
        
        
                do {
                    System.out.print("Defina o imposto: ");
                    Float imposto = scanner.nextFloat();
        
                    try { forn.setImposto(imposto); i=false;}
                    catch (FornecedorException e) {System.out.print(e + "\n"); i = true;}
                } while(i);

                do {
                    System.out.print("Defina o desconto: ");
                    Float desconto = scanner.nextFloat();
        
                    try { forn.setDesconto(desconto); i=false;}
                    catch (FornecedorException e) {System.out.print(e + "\n"); i = true;}
                } while(i);

                try {estado.adicionaFornecedor(forn); i=false;}
                catch (EstadoException e) {System.out.print(e + "\n"); i = true;}
            } while(i);
    }


    public static AbstractMap.SimpleEntry<String,String> menuDispositivo() {
        clearWindow();
        StringBuilder sb = new StringBuilder();
        sb.append("------------Menu Dispositivo---------").append("\n\n");
        sb.append("Introduza o nome do proprietário da casa a que quer adicionar o dispositivo: ");

        System.out.print(sb.toString());

        Scanner scanner = new Scanner(System.in);
        String owner = scanner.nextLine();

        System.out.print("Escolha a respetiva divisão: ");
        String divisao = scanner.nextLine();
        
        return new AbstractMap.SimpleEntry<>(owner, divisao);
    }


    public static DeviceType menuDispositivo2() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("Selecione o Dispositivo que pretende criar.\n\n");
        sb.append("1) SmartBulb\n");
        sb.append("2) SmartCamera\n");
        sb.append("3) SmartSpeaker\n");
        sb.append("\nIntroduza a opção pretendida: ");

        System.out.println(sb.toString());

        Scanner scanner = new Scanner(System.in);
        int opção = scanner.nextInt();

        switch(opção) {
            case 1: return DeviceType.SmartBulb;
            case 2: return DeviceType.SmartCamera;
            case 3: return DeviceType.SmartSpeaker;
            default: return null; 
        }
    }

    public static SmartBulb menuSmartBulb() {
        SmartBulb smartbulb = null;
        
        Random num= new Random();
        StringBuilder nomesb = new StringBuilder();
        int rand_num = num.nextInt(999999);
        nomesb.append("smtblb-").append(rand_num);
        String nome= nomesb.toString();
        smartbulb.setId(nome);

        StringBuilder sb = new StringBuilder();
        
        System.out.print("------------Menu SmartBulb---------\n\n");
        
        boolean i;
        do {
        sb.append("Opções de tonalidade: \n");
        sb.append("0) Cold\n");
        sb.append("1) Neutral\n");
        sb.append("2) Warm\n");
        sb.append("Escolha uma opção: ");

        System.out.println(sb.toString());

        Scanner scanner = new Scanner(System.in);
        int tone = scanner.nextInt();

        
        System.out.print("Defina a dimensão (float): ");
        float dimensao = scanner.nextFloat();
 
        System.out.print("Defina o valor fixo (inteiro): ");
        int valor_fixo = scanner.nextInt();
  
        System.out.print("Pretende deixar o dispositivo ligado?\n");
        System.out.print("1) Sim\n");
        System.out.print("2) Não\n");
        int opcao = scanner.nextInt();
        boolean bool = false;
        if (opcao == 1) bool = true;

        System.out.print("Defina o custo de instalação: ");
        Float cust_inst = scanner.nextFloat();

        try {smartbulb = new SmartBulb(nome, bool, cust_inst, tone, dimensao, valor_fixo); i=false;}
        catch (SmartBulbException e) {System.out.print(e + "\n"); i=true;}
        catch (SmartDeviceException e) {System.out.print(e + "\n"); i=true;}
        } while(i);   
        
        return smartbulb;

    } 

    public static SmartCamera menuSmartCamera() {
        
        SmartCamera smartcamera = null;
        Resolution res = new Resolution();
        
        Random num= new Random();
        StringBuilder nomesb = new StringBuilder();
        int rand_num = num.nextInt(999999);
        nomesb.append("smtcam-").append(rand_num);
        String nome= nomesb.toString();
        smartcamera.setId(nome);

        StringBuilder sb = new StringBuilder();
        
        System.out.println("------------Menu Smart Camera---------\n\n");
        
        Scanner scanner = new Scanner(System.in);
        boolean i;

        do {
        System.out.println("Defina a resolução da camera.");
        System.out.print("Defina a largura (width): ");
        float width = scanner.nextFloat();
        res.setWidth(width);

        System.out.print("Defina a altura (height): ");
        float height = scanner.nextFloat();
        res.setHeight(height);

        System.out.print("Defina o tamanho do ficheiro (float): ");
        float file_size = scanner.nextFloat();
         
        System.out.print("Pretende deixar o dispositivo ligado?\n");
        System.out.print("1) Sim\n");
        System.out.print("2) Não\n");
        int opcao = scanner.nextInt();
        boolean bool = false;
        if (opcao == 1) bool = true;

        System.out.print("Defina o custo de instalação: ");
        Float cust_inst = scanner.nextFloat();

        try {smartcamera = new SmartCamera(nome, bool, cust_inst, res, file_size); i=false;}
        catch (SmartCameraException e) {System.out.print(e + "\n"); i=true;}
        catch (SmartDeviceException e) {System.out.print(e + "\n"); i=true;}
        } while(i);   

        return smartcamera;
    }
    
    public static SmartSpeaker menuSmartSpeaker() {
        
        SmartSpeaker smartspeaker = null;

        Random num= new Random();
        StringBuilder nomesb = new StringBuilder();
        int rand_num = num.nextInt(999999);
        nomesb.append("smtspk-").append(rand_num);
        String nome= nomesb.toString();
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("------------Menu Smart Speaker---------\n\n");
        System.out.println(sb.toString());

        boolean i;
        do {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Defina o volume do Speaker: ");
        int volume = scanner.nextInt();
       

        System.out.print("Defina a rádio online que está a tocar: ");
        String channel = scanner.nextLine();
        
        String brand = scanner.nextLine();

        System.out.print("Defina a marca do Speaker: ");
        brand = scanner.nextLine();

        System.out.print("Defina o consumo da respetiva marca: ");
        Float brand_comsuption = scanner.nextFloat();

        System.out.print("Pretende deixar o dispositivo ligado?\n");
        System.out.print("1) Sim\n");
        System.out.print("2) Não\n");
        int opcao = scanner.nextInt();
        boolean bool = false;
        if (opcao == 1) bool = true;

        System.out.print("Defina o custo de instalação: ");
        Float cust_inst = scanner.nextFloat();

        try {smartspeaker = new SmartSpeaker(nome, bool, cust_inst, volume, channel, brand, brand_comsuption); i=false;}
        catch (SmartSpeakerException e) {System.out.print(e + "\n"); i=true;}
        catch (SmartDeviceException e) {System.out.print(e + "\n"); i=true;}
        } while(i);   
        
        return smartspeaker;
    }
    
}