import java.util.LinkedList;

/**
 * Created by Javier Ortiz on 12/12/2016.
 */
public class MostrarPantalla {
    private final static String INTRO = System.lineSeparator();

    public MostrarPantalla(){}

    public void okBicing (){
        System.out.println("Dades carregades correctament. Benvingut al LSBicing"+ INTRO + INTRO);
    }

    public void infoSites (LinkedList<Site> llsites){
        System.out.println("S'han trobat " + llsites.size() + " resultats:" + INTRO);
    }

    public void mostrarSite (Site s) {
        System.out.println("Lloc: " + s.getName());
        System.out.println("---------------------------------------------");
        System.out.println("Adreça: " + s.getCalle());
        if (s.isOpen()){
            System.out.println("Actualmente obert.");
        } else if (s.isNotOpen()){
            System.out.println("Actualment tancat.");
        } else {
            System.out.println(s.getOpen());
        }
        System.out.println("Rating del lloc: " + s.getRating());
        System.out.println();
    }

    public void guardaFavorits (){
        System.out.println("Vols guardar-lo en el fitxer favorite_places.json? (Si/No)");
    }

    public void imprimirMenu(){
        System.out.println("************* LSBicing menu *************");
        System.out.println("1.Geolocalització");
        System.out.println("2.Buscar estació de Bicing més propera");
        System.out.println("3.Creació d'una ruta");
        System.out.println("4.Visualització d'estacions de Bicing");
        System.out.println("5.Cerca d'ubicacions");
        System.out.println("6.Visualitzar ubicació guardada");
        System.out.println("7.Estacions més llunyanes");
        System.out.println("8.Rànquing");
        System.out.println("9.Sortir");
    }

    public void opcion (){
        System.out.println(INTRO + "Introdueix una opció:");
    }

    public void error (String error){
        System.out.println("ERROR: " + error + INTRO);
    }

    public void ubicacion (){
        System.out.println("Introdueix una ubicació:");
    }

    public void carregant (){
        System.out.println("Carregant Google Maps..." + INTRO);
    }

    public void listarBicing (EstacionBicing estacio){
        System.out.println("L'estacio de bicing mes propera es troba a: " + INTRO + estacio.getCalle()+ " amb un total de " + estacio.getBikes() + " de " + (estacio.getSlots()+estacio.getBikes()) + " bicicletes disponibles." + INTRO);
        System.out.println("Les estaciones més properes a questa són " + estacio.getEstaciones().size());
        System.out.println("-------------------------------------------------" + INTRO);
        for (int i = 0; i<estacio.getEstaciones().size(); i++) {
            System.out.println((i+1) +". "+estacio.getEstaciones().get(i).getCalle());
            System.out.println(" Amb un total de "+estacio.getEstaciones().get(i).getBikes()+" de "+(estacio.getEstaciones().get(i).getSlots()+estacio.getEstaciones().get(i).getBikes())+" bicicletes disponibles.");
        }
        System.out.println(INTRO);
    }

    public void salida (){
        System.out.println("Introdueix la sortida: ");
    }

    public void llegada (){
        System.out.println("Introdueix la arribada: ");
    }

    public void infoRuta (Ruta ruta) {
        System.out.println("------------ Generant Ruta ----------");
        System.out.println("-- Origen: " + ruta.getSsalida());
        System.out.println("-- Destí: " + ruta.getSllegada());
        System.out.println("-- Distància: " + ruta.getDistacia() + " m");
        System.out.println("-- Duració: " + ruta.getTiempo()/60 +":"+ ruta.getTiempo()%60);

        System.out.println(INTRO + "Estació origen més propera: " + ruta.getEbsalida().getCalle());
        System.out.println("Estació destí més propera: " + ruta.getEbllegada().getCalle());
        System.out.println(INTRO);
    }

    public void minimo (){
        System.out.println("Mínim de bicicletes disponibles:");
    }

}
