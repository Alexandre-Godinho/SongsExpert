package pt.ulusofona.aed.songsExpert;

import java.util.ArrayList;

public class Artista {
    String id;
    String nome;
    String localizacao;
    ArrayList<String> artistasSemelhantes;

    public Artista(String id, String nome, String localizacao, ArrayList<String> artistasSemelhantes) {
        this.id = id;
        this.nome = nome;
        this.localizacao = localizacao;
        this.artistasSemelhantes = artistasSemelhantes;
    }
}
