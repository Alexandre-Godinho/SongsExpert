package pt.ulusofona.aed.songsExpert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static pt.ulusofona.aed.songsExpert.Main.askTheExpert;
import static pt.ulusofona.aed.songsExpert.Main.lerFicheiros;

public class TestsMain {



    @Test
    public void countSongs() {
        lerFicheiros("test-files/deisi_songs.csv", "test-files/deisi_artists.csv","test-files/deisi_artist_similarity.csv", "test_files/deisi_artist_location");
        String resultadoReal = askTheExpert("COUNT_SONGS");
        String resultadoEsperado = "1000005";
        assertEquals("devia dar 1000005",resultadoEsperado,resultadoReal);
    }

    @Test
    public void countArtistsManySongs() {
        lerFicheiros("test-files/deisi_songs.csv", "test-files/deisi_artists.csv","test-files/deisi_artist_similarity.csv", "test_files/deisi_artist_location");
        String resultadoReal = askTheExpert("COUNT_ARTISTS_MANY_SONGS 10");
        String resultadoEsperado = "27816";
        assertEquals("devia dar 27816",resultadoEsperado,resultadoReal);
    }

    @Test
    public void countSongsManyArtists() {
        lerFicheiros("test-files/deisi_songs.csv", "test-files/deisi_artists.csv","test-files/deisi_artist_similarity.csv", "test_files/deisi_artist_location");
        String resultadoReal = askTheExpert("COUNT_SONGS_MANY_ARTISTS 10");
        String resultadoEsperado = "98";
        assertEquals("devia dar 98",resultadoEsperado,resultadoReal);
    }

    @Test
    public void countUniqueTitles() {
        lerFicheiros("test-files/deisi_songs.csv", "test-files/deisi_artists.csv","test-files/deisi_artist_similarity.csv", "test_files/deisi_artist_location");
        String resultadoReal = askTheExpert("COUNT_UNIQUE_TITLES");
        String resultadoEsperado = "702427";
        assertEquals("devia dar 702427",resultadoEsperado,resultadoReal);
    }

    @Test
    public void getSongsWithArtists() {
        lerFicheiros("test-files/deisi_songs.csv", "test-files/deisi_artists.csv","test-files/deisi_artist_similarity.csv", "test_files/deisi_artist_location");
        String resultadoReal = askTheExpert("GET_SONGS_WITH_ARTISTS Renata Scotto");
        String resultadoEsperado = "Gran ventura|Or vienmi ad adornar";
        assertEquals("devia dar Gran ventura|Or vienmi ad adornar",resultadoEsperado,resultadoReal);
    }

    @Test
    public void getTopArtistsWithSongsInYear() {
        lerFicheiros("test-files/deisi_songs.csv", "test-files/deisi_artists.csv","test-files/deisi_artist_similarity.csv", "test_files/deisi_artist_location");
        String resultadoReal = askTheExpert("GET_TOP_ARTISTS_WITH_SONGS_IN_YEAR 3 2010");
        String resultadoEsperado = "PlayRadioPlay! 36\nJoe Rogan 27\nJes 23";
        assertEquals("devia dar PlayRadioPlay! 36\nJoe Rogan 27\nJes 23",resultadoEsperado,resultadoReal);
    }

    @Test
    public void getUniqueSongsByArtist() {
        lerFicheiros("test-files/deisi_songs.csv", "test-files/deisi_artists.csv","test-files/deisi_artist_similarity.csv", "test_files/deisi_artist_location");
        String resultadoReal = askTheExpert("GET_UNIQUE_SONGS_BY_ARTIST The Police");
        String resultadoEsperado = "Wrapped Around Your Finger|When The World Is Running Down_ You Make The Best Of What's Still Around|Walking On The Moon|Walking In Your Footsteps|Voices Inside My Head|Truth Hits Everybody|Too Much Information|The Other Way Of Stopping|The Bed's Too Big Without You|Tea In The Sahara|Synchronicity II|Synchronicity I|Spirits In The Material World|So Lonely|Shambelle|Shadows In The Rain|Secret Journey|Roxanne|Rehumanize Yourself|Reggatta De Blanc|Re-Humanise Yourself|Peanuts|One World (Not Three)|Once Upon A Daydream|On Any Other Day|Omegaman|O My God|Nothing Achieving|No Time This Time|Next To You|Mother|Miss Gradenko|Message In A Bottle|Masoko Tanga|Man In A Suitcase|Low Life|Landlord|King Of Pain|It's Alright For You|Invisible Sun|I Burn For You|Hungry For You (J'Aurais Toujours Faim De Toi)|Hungry For You|Hole In My Life|Friends|Flexible Strategies|Fall Out|Every Little Thing She Does Is Magic|Every Breath You Take|Driven To Tears [Live From Live Earth]|Driven To Tears|Don't Stand So Close To Me '86|Don't Stand So Close To Me|Does Everyone Stare|Demolition Man|Deathwish|De Do Do Do_ De Da Da Da|Contact|Canary In A Coalmine|Can't Stand Losing You|Bring On The Night|Born In The 50's|Bombs Away|Behind My Camel|Be My Girl - Sally|A Kind Of Loving";
        assertEquals("devia dar Wrapped Around Your Finger|When The World Is Running Down_ You Make The Best Of What's Still Around|Walking On The Moon|Walking In Your Footsteps|Voices Inside My Head|Truth Hits Everybody|Too Much Information|The Other Way Of Stopping|The Bed's Too Big Without You|Tea In The Sahara|Synchronicity II|Synchronicity I|Spirits In The Material World|So Lonely|Shambelle|Shadows In The Rain|Secret Journey|Roxanne|Rehumanize Yourself|Reggatta De Blanc|Re-Humanise Yourself|Peanuts|One World (Not Three)|Once Upon A Daydream|On Any Other Day|Omegaman|O My God|Nothing Achieving|No Time This Time|Next To You|Mother|Miss Gradenko|Message In A Bottle|Masoko Tanga|Man In A Suitcase|Low Life|Landlord|King Of Pain|It's Alright For You|Invisible Sun|I Burn For You|Hungry For You (J'Aurais Toujours Faim De Toi)|Hungry For You|Hole In My Life|Friends|Flexible Strategies|Fall Out|Every Little Thing She Does Is Magic|Every Breath You Take|Driven To Tears [Live From Live Earth]|Driven To Tears|Don't Stand So Close To Me '86|Don't Stand So Close To Me|Does Everyone Stare|Demolition Man|Deathwish|De Do Do Do_ De Da Da Da|Contact|Canary In A Coalmine|Can't Stand Losing You|Bring On The Night|Born In The 50's|Bombs Away|Behind My Camel|Be My Girl - Sally|A Kind Of Loving",resultadoEsperado,resultadoReal);
    }

    @Test
    public void topNArtistsWithLocation() {
        lerFicheiros("test-files/deisi_songs.csv", "test-files/deisi_artists.csv","test-files/deisi_artist_similarity.csv", "test_files/deisi_artist_location");
        String resultadoReal = askTheExpert("TOP_N_ARTISTS_WITH_LOCATION 10 Portugal");
        String resultadoEsperado = "Moonspell:84\nGNR:71\nRui Veloso:59\nSeabound:47\nMafalda Veiga:36\nRafael Toral:25\nLa Harissa:14\nPanda Bear:10\nTiger Man:4";
        assertEquals("devia dar Moonspell:84\nGNR:71\nRui Veloso:59\nSeabound:47\nMafalda Veiga:36\nRafael Toral:25\nLa Harissa:14\nPanda Bear:10\nTiger Man:4",resultadoEsperado,resultadoReal);
    }

    @Test
    public void mostFrequentWordTitles() {
        lerFicheiros("test-files/deisi_songs.csv", "test-files/deisi_artists.csv","test-files/deisi_artist_similarity.csv", "test_files/deisi_artist_location");
        String resultadoReal = askTheExpert("MOST_FREQUENT_WORD_TITLES 5");
        String resultadoEsperado = "Don't";
        assertEquals("devia dar Don't",resultadoEsperado,resultadoReal);
    }

    @Test
    public void artistMostUniqueSongs() {
        lerFicheiros("test-files/deisi_songs.csv", "test-files/deisi_artists.csv","test-files/deisi_artist_similarity.csv", "test_files/deisi_artist_location");
        String resultadoReal = askTheExpert("ARTIST_MOST_UNIQUE_SONGS 2000");
        String resultadoEsperado = "Jim Reeves";
        assertEquals("devia dar Jim Reeves",resultadoEsperado,resultadoReal);
    }

    @Test
    public void topNSimilarArtists() {
        lerFicheiros("test-files/deisi_songs.csv", "test-files/deisi_artists.csv","test-files/deisi_artist_similarity.csv", "test_files/deisi_artist_location");
        String resultadoReal = askTheExpert("TOP_N_SIMILAR_ARTISTS 7");
        String resultadoEsperado = "The Rolling Stones:146\nThe Beatles:103\nLed Zeppelin:102\nSonny Landreth:86\nJimi Hendrix:80\nMarty Robbins:80\nDinah Washington:78";
        assertEquals("devia dar The Rolling Stones:146\nThe Beatles:103\nLed Zeppelin:102\nSonny Landreth:86\nJimi Hendrix:80\nMarty Robbins:80\nDinah Washington:78",resultadoEsperado,resultadoReal);
    }

    @Test
    public void whySoSimilar() {
        lerFicheiros("test-files/deisi_songs.csv", "test-files/deisi_artists.csv","test-files/deisi_artist_similarity.csv", "test_files/deisi_artist_location");
        String resultadoReal = askTheExpert("WHY_SO_SIMILAR? ARXZP9T1187FB4565D AR82GTS1187FB5662F");
        String resultadoEsperado = "Moving Cloud\nThe Chieftains";
        assertEquals("devia dar Moving Cloud\nThe Chieftains",resultadoEsperado,resultadoReal);
    }

    @Test
    public void countSongsByArtistWithRestrictions() {
        lerFicheiros("test-files/deisi_songs.csv", "test-files/deisi_artists.csv","test-files/deisi_artist_similarity.csv", "test_files/deisi_artist_location");
        String resultadoReal = askTheExpert("COUNT_SONGS_BY_ARTIST_WITH_RESTRICTIONS Placido Domingo false");
        String resultadoEsperado = "54";
        assertEquals("devia dar 54",resultadoEsperado,resultadoReal);
    }

    @Test
    public void songsWithSimilarArtists() {
        lerFicheiros("test-files/deisi_songs.csv", "test-files/deisi_artists.csv","test-files/deisi_artist_similarity.csv", "test_files/deisi_artist_location");
        String resultadoReal = askTheExpert("SONGS_WITH_SIMILAR_ARTISTS? TRMGSHL128F92F5E7B TRMFRIV128F147CBC3");
        String resultadoEsperado = "NAY";
        assertEquals("devia dar NAY",resultadoEsperado,resultadoReal);
    }

    @Test
    public void insertSong() {
        lerFicheiros("test-files/deisi_songs.csv", "test-files/deisi_artists.csv","test-files/deisi_artist_similarity.csv", "test_files/deisi_artist_location");
        String resultadoReal = askTheExpert("INSERT_SONG TIRAMISU1234,Tema lindo,1942,Rui Veloso");
        String resultadoEsperado = "Erro";
        assertEquals("devia dar Erro",resultadoEsperado,resultadoReal);
    }

    @Test
    public void removeSong() {
        lerFicheiros("test-files/deisi_songs.csv", "test-files/deisi_artists.csv","test-files/deisi_artist_similarity.csv", "test_files/deisi_artist_location");
        String resultadoReal = askTheExpert("REMOVE_SONG TRNQRKK128F92D9850");
        String resultadoEsperado = "Erro";
        assertEquals("devia dar Erro",resultadoEsperado,resultadoReal);
    }

    @Test
    public void getTopNWords() {
        lerFicheiros("test-files/deisi_songs.csv", "test-files/deisi_artists.csv","test-files/deisi_artist_similarity.csv", "test_files/deisi_artist_location");
        String resultadoReal = askTheExpert("GET_TOP_N_WORDS 6 Michael Jackson");
        String resultadoEsperado = "The:33\nYou:25\nBe:13\nMe:13\nOn:12\nMy:11";
        assertEquals("devia dar The:33\nYou:25\nBe:13\nMe:13\nOn:12\nMy:11",resultadoEsperado,resultadoReal);
    }

    @Test
    public void getLocationsWithWord() {
        lerFicheiros("test-files/deisi_songs.csv", "test-files/deisi_artists.csv","test-files/deisi_artist_similarity.csv", "test_files/deisi_artist_location");
        String resultadoReal = askTheExpert("GET_LOCATIONS_WITH_WORD Rotterdam DESC");
        String resultadoEsperado = "Rotterdam, The Netherlands|Rotterdam, The Netherlands|Rotterdam, Europe|Rotterdam|Rotterdam";
        assertEquals("devia dar Rotterdam, The Netherlands|Rotterdam, The Netherlands|Rotterdam, Europe|Rotterdam|Rotterdam",resultadoEsperado,resultadoReal);
    }

    @Test
    public void mostFrequentWordTitlesIgnoring() {
        lerFicheiros("test-files/deisi_songs.csv", "test-files/deisi_artists.csv","test-files/deisi_artist_similarity.csv", "test_files/deisi_artist_location");
        String resultadoReal = askTheExpert("MOST_FREQUENT_WORD_TITLES_IGNORING 5 don't");
        String resultadoEsperado = "Blues";
        assertEquals("devia dar Blues",resultadoEsperado,resultadoReal);
    }
}