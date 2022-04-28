import java.util.*;

/**
 * A CasaInteligente faz a gestão dos SmartDevices que existem e dos espaços (as salas) que existem na casa.
 *
 * @author josefonte
 * @version 0.1
 */
public class CasaInteligente {

    private String owner;
    private int nif;

    private Fornecedor fornecedor;
    private Map<String, SmartDevice> devices; // identificador -> SmartDevice
    private Map<String, List<String>> locations; // Espaço -> Lista codigo dos devices

    /**
     * Constructor for objects of class CasaInteligente
     */
    public CasaInteligente() {
        // initialise instance variables
        this.owner = "";
        this.nif = 0;
        this.fornecedor = null;
        this.devices = new HashMap<>();
        this.locations = new HashMap<>();
    }

    public CasaInteligente(String owner,int nif,Fornecedor fornecedor, Map<String,SmartDevice> ndevices, Map<String, List<String>> nlocations ) {
        // initialise instance variables
        this.owner = owner;
        this.nif = nif;
        this.fornecedor = fornecedor.clone();
        this.devices = new HashMap<>();
        for (Map.Entry<String,SmartDevice> e  : ndevices.entrySet()){
            this.devices.put(e.getKey(),e.getValue().clone());
        }

        this.locations = new HashMap<>();
        for (Map.Entry<String,List<String>> e  : nlocations.entrySet()){
            List<String> ids = new ArrayList<>(e.getValue());
            this.locations.put(e.getKey(),ids);
        }
    }

    public CasaInteligente(String owner,int nif, Fornecedor fornecedor){
        this.owner = owner;
        this.nif = nif;
        this.fornecedor = fornecedor.clone();
        this.devices = new HashMap<>();
        this.locations = new HashMap<>();
    }

    public CasaInteligente(CasaInteligente casa){
        this.owner = casa.getOwner();
        this.nif = casa.getNif();
        this.fornecedor = casa.getFornecedor();
        this.devices = casa.getDevices();
        this.locations = casa.getLocations();

    }

    public String getOwner() {return this.owner;}

    public int getNif() {return this.nif;}

    public Fornecedor getFornecedor() {return this.fornecedor.clone();}

    public Map<String, SmartDevice> getDevices() {
        Map<String, SmartDevice> devs = new HashMap<>();
        for (Map.Entry<String,SmartDevice> e  : this.devices.entrySet()){
            devs.put(e.getKey(),e.getValue().clone());
        }
        return devs;
    }

    public Map<String, List<String>> getLocations() {
        Map<String, List<String>> locs = new HashMap<>();
        for (Map.Entry<String,List<String>> e  : this.locations.entrySet()){
            List<String> ids = new ArrayList<>(e.getValue());
            locs.put(e.getKey(),ids);
        }
        return locs;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public void setDevices(Map<String, SmartDevice> devs){
        this.devices = new HashMap<>();
        for(SmartDevice smt : devs.values()){
            this.devices.put(smt.getID(),smt.clone());
        }
    }

    public void setLocations(Map<String, List<String>> locs){
        this.locations = new HashMap<>();
        for (Map.Entry<String,List<String>> e  : locs.entrySet()){
            List<String> ids = new ArrayList<>(e.getValue());
            this.locations.put(e.getKey(),ids);
        }
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CasaInteligente)) return false;
        CasaInteligente casa = (CasaInteligente) o;
        return ( this.owner.equals(casa.getOwner()) &&
                this.nif == casa.getNif() &&
                this.devices.equals(casa.getDevices()) &&
                this.locations.equals(casa.getLocations()) &&
                this.fornecedor.equals(casa.getFornecedor()));
    }

    public String toString(){
        return  "\n### House ###" +
                "\nOwner: " + this.owner +
                "\nNIF: " + this.nif +
                "\nFornecedor: " + this.fornecedor +
                "\nDevices " + this.devices.toString() +
                "\nRooms: " + this.locations.toString();
    }
    public CasaInteligente clone(){
        return new CasaInteligente(this);
    }

    //---------------------------------------------------------------------------------


    public void addDevice(SmartDevice s, String room) {
        this.devices.put(s.getID(),s.clone());
        if(this.locations.containsKey(room)) this.locations.get(room).add(s.getID());
        else {
            addRoom(room);
            this.locations.get(room).add(s.getID());
        }
    }

    public void removeDevice(SmartDevice s) {
        this.devices.remove(s.getID());
        for(List<String> devs_ids : this.locations.values()){
            devs_ids.remove(s.getID());
        }
    }

    public void turnDeviceOn(String id) {
        this.devices.get(id).turnOn();
    }

    public void turnDeviceOFF(String id) {this.devices.get(id).turnOff();}

    public boolean existsDevice(String id) {return this.devices.containsKey(id);}

    public SmartDevice getDevice(String id) {return this.devices.get(id).clone();}



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
        List<String> ids_cp = new ArrayList<>(ids);
        this.locations.put(room,ids_cp);
    }

    public boolean hasRoom(String room) {return this.locations.containsKey(room);}

    public boolean roomHasDevice (String room, String id) {return this.locations.get(room).contains(id);}


    public double consumoRoom(String room){
        return this.locations.get(room).stream()
                .mapToDouble(id-> this.fornecedor.formulaPreco(this.devices.get(id), this))
                .sum();

    }

    public double consumoCasa(){
        return this.devices.values().stream().filter(SmartDevice::getOn).mapToDouble(a -> this.fornecedor.formulaPreco(a,this)).sum();
    }


    public int numberDevices(){
       return this.devices.keySet().size();
    }

    public Set<String> rooms(){
        return this.locations.keySet();
    }

    public List<SmartDevice> getDevicesinRoom(String room){
        List<SmartDevice> devs = new ArrayList<>();

        for (String id : this.locations.get(room)){
            devs.add( this.devices.get(id).clone());
        }
        return devs;
    }

    public List<Boolean> getRoomState(String room){

        List<Boolean> lista = new ArrayList<>();

        for (String id : this.locations.get(room)) {
            lista.add(this.devices.get(id).getOn());
        }
        return lista;
    }

}
