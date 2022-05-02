import ErrorHandling.FaturaException;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Estado implements Serializable {

    private HashMap<String,CasaInteligente> housesConfig;
    private HashMap<String,Fornecedor> fornecedores;

    public Estado(){
        this.housesConfig = new HashMap<>();
        this.fornecedores = new HashMap<>();
    }

    public Estado(HashMap<String,CasaInteligente> housesConfig, HashMap<String,Fornecedor> fornecedores){
        this.housesConfig = new HashMap<>();
        for(Map.Entry<String,CasaInteligente> ent : housesConfig.entrySet()){
            this.housesConfig.put(ent.getKey(),ent.getValue().clone());
        }

        this.fornecedores = new HashMap<>();
        for(Map.Entry<String,Fornecedor> ent : fornecedores.entrySet()){
            this.fornecedores.put(ent.getKey(),ent.getValue().clone());
        }
    }

    public Estado(Estado est){
        this.housesConfig = est.getHousesConfig();
        this.fornecedores = est.getFornecedores();
    }


    public HashMap<String, CasaInteligente> getHousesConfig() {
        HashMap<String,CasaInteligente> casas = new HashMap<>();
        for(Map.Entry<String,CasaInteligente> ent : this.housesConfig.entrySet()){
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

    public void setHousesConfig(HashMap<String, CasaInteligente> housesConfig) {
        this.housesConfig = new HashMap<>();
        for(Map.Entry<String,CasaInteligente> ent : housesConfig.entrySet()){
            this.housesConfig.put(ent.getKey(),ent.getValue().clone());
        }
    }

    public boolean equals(Object obj) {
        if(obj == this) return true;
        if( obj == null || obj.getClass()!=this.getClass()) return false;
        Estado stt = (Estado) obj;
        return this.housesConfig.equals(stt.getHousesConfig()) && this.fornecedores.equals(stt.getFornecedores());
     }

    public String toString(){
        return this.housesConfig.entrySet() + this.housesConfig.entrySet().toString();
    }

    public Estado clone(){
        return new Estado(this);
    }

    //---------------------------------------------------------------------


    public void adicionaCasa(CasaInteligente casa){
        this.housesConfig.put(casa.getOwner(),casa);
    }
    public void removeCasa(CasaInteligente casa){
        this.housesConfig.remove(casa.getOwner());
    }
    public void adicionaFornecedor(Fornecedor fornecedor){
        if(!fornecedores.containsKey(fornecedor.getName()))
            this.fornecedores.put(fornecedor.getName(),fornecedor);
    }

    public void removeFornecedor(Fornecedor fornecedor){
        this.housesConfig.remove(fornecedor.getName());
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

    public HashMap<String,Fatura> geradorFaturas(int periodo){
        HashMap<String,Fatura> fats = new HashMap<>();

        this.housesConfig.values().stream().forEach(casa -> {
            try {
                fats.put(casa.getOwner(), casa.faturaCasa(periodo));
            } catch (FaturaException e) {
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

        this.housesConfig.values().stream()
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
        this.housesConfig.values().stream()
                .forEach(a->set_casas.add(a.clone()));

        return set_casas;
    }

    public TreeSet<Fatura> faturasFornecedor(Fornecedor forn) {
        TreeSet<Fatura> lista = new TreeSet<>();

        this.housesConfig.values().stream().filter(a-> a.getFornecedor().getName().equals(forn.getName())).forEach(a -> {
            try {
                lista.add(a.faturaCasa(1).clone());
            } catch (FaturaException e) {
                throw new RuntimeException(e);
            }
        });

        return lista;
    }

    public Fornecedor fornecedorMaisFaturou(){
        Fornecedor maior = null;
        double consumomaior=0;
        double consumoforn=0;
        for(Fornecedor forn : this.fornecedores.values()){
            consumoforn =faturasFornecedor(forn).stream().mapToDouble(Fatura::valor_fatura).sum();
           if (consumomaior <  consumoforn) {
               consumomaior =  consumoforn;
               maior = forn ;
           }
        }
        return maior;
    }

}
