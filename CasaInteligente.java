
import java.util.Map;
import java.util.List;
import java.util.HashMap;


/**
 * A CasaInteligente faz a gestão dos SmartDevices que existem e dos 
 * espaços (as salas) que existem na casa.
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
        Map<String, SmartDevice> ndevices = new HashMap<>(this.devices);

        return ndevices;
    }

    public Map<String, List<String>> getLocations() {
        return null;
    }

    public void setDeviceOn(String devCode) {
        this.devices.get(devCode).turnOn();
    }
    public void setDeviceOFF(String devCode) {this.devices.get(devCode).turnOff();}
    
    public boolean existsDevice(String id) {return this.devices.containsKey(id);}
    
    public void addDevice(SmartDevice s) {this.devices.put(s.getID(),s);}
    public void removeDevice(SmartDevice s) {this.devices.remove(s.getID());}
    
    public SmartDevice getDevice(String id) {return this.devices.get(id);}
    
    public void setRoomOn(String room, boolean b) {


    }
    
    public void setAllOn(boolean b) {}
    
    public void addRoom(String s) {}
    
    public boolean hasRoom(String s) {return false;}
    
    public void addToRoom (String s1, String s2) {}
    
    public boolean roomHasDevice (String s1, String s2) {return false;}


    //estatisticas
    
}
