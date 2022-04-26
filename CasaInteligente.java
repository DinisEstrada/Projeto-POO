import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.HashMap;


/**
 * A CasaInteligente faz a gestão dos SmartDevices que existem e dos espaços (as salas) que existem na casa.
 *
 * @author josefonte
 * @version 0.1
 */
public class CasaInteligente {

    private String owner;
    private String nif;
    private Map<String, SmartDevice> devices; // identificador -> SmartDevice
    private Map<String, List<String>> locations; // Espaço -> Lista codigo dos devices

    /**
     * Constructor for objects of class CasaInteligente
     */
    public CasaInteligente() {
        // initialise instance variables
        this.owner = "";
        this.nif = "";
        this.devices = new HashMap<>();
        this.locations = new HashMap<>();
    }

    public CasaInteligente(String owner,String nif, Map<String,SmartDevice> ndevices, Map<String, List<String>> nlocations ) {
        // initialise instance variables
        this.owner = owner;
        this.nif = nif;
        this.devices = new HashMap<>(ndevices);
        /*for (Map.Entry<String,SmartDevice> e  : ndevices.entrySet()){
            this.devices.put(e.getKey(),e.getValue().clone());
        }*/

        this.locations = new HashMap<>(nlocations);
        /*for (Map.Entry<String,List<String>> e  : nlocations.entrySet()){
            List<String> ids = new ArrayList<String>(e.getValue());
            this.locations.put(e.getKey(),ids);
        }*/
    }

    public CasaInteligente(String owner,String nif,List<SmartDevice> smtdvs){
        this.owner = owner;
        this.nif = nif;
        this.devices = new HashMap<>();
        for(SmartDevice smt : smtdvs){
            this.devices.put(smt.getID(), smt);
        }
    }
    public CasaInteligente(CasaInteligente casa){
        this.owner = casa.getOwner();
        this.nif = casa.getNif();
        this.devices = casa.getDevices();
        this.locations = casa.getLocations();

    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getNif() {return this.nif;}

    public void setNif(String nif) {
        this.nif = nif;
    }

    public Map<String, SmartDevice> getDevices() {
        Map<String, SmartDevice> devices_copy = new HashMap<>(this.devices);
        return devices_copy;
    }

    public Map<String, List<String>> getLocations() {
        Map<String, List<String>> locations_copy = new HashMap<>(this.locations);
        return locations_copy;
    }

    
    public void setDeviceOn(String id) {
        this.devices.get(id).turnOn();
    }
    public void setDeviceOFF(String id) {this.devices.get(id).turnOff();}
    
    public boolean existsDevice(String id) {return this.devices.containsKey(id);}
    
    public void addDevice(SmartDevice s) {this.devices.put(s.getID(),s);}
    public void removeDevice(SmartDevice s) {this.devices.remove(s.getID());}
    
    public SmartDevice getDevice(String id) {return this.devices.get(id);}

    public void setRoomOn(String room, boolean b) {
        for (String id : this.locations.get(room)){
            this.devices.get(id).setOn(b);
        }
    }

    public void turnRoomOn(String room){
        setRoomOn(room,true);
    }

    public void turnRoomOff(String room){
        setRoomOn(room,false);
    }

    public void setAllOn(boolean b) {
        for(String room : this.locations.keySet())
            for (String id : this.locations.get(room))
                this.devices.get(id).setOn(b);
    }

    public void turnAllOn(){
        setAllOn(true);
    }

    public void turnAllOff(){
        setAllOn(false);
    }


    public void addRoom(String s) {
        List<String> ids = new ArrayList<>();
        this.locations.put(s,ids);
    }
    
    public void addRoom(String room, List<String> ids){
        this.locations.put(room,ids);
    }
    
    public boolean hasRoom(String room) {return this.locations.containsKey(room);}
    
    public void addDeviceToRoom (String room, String id) {this.locations.get(room).add(id);}
    public void removeDeviceToRoom (String room, String id) {this.locations.get(room).remove(id);}
    
    public boolean roomHasDevice (String room, String id) {return this.locations.get(room).contains(id);}


    public float consumoRoom(String room){
        float consumo = 0;
        for (String id : this.locations.get(room)){
            if (this.devices.get(id).getOn()) consumo += this.devices.get(id).getConsumo();
        }
        return consumo;
    }
    
    public float consumoCasa(){
        float consumo = 0;
        for(String room : this.locations.keySet()){
            consumo += consumoRoom(room);
        }
        return consumo;
    }
    
}
