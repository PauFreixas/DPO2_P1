import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Created by Javier Ortiz on 12/12/2016.
 */
public class MostrarPantalla {
    private final static String INTRO = System.lineSeparator();

    public MostrarPantalla(){}

    public void sitios (LinkedList<Site> sitios){
        System.out.println("----- LLOC PREFERITS -----");
        for (int i = 0; i < sitios.size(); i++){
            System.out.println((i+1) + ". " + sitios.get(i).getName() + " - " + sitios.get(i).getCalle());
        }
    }

    public void okBicing (){
        System.out.println("Dades carregades correctament. Benvingut al LSBicing"+ INTRO + INTRO);
    }

    public void infoSites (LinkedList<Site> llsites){
        System.out.println("S'han trobat " + llsites.size() + " resultats." + INTRO);
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
        System.out.println(INTRO+INTRO+"************* LSBicing menu *************");
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

    public void resultadostoShow (){
        System.out.println("Cuantos resultados quieres mostrar?");
    }

    public void mayorDistacian (EstacionBicing salida, EstacionBicing llegada){
        System.out.println("Las estaciones mas leganas entre si son " + salida.getCalle() + " y " + llegada.getCalle() + ".");
    }

    public void adios (){
        System.out.println("Gracias por utilizarnos.");
        System.out.println("LSBICING by Javier Ortiz & Pau Freixes");
    }

    public void info (Ruta ruta, LinkedList<Ubicacion> llubi){
        Collections.sort(llubi, new Comparator<Ubicacion>() {
            @Override
            public int compare(Ubicacion u1, Ubicacion u2) {
                return u2.getCont() - u1.getCont();
            }
        });

        System.out.println("------ Lugares más visitados ------");
        if (llubi.size() == 0){
            error("No se han introducido ningún lugar.");
        } else if (llubi.size() > 10){
            for (int i = 0; i < 10; i++){
                System.out.println("Nombre: " + llubi.get(i).getCalle());
                System.out.println("Latitud: " + llubi.get(i).getLatitud());
                System.out.println("Longitud: " + llubi.get(i).getLongitud());
            }
        } else {
            for (int i = 0; i < llubi.size(); i++){
                System.out.println("Nombre: " + llubi.get(i).getCalle());
                System.out.println("Latitud: " + llubi.get(i).getLatitud());
                System.out.println("Longitud: " + llubi.get(i).getLongitud());
            }
        }

        System.out.println("------ Ruta más Larga ------");
        if (ruta.getSllegada() == null){
            error("No se ha introducido ninguna ruta.");
        } else {
            System.out.println("-- Origen: " + ruta.getSsalida());
            System.out.println("-- Destí: " + ruta.getSllegada());
            System.out.println("-- Distància: " + ruta.getDistacia() + " m");
            System.out.println("-- Duració: " + ruta.getTiempo() / 60 + ":" + ruta.getTiempo() % 60);
        }

    }
}
