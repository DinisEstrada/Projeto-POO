import ErrorHandling.CasaInteligenteException;
import ErrorHandling.EstadoException;
import ErrorHandling.FaturaException;
import ErrorHandling.FornecedorException;

import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Estado implements Serializable {

    private HashMap<String,CasaInteligente> casas;
    private HashMap<String,Fornecedor> fornecedores;

    public Estado(){
        this.casas = new HashMap<>();
        this.fornecedores = new HashMap<>();
    }

    public Estado(HashMap<String,CasaInteligente> housesConfig, HashMap<String,Fornecedor> fornecedores){
        this.casas = new HashMap<>();
        for(Map.Entry<String,CasaInteligente> ent : housesConfig.entrySet()){
            this.casas.put(ent.getKey(),ent.getValue().clone());
        }

        this.fornecedores = new HashMap<>();
        for(Map.Entry<String,Fornecedor> ent : fornecedores.entrySet()){
            this.fornecedores.put(ent.getKey(),ent.getValue().clone());
        }
    }

    public Estado(Estado est){
        this.casas = est.getCasas();
        this.fornecedores = est.getFornecedores();
    }


    public HashMap<String, CasaInteligente> getCasas() {
        HashMap<String,CasaInteligente> casas = new HashMap<>();
        for(Map.Entry<String,CasaInteligente> ent : this.casas.entrySet()){
            casas.put(ent.getKey(),ent.getValue().clone());
        }
        return casas;
    }

    public HashMap<String, Fornecedor> getFornecedores() {
        HashMap<String,Fornecedor> forns = new HashMap<>();
        for(Map.Entry<String,Fornecedor> ent : this.fornecedores.entrySet()){
            forns.put(ent.getKey(),ent.getValue().clone());
        }
        return forns;
    }

    public void setFornecedores(HashMap<String, Fornecedor> fornecedores) {
        this.fornecedores = new HashMap<>();
        for(Map.Entry<String,Fornecedor> ent : fornecedores.entrySet()){
            this.fornecedores.put(ent.getKey(),ent.getValue().clone());
        }
    }

    public void setCasas(HashMap<String, CasaInteligente> casas) {
        this.casas = new HashMap<>();
        for(Map.Entry<String,CasaInteligente> ent : casas.entrySet()){
            this.casas.put(ent.getKey(),ent.getValue().clone());
        }
    }

    public boolean equals(Object obj) {
        if(obj == this) return true;
        if( obj == null || obj.getClass()!=this.getClass()) return false;
        Estado stt = (Estado) obj;
        return this.casas.equals(stt.getCasas()) && this.fornecedores.equals(stt.getFornecedores());
     }

    public String toString(){
        return this.casas.entrySet() + this.casas.entrySet().toString();
    }

    public Estado clone(){
        return new Estado(this);
    }

    //---------------------------------------------------------------------


    public void adicionaCasa(CasaInteligente casa){
        this.casas.put(casa.getOwner(),casa);
    }
    public void removeCasa(CasaInteligente casa){
        this.casas.remove(casa.getOwner());
    }
    public void adicionaFornecedor(Fornecedor fornecedor) throws EstadoException {
        if(fornecedores.containsKey(fornecedor.getName())) throw new EstadoException("Fornecedor já existe");
        else this.fornecedores.put(fornecedor.getName(),fornecedor);
    }

    public void removeFornecedor(Fornecedor fornecedor){
        this.casas.remove(fornecedor.getName());
    }

    public void guardaEstado(String nomeFicheiro) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(nomeFicheiro);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
        fos.close();
    }

    public Estado carregaEstado(String nomeFicheiro) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fos = new FileInputStream(nomeFicheiro);
        ObjectInputStream oos = new ObjectInputStream(fos);
        Estado c = (Estado) oos.readObject();
        oos.close();
        fos.close();
        return c;
    }

    public HashMap<String,Fatura> geradorFaturas(String inicio, String fim){
        HashMap<String,Fatura> fats = new HashMap<>();

        this.casas.values().stream().forEach(casa -> {
            try {
                fats.put(casa.getOwner(), casa.faturaCasa(inicio,fim));
            } catch (FaturaException | ParseException e) {
                throw new RuntimeException(e);
            }
        });

        return fats;
    }

    /*Estatisticas
    - casa que gastou mais (done)
    - fornecedor com maior volume de faturação (done)
    - todas as faturas por fornecedor (done)
    - casas com maior consumo durante X tempo
*/

    public CasaInteligente casaMaisGastou(){
        TreeSet<CasaInteligente> set_casas = new TreeSet<>();

        this.casas.values().stream()
                        .forEach(a->set_casas.add(a.clone()));

        return set_casas.first();
    }

    public TreeSet<CasaInteligente> casasMaiorConsumo(int periodo){
        Comparator<CasaInteligente> comp = (o1, o2) -> {
            if (o1.consumoCasa()*periodo  < o2.consumoCasa()*periodo ) return 1;
            else if (o1.consumoCasa()*periodo > o2.consumoCasa()*periodo) return -1;
            else {
                return o1.getOwner().compareTo(o2.getOwner());
            }

        };
        TreeSet<CasaInteligente> set_casas = new TreeSet<>(comp);
        this.casas.values().stream()
                .forEach(a->set_casas.add(a.clone()));

        return set_casas;
    }

    public TreeSet<Fatura> faturasFornecedor(Fornecedor forn) {
        TreeSet<Fatura> lista = new TreeSet<>();

        this.casas.values().stream().filter(a-> a.getFornecedor().getName().equals(forn.getName())).forEach(a -> {
            try {
                lista.add(a.faturaCasa("19-10-2022","20-10-2022").clone());
            } catch (FaturaException | ParseException e) {
                throw new RuntimeException(e);
            }
        });
        return lista;
    }


    public Fornecedor fornecedorMaisFaturou() {
        Fornecedor maior = null;
        double consumomaior=0;
        double consumoforn;

        for(Fornecedor forn : this.fornecedores.values()){
            consumoforn =faturasFornecedor(forn).stream().mapToDouble(a-> {
                try {
                    return a.valor_fatura();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }).sum();
           if (consumomaior <  consumoforn) {
               consumomaior =  consumoforn;
               maior = forn ;}
        }
        return maior;
    }

    // MUDANÇAS
    //  - casa muda de fornecedor
    //  - ligar e desligar dispositivos
    //  - mudar valores praticados pelo fornecedor (valor_base,desconto,imposto)

    public void mudaFornecedor(CasaInteligente casa, String forn) throws EstadoException {
        if (!this.fornecedores.containsKey(forn)) throw new EstadoException("Fornecedor não listado");
        if (!this.casas.containsKey(casa.getOwner())) throw new EstadoException("Casa não existe");

        Fornecedor fornecedor = null;
        for(Fornecedor f : this.fornecedores.values()){
            if(f.getName().equals(forn)) fornecedor = f;
        }
        CasaInteligente casa_upd = this.casas.get(casa.getOwner()).clone();

        casa_upd.setFornecedor(fornecedor);

        this.casas.replace(casa_upd.getOwner(), casa_upd);

    }

    public void turnDeviceON(CasaInteligente casa, String id) throws EstadoException, CasaInteligenteException {
        if (!this.casas.containsKey(casa.getOwner())) throw new EstadoException("Casa não existe");

        CasaInteligente casa_upd = this.casas.get(casa.getOwner()).clone();
        casa_upd.turnDeviceOn(id);
        this.casas.replace(casa_upd.getOwner(), casa_upd);
    }

    public void turnDeviceOFF(CasaInteligente casa, String id) throws EstadoException, CasaInteligenteException {
        if (!this.casas.containsKey(casa.getOwner())) throw new EstadoException("Casa não existe");

        CasaInteligente casa_upd = this.casas.get(casa.getOwner()).clone();
        casa_upd.turnDeviceOFF(id);
        this.casas.replace(casa_upd.getOwner(), casa_upd);
    }

    public void turnALLON(CasaInteligente casa) throws EstadoException, CasaInteligenteException {
        if (!this.casas.containsKey(casa.getOwner())) throw new EstadoException("Casa não existe");

        CasaInteligente casa_upd = this.casas.get(casa.getOwner()).clone();
        casa_upd.turnAllOn();
        this.casas.replace(casa_upd.getOwner(), casa_upd);
    }

    public void turnALLOFF(CasaInteligente casa) throws EstadoException, CasaInteligenteException {
        if (!this.casas.containsKey(casa.getOwner())) throw new EstadoException("Casa não existe");

        CasaInteligente casa_upd = this.casas.get(casa.getOwner()).clone();
        casa_upd.turnAllOff();
        this.casas.replace(casa_upd.getOwner(), casa_upd);
    }

    public void turnRoomOFF(CasaInteligente casa, String room) throws EstadoException, CasaInteligenteException {
        if (!this.casas.containsKey(casa.getOwner())) throw new EstadoException("Casa não existe");

        CasaInteligente casa_upd = this.casas.get(casa.getOwner()).clone();
        casa_upd.turnRoomOff(room);
        this.casas.replace(casa_upd.getOwner(), casa_upd);
    }

    public void turnRoomOn(CasaInteligente casa, String room) throws EstadoException, CasaInteligenteException {
        if (!this.casas.containsKey(casa.getOwner())) throw new EstadoException("Casa não existe");

        CasaInteligente casa_upd = this.casas.get(casa.getOwner()).clone();
        casa_upd.turnRoomOn(room);
        this.casas.replace(casa_upd.getOwner(), casa_upd);
    }

/*Ainda nao esta certo
    public void mudaValoresForn(String fornecedor, float valor_energia, float imposto, float desconto) throws EstadoException, CasaInteligenteException, FornecedorException {
        if (!this.fornecedores.containsKey(fornecedor)) throw new EstadoException("Casa não existe");

        Fornecedor f_upd = this.fornecedores.get(fornecedor).clone();

        f_upd.setValor_base(valor_energia);
        f_upd.setImposto(imposto);
        f_upd.setDesconto(desconto);

        this.fornecedores.replace(f_upd.getName(), f_upd);

        for(CasaInteligente casa : this.casas.values()){
            if(casa.getFornecedor().getName().equals(fornecedor)) {
                casa.setFornecedor(f_upd);
                this.casas.replace(casa.getOwner(), casa);
            }
        }
    }
    */

}
