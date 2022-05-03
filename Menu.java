import java.util.AbstractMap;
import java.util.Scanner;

import ErrorHandling.CasaInteligenteException;
import ErrorHandling.ResolutionException;
import ErrorHandling.SmartBulbException;
import ErrorHandling.SmartCameraException;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

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
        System.out.println("Pressione qualquer tecla para continuar...");
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
        sb.append("1) Criar Casa.\n");
        sb.append("2) Criar Novo Dispositivo.\n");
        sb.append("3) Carregar ficheiro.\n");
        sb.append("0) Retroceder.\n\n");
        sb.append("Selecione a opção pretendida: ");
        System.out.println(sb.toString());
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static CasaInteligente MenuCriarCasa() {
        clearWindow();
        
        CasaInteligente smarthouse = new CasaInteligente();
        
        
        StringBuilder sb = new StringBuilder("-----------Menu Criar Casa -----------\n\n");
        System.out.println(sb.toString());                 
        
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
        
        
        return smarthouse;



    }


    public static int MenuCriarDivisão() {
        clearWindow();
        StringBuilder sb = new StringBuilder("-----------Menu Criar Divisões -----------\n\n");
        sb.append("1) Criar Nova Divisão.\n");
        sb.append("0) Retroceder.\n\n");
        sb.append("Selecione a opção pretendida: ");
        System.out.println(sb.toString());
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
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
        sb.append("Introduza a opção pretendida: ");

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
        SmartBulb smartbulb = new SmartBulb();
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("------------Menu SmartBulb---------").append("\n\n");
        sb.append("Opções de tonalidade: \n");
        sb.append("0) Cold\n");
        sb.append("1) Neutral\n");
        sb.append("2) Warm\n");
        sb.append("Escolha uma opção: ");

        System.out.println(sb.toString());

        Scanner scanner = new Scanner(System.in);
        int tone = scanner.nextInt();
        smartbulb.setTone(tone);

        boolean i;
        do {
            System.out.print("Defina a dimensão (float): ");
            float dimensao = scanner.nextFloat();
        
            try { smartbulb.setDimensao(dimensao); i=false;}
            catch (SmartBulbException e) {System.out.print(e + "\n"); i = true;}
        } while(i); 
        

        do {
            System.out.print("Defina o valor fixo (inteiro): ");
            int valor_fixo = scanner.nextInt();
            
            try { smartbulb.setValorFixo(valor_fixo); i=false;}
            catch (SmartBulbException e) {System.out.print(e + "\n"); i = true;}
        } while(i); 
        
        return smartbulb;

    } 

    public static SmartCamera menuSmartCamera() {
        
        SmartCamera smartcamera = new SmartCamera();
        Resolution res = new Resolution();
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("------------Menu Smart Camera---------").append("\n\n");
        sb.append("Defina a resolução da camera.");
        System.out.println(sb.toString());
        
        Scanner scanner = new Scanner(System.in);
        boolean i;

        do {
            System.out.print("Defina a largura (width): ");
            float width = scanner.nextFloat();
            res.setWidth(width);

            System.out.print("Defina a altura (height): ");
            float height = scanner.nextFloat();
            res.setHeight(height);

            try {smartcamera.setResolution(res); i=false;}
            catch (ResolutionException e) {System.out.print(e + "\n"); i = true;}
        } while(i); 


        do {
            System.out.print("Defina o tamanho do ficheiro (float): ");
            float file_size = scanner.nextFloat();
    
            try { smartcamera.setFileSize(file_size); i=false;}
            catch (SmartCameraException e) {System.out.print(e + "\n"); i = true;}
        } while(i);
        
        do {
            System.out.print("Defina a compressão do ficheiro (float): ");
            float compression = scanner.nextFloat();
    
            try { smartcamera.setCompression(compression);; i=false;}
            catch (SmartCameraException e) {System.out.print(e + "\n"); i = true;}
        } while(i);
             
        return smartcamera;
    }
    
    public static SmartSpeaker menuSmartSpeaker() {
        SmartSpeaker smartspeaker = new SmartSpeaker();
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("------------Menu Smart Speaker---------").append("\n\n");
        System.out.println(sb.toString());

        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Defina o volume do Speaker: ");
        int volume = scanner.nextInt();
        smartspeaker.setVolume(volume);

        System.out.print("Defina a rádio online que está a tocar: ");
        String channel = scanner.nextLine();
        smartspeaker.setChannel(channel);

        System.out.print("Defina a marca do Speaker: ");
        String brand = scanner.nextLine();
        smartspeaker.setBrand(brand);

        return smartspeaker;
        //bugado
    }
    
}