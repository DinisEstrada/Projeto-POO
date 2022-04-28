
import java.util.*;

public class testes {

    public static void main(String[] args) {

        /*SmartSpeaker smtspk1 = new SmartSpeaker("SMTSPK-01", true, 1.3f, 10, "RFM", "JBL");
        SmartSpeaker smtspk2 = new SmartSpeaker("SMTSPK-02", true, 1.3f, 10, "RFM", "JBL");
        SmartSpeaker smtspk3 = new SmartSpeaker("SMTSPK-03", true, 1.3f, 10, "RFM", "JBL");

        SmartBulb smtbulb1 = new SmartBulb("Smtbulb-01", true, 1.3f, SmartBulb.NEUTRAL, 3, 1.5f);
        SmartBulb smtbulb2 = new SmartBulb("Smtbulb-02", true, 1.3f, SmartBulb.NEUTRAL, 3, 1.5f);
        SmartBulb smtbulb3 = new SmartBulb("Smtbulb-03", true, 1.3f, SmartBulb.NEUTRAL, 3, 1.5f);

        SmartCamera smtcmr1 = new SmartCamera("Smtcmr-01", true, 1.3f, 1080, 40);
        SmartCamera smtcmr2 = new SmartCamera("Smtcmr-02", true, 1.3f, 1080, 40);
        SmartCamera smtcmr3 = new SmartCamera("Smtcmr-03", true, 1.3f, 1080, 40);

        HashMap<String,SmartDevice> devices = new HashMap<>();

        devices.put(smtspk1.getID(),smtspk1);
        devices.put(smtspk2.getID(),smtspk2);
        devices.put(smtspk3.getID(),smtspk3);
        devices.put(smtbulb1.getID(),smtbulb1);
        devices.put(smtbulb2.getID(),smtbulb2);
        devices.put(smtbulb3.getID(),smtbulb3);
        devices.put(smtcmr1.getID(),smtcmr1);
        devices.put(smtcmr2.getID(),smtcmr2);
        devices.put(smtcmr3.getID(),smtcmr3);

        List<String> ids_quarto = new ArrayList<>();
        List<String> ids_cozinha = new ArrayList<>();
        List<String> ids_sala = new ArrayList<>();

        ids_quarto.add(smtspk1.getID());
        ids_quarto.add(smtspk2.getID());
        ids_quarto.add(smtspk3.getID());

        ids_cozinha.add(smtbulb1.getID());
        ids_cozinha.add(smtbulb2.getID());
        ids_cozinha.add(smtbulb3.getID());

        ids_sala.add(smtcmr1.getID());
        ids_sala.add(smtcmr2.getID());
        ids_sala.add(smtcmr3.getID());

        Map<String,List<String>> locations = new HashMap<>();

        locations.put("quarto",ids_quarto);
        locations.put("sala",ids_sala);
        locations.put("cozinha",ids_cozinha);

        /*
        Map<String,List<String>> locations_copy = new HashMap<>();
        for (Map.Entry<String,List<String>> e  : locations.entrySet()){
            List<String> ids = new ArrayList<String>(e.getValue());
            locations_copy.put(e.getKey(),ids);
        }*/


       /* for (Map.Entry<String,List<String>> e  : locations.entrySet()){
            System.out.println(e.getValue());
        }*/

       //System.out.println(devices);
        /*
        List<String> devs_ids = locations.get("sala");

        System.out.println(devs_ids);

        for (String id : devs_ids){
            //devices.get(id).turnOff();
            System.out.println(devices.get(id));
        }

        */
        /*
        Interpreter it = new Interpreter("config.txt");

        HashMap<String,CasaInteligente> config = it.creator();
        it.getCasaInteligente("casa1").turnAllOn();
        System.out.println(it.getCasaInteligente("casa1").getDevices());


        SmartSpeaker smtspk1 = new SmartSpeaker("SMTSPK-01", true, 1.3f, 10, "MEGAHITS", "JBL");
        SmartSpeaker smtspk2 = new SmartSpeaker("SMTSPK-02", false, 1.3f, 10, "RFM", "Sony");
        SmartSpeaker smtspk3 = new SmartSpeaker("SMTSPK-03", true, 1.3f, 10, "ANTENA3", "Wonderboom");


        HashMap<String, SmartDevice> og = new HashMap<>();

        og.put(smtspk1.getID(), smtspk1);
        og.put(smtspk2.getID(), smtspk2);
        og.put(smtspk3.getID(), smtspk3);

        //System.out.println(og.values());

        HashMap<String, SmartDevice> copy = new HashMap<>(og);

        List<String> lista_ids1 = new ArrayList<>();
        List<String> lista_ids2 = new ArrayList<>();

        lista_ids1.add(smtspk1.getID());
        lista_ids1.add(smtspk2.getID());

        lista_ids2.add(smtspk3.getID());


        HashMap<String, List<String>> rooms = new HashMap<>();
        HashMap<String, List<String>> rooms2 = new HashMap<>();

        rooms.put("UM",lista_ids1);
        rooms.put("DOIS",lista_ids2);

        for (Map.Entry<String,List<String>> e  : rooms.entrySet()){
            List<String> ids = new ArrayList<>(e.getValue());
            /*for(String id : e.getValue()) {
                ids.add(id);
            }
            rooms2.put(e.getKey(),ids);
        }

        System.out.println(rooms);
        System.out.println(rooms2);

        rooms.replace("DOIS", lista_ids1);

        System.out.println(rooms);
        System.out.println(rooms2);
*/
        Parser it = new Parser("logs.csv");

        HashMap<String,CasaInteligente> housesConfig = it.housesConfig();
        HashMap<String,Fornecedor> energyConfig = it.energyConfig();

        CasaInteligente casa = housesConfig.get("casa1");

        System.out.println(housesConfig.get("casa0"));

    }
}
