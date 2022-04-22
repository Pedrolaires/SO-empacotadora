package transporte;

import java.util.ArrayList;

import valor.Pacote;

public class Transporte {
    private ArrayList<Pacote> pacotes;

    public Transporte(){
        this.pacotes = new ArrayList<Pacote>();
    }

    public void addPacote(Pacote p){
        this.pacotes.add(p);
    }
}
