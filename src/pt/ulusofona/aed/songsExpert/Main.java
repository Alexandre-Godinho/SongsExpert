package pt.ulusofona.aed.songsExpert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.*;
import java.io.IOException;
import java.io.File;

public class Main {
    private static ArrayList<Artista> artistaArrayList = new ArrayList<Artista>();
    private static ArrayList<TemaMusical> temaMusicalArrayList = new ArrayList<TemaMusical>();
    private static ArrayList<TemaMusical> temaMusicalArrayListAtualizada = new ArrayList<TemaMusical>();
    private static HashMap<String, Integer> temasMusicais = new HashMap<>();

    public static void lerFicheiros() {
        lerFicheiros("deisi_songs.csv", "deisi_artists.csv", "deisi_artist_location.csv", "deisi_artist_similarity.csv");
    }

    public static void lerFicheiros(String filenameSongs, String filenameArtists, String fileLocationArtists, String fileArtistsSimilarity) {
        int count = 0, line = 0;
        String nomeFicheiro = " ";
        ArrayList<String> nomesArtistasArrayList = new ArrayList<String>();

        do {
            switch (count) {
                case 0: {
                    nomeFicheiro = filenameArtists;
                    break;
                }
                case 1: {
                    nomeFicheiro = fileLocationArtists;
                    break;
                }
                case 2: {
                    nomeFicheiro = fileArtistsSimilarity;
                    break;
                }
                case 3: {
                    nomeFicheiro = filenameSongs;
                    break;
                }
            }
            BufferedReader leitorFicheiro = null;
            try {
                String linha;
                File ficheiro = new File(nomeFicheiro);
                leitorFicheiro = new BufferedReader(new FileReader(ficheiro));
                String dados[];

                while ((linha = leitorFicheiro.readLine()) != null) {
                    if (count != 1) {
                        dados = linha.split(",");
                    } else {
                        String spliter = ",\"";
                        dados = linha.split(spliter);
                    }
                    while (dados.length != 2 && count != 3 || dados.length != 4 && count == 3) {
                        linha = leitorFicheiro.readLine();
                        if (count != 1) {
                            dados = linha.split(",");
                        } else {
                            String spliter = ",\"";
                            dados = linha.split(spliter);
                        }
                    }
                    switch (count) {
                        case 0: {
                            ArrayList<String> artistasSemelhantes = new ArrayList<String>();
                            Artista artista = new Artista(dados[0], dados[1], "", artistasSemelhantes);
                            nomesArtistasArrayList.add(dados[1]);
                            artistaArrayList.add(artista);
                            break;
                        }
                        case 1: {
                            String id = dados[0];
                            int valido = 0, pos = 0;
                            while (pos < artistaArrayList.size() && valido == 0) {
                                if (id.equals(artistaArrayList.get(pos).id)) {
                                    artistaArrayList.get(pos).localizacao = dados[1].replace("\"", "");
                                    valido = 1;
                                }
                                pos++;
                            }
                            break;
                        }
                        case 2: {
                            String id1 = dados[0];
                            String id2 = dados[1];
                            int pos = 0, valido = 0;
                            while (pos < artistaArrayList.size() && valido == 0) {
                                if (id1.equals(artistaArrayList.get(pos).id)) {
                                    artistaArrayList.get(pos).artistasSemelhantes.add(id2);
                                    valido = 1;
                                }
                                pos++;
                            }
                            break;
                        }
                        case 3: {
                            int ano = 0;
                            if (!(dados[2].equals(""))) {
                                ano = Integer.parseInt(dados[2]);
                            }
                            String nomesArtistas[] = dados[3].split(";");
                            ArrayList<String> artistas = new ArrayList<String>(Arrays.asList(nomesArtistas));
                            ArrayList<String> artistas2 = new ArrayList<String>(Arrays.asList(nomesArtistas));
                            TemaMusical temaMusical = new TemaMusical(dados[0], dados[1], ano, artistas);
                            TemaMusical temaMusical2 = new TemaMusical(dados[0], dados[1], ano, artistas2);
                            int i = 0, valido = 0;
                            while (i < artistas.size()) {
                                if (temasMusicais.containsKey(artistas.get(i))) {
                                    int aux = temasMusicais.get(artistas.get(i));
                                    temasMusicais.replace(artistas.get(i), aux + 1);
                                } else {
                                    temasMusicais.put(artistas.get(i), 1);
                                }
                                if (nomesArtistasArrayList.contains(artistas.get(i)) && valido == 0) {
                                    valido = 1;
                                    temaMusicalArrayList.add(temaMusical);
                                    temaMusicalArrayListAtualizada.add(temaMusical2);
                                }
                                i++;
                            }
                            break;
                        }
                    }
                    line++;
                }
                leitorFicheiro.close();
            } catch (IOException ficheiroNaoEncontrado) {
                String mensagem = "Erro: o ficheiro " + nomeFicheiro + " nao foi encontrado.";
                System.out.println(mensagem);
            }
            count++;
            line = 0;
        } while (count < 4);
        info();
    }

    static void info() {
        HashMap<String, Integer> artistas = new HashMap<>();
        for (int i = 0; i < artistaArrayList.size(); i++) {
            artistas.put(artistaArrayList.get(i).nome, i);
        }
        for (int i = 0; i < temaMusicalArrayListAtualizada.size(); i++) {
            for (int e = 0; e < temaMusicalArrayListAtualizada.get(i).artistas.size(); e++) {
                String artista = temaMusicalArrayListAtualizada.get(i).artistas.get(e);
                if (artistas.containsKey(artista)) {
                    String texto = artista.concat(" (" + temasMusicais.get(artista) + " - " + numeroArtistasSemelhantes(artistas.get(artista)) + ")");
                    temaMusicalArrayListAtualizada.get(i).artistas.set(e, texto);
                } else {
                    String texto = artista.concat(" (0-0)");
                    temaMusicalArrayListAtualizada.get(i).artistas.set(e, texto);
                }
            }

        }
    }

    public static ArrayList<TemaMusical> obterTemasMusicais() {
        return temaMusicalArrayListAtualizada;
    }

    static int numeroArtistasSemelhantes(int numero) {
        return artistaArrayList.get(numero).artistasSemelhantes.size();
    }

    static String[] ordenarPal(String[] aOrdenar, int left, int right) {
        if (left < right) {
            int posicaoPivot = partitionPal(aOrdenar, left, right - 1);

            aOrdenar = ordenarPal(aOrdenar, left, posicaoPivot);
            aOrdenar = ordenarPal(aOrdenar, posicaoPivot + 1, right);
        }
        return aOrdenar;
    }

    static String[] ordenarPalavras(String[] palavras) {
        return ordenarPal(palavras, 0, palavras.length);
    }

    static int partitionPal(String[] palavras, int left, int right) {
        String pivot = palavras[right];

        int leftIdx = left;
        int rightIdx = right - 1;

        while (leftIdx <= rightIdx) {
            if (palavras[leftIdx].compareTo(pivot) > 0 && palavras[rightIdx].compareTo(pivot) < 0) {
                String temp = palavras[leftIdx];
                palavras[leftIdx] = palavras[rightIdx];
                palavras[rightIdx] = temp;
            }
            if (palavras[leftIdx].compareTo(pivot) <= 0) {
                leftIdx++;
            }

            if (palavras[rightIdx].compareTo(pivot) >= 0) {
                rightIdx--;
            }
        }

        palavras[right] = palavras[leftIdx];
        palavras[leftIdx] = pivot;

        return leftIdx;
    }

    static int[] ordenarInt(int[] numeros, int left, int right) {
        if (left < right) {
            int posicaoPivot = partitionInt(numeros, left, right - 1);

            numeros = ordenarInt(numeros, left, posicaoPivot);
            numeros = ordenarInt(numeros, posicaoPivot + 1, right);
        }

        return numeros;
    }

    static int[] ordenarInteiros(int[] numeros) {
        return ordenarInt(numeros, 0, numeros.length);
    }

    static int partitionInt(int[] numeros, int left, int right) {
        int pivot = numeros[right];

        int leftIdx = left;
        int rightIdx = right - 1;

        while (leftIdx <= rightIdx) {
            if (numeros[leftIdx] > pivot && numeros[rightIdx] < pivot) {
                int temp = numeros[leftIdx];
                numeros[leftIdx] = numeros[rightIdx];
                numeros[rightIdx] = temp;
            }
            if (numeros[leftIdx] <= pivot) {
                leftIdx++;
            }

            if (numeros[rightIdx] >= pivot) {
                rightIdx--;
            }
        }

        numeros[right] = numeros[leftIdx];
        numeros[leftIdx] = pivot;

        return leftIdx;
    }

    static HashMap<String, ArrayList<String>> musicasDoAno(int ano) {
        HashMap<String, ArrayList<String>> musicas = new HashMap<>();
        for (int i = 0; i < temaMusicalArrayList.size(); i++) {
            if (temaMusicalArrayList.get(i).anoProducao == ano) {
                musicas.put(temaMusicalArrayList.get(i).id, temaMusicalArrayList.get(i).artistas);
            }
        }
        return musicas;
    }

    static HashSet<String> artistasLocal(String local) {
        HashSet<String> artistas = new HashSet<>();
        for (int pos = 0; pos < artistaArrayList.size(); pos++) {
            if (artistaArrayList.get(pos).localizacao.contains(local)) {
                artistas.add(artistaArrayList.get(pos).nome);
            }
        }
        return artistas;
    }

    static HashMap<String, Integer> artistasSemelhantes() {
        HashMap<String, Integer> artistas = new HashMap<>();
        for (int pos = 0; pos < artistaArrayList.size(); pos++) {
            artistas.put(artistaArrayList.get(pos).nome, artistaArrayList.get(pos).artistasSemelhantes.size());
        }
        return artistas;
    }

    static HashMap<String, Integer> topArtistas(HashMap<String, ArrayList<String>> musicas) {
        HashMap<String, Integer> artistas = new HashMap<>();
        for (String id : musicas.keySet()) {
            for (int i = 0; i < musicas.get(id).size(); i++) {
                if (artistas.containsKey(musicas.get(id).get(i))) {
                    int aux = artistas.get(musicas.get(id).get(i));
                    artistas.replace(musicas.get(id).get(i), aux + 1);
                } else {
                    artistas.put(musicas.get(id).get(i), 1);
                }
            }
        }
        return artistas;
    }

    static String askTheExpert(String query) {
        ArrayList<TemaMusical> temaMusicalValidArrayList = new ArrayList<TemaMusical>();
        temaMusicalValidArrayList = obterTemasMusicais();
        String queryDividida[] = query.split(" ");
        switch (queryDividida[0]) {
            case "COUNT_SONGS": {
                return String.valueOf(temaMusicalValidArrayList.size());
            }
            case "COUNT_ARTISTS_MANY_SONGS": {
                int artistasMaisXtemas = Integer.valueOf(queryDividida[1]);
                int countArtistas = 0;
                for (int pos = 0; pos < artistaArrayList.size(); pos++) {
                    int count = 0;
                    if (temasMusicais.containsKey(artistaArrayList.get(pos).nome)) {
                        count = temasMusicais.get(artistaArrayList.get(pos).nome);
                    }
                    if (count > artistasMaisXtemas) {
                        countArtistas++;
                    }
                }
                return String.valueOf(countArtistas);
            }
            case "COUNT_SONGS_MANY_ARTISTS": {
                int countTemas = 0;
                int maisDoQueXArtistas = Integer.valueOf(queryDividida[1]);
                int artistasCount;
                for (int pos = 0; pos < temaMusicalValidArrayList.size(); pos++) {
                    artistasCount = temaMusicalValidArrayList.get(pos).artistas.size();
                    if (artistasCount > maisDoQueXArtistas) {
                        countTemas++;
                    }
                }
                return String.valueOf(countTemas);
            }
            case "COUNT_UNIQUE_TITLES": {
                HashSet<String> titulosUnicos = new HashSet<>();
                for (TemaMusical aTemaMusicalValidArrayList : temaMusicalValidArrayList) {
                    titulosUnicos.add(aTemaMusicalValidArrayList.titulo);
                }
                return String.valueOf(titulosUnicos.size());
            }
            case "GET_SONGS_WITH_ARTISTS": {
                query = query.replace(queryDividida[0] + " ", "");
                String titulos = "";
                String artistas[] = query.split(",");
                int count = 0;
                while (count < temaMusicalArrayList.size()) {
                    int countArtista = 0, valido = 0;
                    while (countArtista < artistas.length) {
                        int countArtista2 = 0;
                        int valido2 = 0;
                        while (countArtista2 < temaMusicalArrayList.get(count).artistas.size() && valido2 == 0) {
                            if (artistas[countArtista].equals(temaMusicalArrayList.get(count).artistas.get(countArtista2))) {
                                valido2 = 1;
                            }
                            countArtista2++;
                        }
                        valido += valido2;
                        countArtista++;
                    }
                    if (valido == artistas.length) {
                        if (titulos.equals("")) {
                            titulos = temaMusicalArrayList.get(count).titulo;
                        } else {
                            titulos = titulos.concat("|").concat(temaMusicalArrayList.get(count).titulo);
                        }
                    }
                    count++;
                }
                if (titulos.equals("")) {
                    return "N/A";
                } else {
                    return titulos;
                }
            }
            case "GET_TOP_ARTISTS_WITH_SONGS_IN_YEAR": {
                HashMap<String, Integer> artistas;
                artistas = topArtistas(musicasDoAno(Integer.valueOf(queryDividida[2])));
                int numeroDeMusicas[] = new int[artistas.size()], count = 0;
                for (String artista : artistas.keySet()) {
                    numeroDeMusicas[count] = artistas.get(artista);
                    count++;
                }
                numeroDeMusicas = ordenarInteiros(numeroDeMusicas);
                String texto = "";
                int numero = numeroDeMusicas.length - 1;
                for (int i = 0; i < Integer.valueOf(queryDividida[1]); i++) {
                    int valido = 0;
                    for (String artista : artistas.keySet()) {
                        if (artistas.get(artista).equals(numeroDeMusicas[numero]) && !(texto.contains(artista)) && valido == 0 && i != Integer.valueOf(queryDividida[1]) - 1) {
                            texto += artista + " " + numeroDeMusicas[numero] + "\n";
                            valido = 1;
                        }
                        if (artistas.get(artista).equals(numeroDeMusicas[numero]) && !(texto.contains(artista)) && valido == 0 && i == Integer.valueOf(queryDividida[1]) - 1) {
                            texto += artista + " " + numeroDeMusicas[numero];
                            valido = 1;
                        }
                    }
                    numero--;
                }
                if (texto.equals("")) {
                    return "N/A";
                } else {
                    return texto;
                }
            }
            case "GET_UNIQUE_SONGS_BY_ARTIST": {
                HashSet<String> titulosUnicos = new HashSet<>();
                String artista = query.replace(queryDividida[0] + " ", "");
                for (TemaMusical temaMusical : temaMusicalArrayList) {
                    if (temaMusical.artistas.contains(artista)) {
                        titulosUnicos.add(temaMusical.titulo);
                    }
                }
                String titulos[] = titulosUnicos.toArray(new String[0]);
                ordenarPalavras(titulos);
                String titulosOrdenados = "";
                for (int i = titulos.length-1; i >= 0 ; i--) {
                    if (titulosOrdenados.equals("")) {
                        titulosOrdenados += titulos[i];
                    } else {
                        titulosOrdenados += "|" + titulos[i];
                    }
                }
                return titulosOrdenados;
            }
            case "TOP_N_ARTISTS_WITH_LOCATION": {
                HashSet<String> artistas;
                artistas = artistasLocal(queryDividida[2]);
                int numeroDeMusicas[] = new int[artistas.size()], count = 0;
                for (String artista : artistas) {
                    numeroDeMusicas[count] = temasMusicais.get(artista);
                    count++;
                }
                numeroDeMusicas = ordenarInteiros(numeroDeMusicas);
                String texto = "";
                int numero = numeroDeMusicas.length - 1;
                if (numeroDeMusicas.length < Integer.valueOf(queryDividida[1])) {
                    queryDividida[1] = String.valueOf(numeroDeMusicas.length);
                }
                for (int i = 0; i < Integer.valueOf(queryDividida[1]); i++) {
                    int valido = 0;
                    for (String artista : artistas) {
                        if (temasMusicais.get(artista).equals(numeroDeMusicas[numero]) && !(texto.contains(artista)) && valido == 0 && i != Integer.valueOf(queryDividida[1]) - 1) {
                            texto += artista + ":" + numeroDeMusicas[numero] + "\n";
                            valido = 1;
                        }
                        if (temasMusicais.get(artista).equals(numeroDeMusicas[numero]) && !(texto.contains(artista)) && valido == 0 && i == Integer.valueOf(queryDividida[1]) - 1) {
                            texto += artista + ":" + numeroDeMusicas[numero];
                            valido = 1;
                        }
                    }
                    numero--;
                }
                return texto;
            }
            case "MOST_FREQUENT_WORD_TITLES": {
                String ocorerncias[] = new String[2];
                ocorerncias[0] = "";
                ocorerncias[1] = "0";
                HashMap<String, Integer> palavras = new HashMap<>();
                int tamanho = Integer.valueOf(queryDividida[1]);
                String[] tituloPalavras;
                for (int pos = 0; pos < temaMusicalValidArrayList.size(); pos++) {
                    tituloPalavras = temaMusicalValidArrayList.get(pos).titulo.split(" ");
                    for (int pos1 = 0; pos1 < tituloPalavras.length; pos1++) {
                        if (tituloPalavras[pos1].length() == tamanho) {
                            if (palavras.containsKey(tituloPalavras[pos1])) {
                                int aux = palavras.get(tituloPalavras[pos1]);
                                palavras.replace(tituloPalavras[pos1], aux + 1);
                            } else {
                                palavras.put(tituloPalavras[pos1], 1);
                            }
                        }
                    }
                }
                for (String palavra : palavras.keySet()) {
                    int ocorrenciasPalavra = palavras.get(palavra);
                    if (ocorrenciasPalavra > Integer.valueOf(ocorerncias[1])) {
                        ocorerncias[0] = palavra;
                        ocorerncias[1] = String.valueOf(ocorrenciasPalavra);
                    }
                }
                return ocorerncias[0];
            }
            case "ARTIST_MOST_UNIQUE_SONGS": {
                HashMap<String, Integer> artistas;
                artistas = topArtistas(musicasDoAno(Integer.valueOf(queryDividida[1])));
                int numeroDeMusicas[] = new int[artistas.size()], count = 0;
                for (String artista : artistas.keySet()) {
                    numeroDeMusicas[count] = artistas.get(artista);
                    count++;
                }
                numeroDeMusicas = ordenarInteiros(numeroDeMusicas);
                String texto = "";
                int numero = numeroDeMusicas.length - 1;
                int valido = 0;
                for (String artista : artistas.keySet()) {
                    if (artistas.get(artista).equals(numeroDeMusicas[numero]) && valido == 0) {
                        texto = artista;
                        valido = 1;
                    }
                }
                if (texto.equals("")) {
                    return "N/A";
                } else {
                    return texto;
                }
            }
            case "TOP_N_SIMILAR_ARTISTS": {
                HashMap<String, Integer> artistas;
                artistas = artistasSemelhantes();
                int numeroDeSemelhantes[] = new int[artistas.size()], count = 0;
                for (String artista : artistas.keySet()) {
                    numeroDeSemelhantes[count] = artistas.get(artista);
                    count++;
                }
                numeroDeSemelhantes = ordenarInteiros(numeroDeSemelhantes);
                String texto = "";
                int numero = numeroDeSemelhantes.length - 1;
                for (int i = 0; i < Integer.valueOf(queryDividida[1]); i++) {
                    int valido = 0;
                    for (String artista : artistas.keySet()) {
                        if (artistas.get(artista).equals(numeroDeSemelhantes[numero]) && !(texto.contains(artista)) && valido == 0 && i != Integer.valueOf(queryDividida[1]) - 1) {
                            texto += artista + ":" + numeroDeSemelhantes[numero] + "\n";
                            valido = 1;
                        }
                        if (artistas.get(artista).equals(numeroDeSemelhantes[numero]) && !(texto.contains(artista)) && valido == 0 && i == Integer.valueOf(queryDividida[1]) - 1) {
                            texto += artista + ":" + numeroDeSemelhantes[numero];
                            valido = 1;
                        }
                    }
                    numero--;
                }
                if (texto.equals("")) {
                    return "N/A";
                } else {
                    return texto;
                }
            }
            case "WHY_SO_SIMILAR?": {
                int artistasSemelhantesExiste = 0;
                ArrayList<String> artistasSemelhantesAosDoisArrayList = new ArrayList<>();
                String artistaID1 = queryDividida[1];
                String artistaID2 = queryDividida[2];
                ArrayList<String> artistasSemelhantesID1 = new ArrayList<>();
                ArrayList<String> artistasSemelhantesID2 = new ArrayList<>();
                for (int pos = 0; pos < artistaArrayList.size(); pos++) {
                    if (artistaID1.equals(artistaArrayList.get(pos).id)) {
                        artistasSemelhantesID1 = artistaArrayList.get(pos).artistasSemelhantes;

                    }
                    if (artistaID2.equals(artistaArrayList.get(pos).id)) {
                        artistasSemelhantesID2 = artistaArrayList.get(pos).artistasSemelhantes;
                    }
                }
                for (int pos = 0; pos < artistasSemelhantesID1.size(); pos++) {
                    for (int pos1 = 0; pos1 < artistasSemelhantesID2.size(); pos1++) {
                        if (artistasSemelhantesID1.get(pos).equals(artistasSemelhantesID2.get(pos1))) {
                            artistasSemelhantesAosDoisArrayList.add(artistasSemelhantesID1.get(pos));
                            artistasSemelhantesExiste = 1;
                        }
                    }
                }
                if (artistasSemelhantesExiste == 1) {
                    String[] artistasSemelhantesAosDois = new String[artistasSemelhantesAosDoisArrayList.size()];
                    String result = "";
                    for (int pos = 0; pos < artistasSemelhantesAosDoisArrayList.size(); pos++) {
                        artistasSemelhantesAosDois[pos] = artistasSemelhantesAosDoisArrayList.get(pos);
                    }
                    for (int pos = 0; pos < artistasSemelhantesAosDoisArrayList.size(); pos++) {
                        int count = 0, valido = 0;
                        while (count < artistaArrayList.size() && valido == 0) {
                            if (artistaArrayList.get(count).id.equals(artistasSemelhantesAosDois[pos])) {
                                artistasSemelhantesAosDois[pos] = artistaArrayList.get(count).nome;
                                valido = 1;
                            }
                            count++;
                        }
                    }
                    artistasSemelhantesAosDois = ordenarPalavras(artistasSemelhantesAosDois);
                    for (int pos = 0; pos < artistasSemelhantesAosDois.length; pos++) {
                        if (pos == artistasSemelhantesAosDois.length - 1) {
                            result += artistasSemelhantesAosDois[pos];
                        } else {
                            result += artistasSemelhantesAosDois[pos] + "\n";
                        }
                    }
                    return result;
                } else {
                    return "N/A";
                }
            }
            case "COUNT_SONGS_BY_ARTIST_WITH_RESTRICTIONS": {
                String valor = queryDividida[queryDividida.length - 1];
                query = query.replace(queryDividida[0] + " ", "");
                query = query.replace(" " + valor, "");
                int numeroMusicas = 0;
                if (valor.equals("true")) {
                    for (int i = 0; i < temaMusicalArrayList.size(); i++) {
                        if (temaMusicalArrayList.get(i).artistas.get(0).equals(query) && temaMusicalArrayList.get(i).artistas.size() == 1) {
                            numeroMusicas++;
                        }
                    }
                } else {
                    for (int i = 0; i < temaMusicalArrayList.size(); i++) {
                        if (temaMusicalArrayList.get(i).artistas.contains(query)) {
                            numeroMusicas++;
                        }
                    }
                }
                return String.valueOf(numeroMusicas);
            }
            case "SONGS_WITH_SIMILAR_ARTISTS?": {
                int existe = 0;
                ArrayList<String> artistas1 = new ArrayList<>();
                ArrayList<String> artistas2 = new ArrayList<>();
                for (int pos = 0; pos < temaMusicalArrayList.size(); pos++) {
                    if (queryDividida[1].equals(temaMusicalArrayList.get(pos).id)) {
                        artistas1 = temaMusicalArrayList.get(pos).artistas;
                    }
                }
                for (int pos = 0; pos < temaMusicalArrayList.size(); pos++) {
                    if (queryDividida[2].equals(temaMusicalArrayList.get(pos).id)) {
                        artistas2 = temaMusicalArrayList.get(pos).artistas;
                    }
                }
                if (artistas1 == null || artistas2 == null) {
                    return "N/A";
                } else {
                    for (int pos = 0; pos < artistas2.size(); pos++) {
                        if (artistas1.contains(artistas2.get(pos))) {
                            existe = 1;
                        }
                    }
                    if (existe == 1) {
                        return "YAY";
                    } else {
                        return "NAY";
                    }
                }
            }
            case "INSERT_SONG": {
                query = query.replace(queryDividida[0] + " ", "");
                String insertSongData[] = query.split(",");
                int valido = 1, valido2 = 0, count = 0;
                while (count < temaMusicalArrayList.size() && valido == 1) {
                    if (temaMusicalArrayList.get(count).id.equals(insertSongData[0])) {
                        valido = 0;
                    }
                    count++;
                }
                count = 0;
                while (count < artistaArrayList.size() && valido2 == 0) {
                    if (artistaArrayList.get(count).nome.equals(insertSongData[3])) {
                        valido2 = 1;
                    }
                    count++;
                }
                if (valido == 1 && valido2 == 1) {
                    ArrayList<String> artista = new ArrayList<>();
                    artista.add(insertSongData[3]);
                    TemaMusical insertTemaMusical = new TemaMusical(insertSongData[0], insertSongData[1], Integer.valueOf(insertSongData[2]), artista);
                    temaMusicalArrayList.add(insertTemaMusical);
                    temaMusicalArrayListAtualizada.add((insertTemaMusical));
                    return "OK";
                } else {
                    return "Erro";
                }
            }
            case "REMOVE_SONG": {
                int valido = 0, count = 0;
                while (count < temaMusicalArrayList.size() && valido == 0) {
                    if (temaMusicalArrayList.get(count).id.equals(queryDividida[1])) {
                        valido = 1;
                    }
                    count++;
                }
                if (valido == 1) {
                    temaMusicalArrayList.remove(count - 1);
                    temaMusicalArrayListAtualizada.remove(count - 1);
                    info();
                    return "OK";
                } else {
                    return "Erro";
                }
            }
            case "GET_TOP_N_WORDS": {
                int n = Integer.valueOf(queryDividida[1]);
                String nomeArtista = query.replace(queryDividida[0] + " " + queryDividida[1] + " ", "");
                HashMap<String, Integer> palavras = new HashMap<String, Integer>();
                for (TemaMusical aTemaMusicalValidArrayList : temaMusicalArrayList) {
                    if (aTemaMusicalValidArrayList.artistas.contains(nomeArtista)) {
                        String titulo = aTemaMusicalValidArrayList.titulo;
                        String[] tituloDividido = titulo.split(" ");
                        for (String aTituloDividido : tituloDividido) {
                            if (palavras.containsKey(aTituloDividido)) {
                                int count = palavras.get(aTituloDividido);
                                palavras.replace(aTituloDividido, count + 1);
                            } else {
                                palavras.put(aTituloDividido, 1);
                            }
                        }
                    }
                }
                int vezes[] = new int[palavras.size()];
                int i = 0;
                for (String numero : palavras.keySet()) {
                    vezes[i] = palavras.get(numero);
                    i++;
                }
                ordenarInteiros(vezes);
                String texto = "";
                LinkedHashMap<String, Integer> palavrasOrdenadas = new LinkedHashMap<>();
                String palavrasCorretas[];
                for (i = vezes.length - 1; i >= 0; i--) {
                    String palavraCorreta = "";
                    for (String palavra : palavras.keySet()) {
                        if (palavras.get(palavra).equals(vezes[i]) && !(palavraCorreta.contains(palavra))) {
                            if (palavraCorreta.equals("")) {
                                palavraCorreta = palavra;
                            } else {
                                palavraCorreta += ";" + palavra;
                            }
                        }
                    }
                    palavrasCorretas = ordenarPalavras(palavraCorreta.split(";"));
                    for (String palavra : palavrasCorretas) {
                        palavrasOrdenadas.put(palavra, vezes[i]);
                    }
                }

                int count = 0, valido = 0;
                for (String palavra : palavrasOrdenadas.keySet()) {
                    if (count < n - 1 && count < palavrasOrdenadas.size() - 1) {
                        texto += palavra + ":" + palavrasOrdenadas.get(palavra) + "\n";
                    }
                    if ((count == n - 1 || count == palavrasOrdenadas.size() - 1) && valido == 0) {
                        texto += palavra + ":" + palavrasOrdenadas.get(palavra);
                        valido = 1;
                    }
                    count++;
                }


                if (palavras.isEmpty()) {
                    return "N/A";
                } else {
                    return texto;
                }
            }
            case "GET_LOCATIONS_WITH_WORD": {
                StringBuilder result = new StringBuilder();
                int size;
                int existe = 0;
                ArrayList<String> localizacoesComSubstringArrayList = new ArrayList<String>();
                for (int pos = 0; pos < artistaArrayList.size(); pos++) {
                    if (artistaArrayList.get(pos) != null && artistaArrayList.get(pos).localizacao.contains(queryDividida[1])) {
                        localizacoesComSubstringArrayList.add(artistaArrayList.get(pos).localizacao);
                        existe = 1;
                    }
                }
                if (existe == 1) {
                    size = localizacoesComSubstringArrayList.size();
                    String[] localizacoesComSubstring = new String[size];
                    for (int pos = 0; pos < localizacoesComSubstringArrayList.size(); pos++) {
                        localizacoesComSubstring[pos] = localizacoesComSubstringArrayList.get(pos);
                    }
                    if (queryDividida[2].equals("ASC")) {
                        ordenarPalavras(localizacoesComSubstring);
                        for (int pos = 0; pos < localizacoesComSubstring.length; pos++) {
                            if (pos + 1 == localizacoesComSubstring.length) {
                                result.append(localizacoesComSubstring[pos]);
                            } else {
                                result.append(localizacoesComSubstring[pos]).append("|");
                            }
                        }
                    }
                    if (queryDividida[2].equals("DESC")) {
                        ordenarPalavras(localizacoesComSubstring);
                        for (int pos = localizacoesComSubstring.length - 1; pos >= 0; pos--) {
                            if (pos == 0) {
                                result.append(localizacoesComSubstring[pos]);
                            } else {
                                result.append(localizacoesComSubstring[pos]).append("|");
                            }
                        }
                    }
                    return result.toString();
                } else {
                    return "N/A";
                }
            }
            case "MOST_FREQUENT_WORD_TITLES_IGNORING": {
                int tamanho = Integer.valueOf(queryDividida[1]);
                query = query.replace(queryDividida[0] + " " + queryDividida[1] + " ", "");
                HashMap<String, Integer> frequenciaPalavras = new HashMap<>();
                String palavrasProibidas[] = query.split(" ");
                ArrayList<String> palavras = new ArrayList<>(Arrays.asList(palavrasProibidas));
                for (int i = 0; i < temaMusicalArrayList.size(); i++) {
                    String palavrasTitulo[] = temaMusicalArrayList.get(i).titulo.split(" ");
                    for (String palavra : palavrasTitulo) {
                        int valido = 0;
                        if (palavra.length() == tamanho) {
                            for (int e = 0; e < palavras.size(); e++) {
                                if (!(palavras.get(e).equalsIgnoreCase(palavra))) {
                                    valido += 1;
                                }
                            }
                            if (valido == palavras.size()) {
                                if (frequenciaPalavras.containsKey(palavra)) {
                                    int aux = frequenciaPalavras.get(palavra);
                                    frequenciaPalavras.replace(palavra, aux + 1);
                                } else {
                                    frequenciaPalavras.put(palavra, 1);
                                }
                            }
                        }
                    }
                }
                int vezes[] = new int[frequenciaPalavras.size()];
                int i = 0;
                for (String palavra : frequenciaPalavras.keySet()) {
                    vezes[i] = frequenciaPalavras.get(palavra);
                    i++;
                }
                ordenarInteiros(vezes);
                String palavraMaisUsada = "";
                for (String palavra : frequenciaPalavras.keySet()) {
                    if (frequenciaPalavras.get(palavra) == vezes[vezes.length - 1]) {
                        palavraMaisUsada = palavra;
                    }
                }
                return palavraMaisUsada;
            }
            case "QUIT": {
                return "";
            }
            default: {
                return "Query com formato inválido. Tente novamente.";
            }
        }
    }

    public static void main(String[] args) {
        lerFicheiros();
        System.out.println(temaMusicalArrayList.size());
        System.out.println(temaMusicalArrayListAtualizada.get(10602));
        System.out.println(artistaArrayList.get(24108).localizacao);
        System.out.println(askTheExpert("TOP_N_ARTISTS_WITH_LOCATION 10 Portugal"));
        System.out.println(askTheExpert("MOST_FREQUENT_WORD_TITLES 4"));
        System.out.println(askTheExpert("COUNT_UNIQUE_TITLES"));
        System.out.println(askTheExpert("COUNT_ARTISTS_MANY_SONGS 10"));
        System.out.println(askTheExpert("GET_UNIQUE_SONGS_BY_ARTIST Karkkiautomaatti"));
        System.out.println(askTheExpert("GET_LOCATIONS_WITH_WORD Rotterdam DESC"));
        System.out.println(askTheExpert("REMOVE_SONG TRNQRKK128F92D9850"));
        System.out.println(askTheExpert("GET_TOP_ARTISTS_WITH_SONGS_IN_YEAR 10 2010"));
        System.out.println(askTheExpert("GET_TOP_N_WORDS 10 Michael Jackson"));
        System.out.println(askTheExpert("MOST_FREQUENT_WORD_TITLES_IGNORING 5 don't"));
        System.out.println(askTheExpert("WHY_SO_SIMILAR? ARXZP9T1187FB4565D AR82GTS1187FB5662F"));
        System.out.println(askTheExpert("COUNT_SONGS"));
        System.out.println(askTheExpert("REMOVE_SONG 999985TRMMMYQ128F932D901"));
        System.out.println(askTheExpert("COUNT_SONGS"));
        System.out.println(askTheExpert("INSERT_SONG 999985TRMMMYQ128F932D901,Silent Night,2003,Faster Pussy cat"));
        System.out.println(askTheExpert("COUNT_SONGS"));
        System.out.println(askTheExpert("ARTIST_MOST_UNIQUE_SONGS 2001"));
        System.out.println(askTheExpert("TOP_N_SIMILAR_ARTISTS 9"));
        System.out.println(askTheExpert("COUNT_SONGS_BY_ARTIST_WITH_RESTRICTIONS Placido Domingo false"));
        System.out.println(askTheExpert("GET_SONGS_WITH_ARTISTS Renata Scotto"));
    }
}