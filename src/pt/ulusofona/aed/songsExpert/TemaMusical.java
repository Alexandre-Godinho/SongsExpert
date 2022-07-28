package pt.ulusofona.aed.songsExpert;

import java.util.ArrayList;

public class TemaMusical {
    String id;
    String titulo;
    int anoProducao;
    ArrayList<String> artistas;


    public  TemaMusical(String id, String titulo,int anoProducao, ArrayList<String> artistas) {
        this.id = id;
        this.titulo = titulo;
        this.anoProducao = anoProducao;
        this.artistas = artistas;

    }
    public String toString() {
        String texto = "";
        for(int i = 0; i < artistas.size(); i++){
            if(i == artistas.size()-1) {
                texto += artistas.get(i);
            }else{
                texto += artistas.get(i)+ " / ";
            }
        }
        if(anoProducao < 1 || anoProducao > 2019){
            return id + " | " + titulo + " | Ano desconhecido | " + texto;
        }else{
            return id + " | " + titulo + " | " + anoProducao + " | " + texto;
        }
    }

}

