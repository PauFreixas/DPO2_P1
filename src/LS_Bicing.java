import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by Javier Ortiz on 12/12/2016.
 */


public class LS_Bicing {
    private final static String URLBICING = "http://wservice.viabicing.cat/v2/stations";
    private final static String FAVORITOS = "favorite_places.json";

    public static void main (String[] args){
        MostrarPantalla mp = new MostrarPantalla();
        WebService ws = new WebService();
        LecturaJson lj = new LecturaJson();
        LinkedList<EstacionBicing>  llbicing = new LinkedList<>();
        Scanner sc = new Scanner(System.in);

        String sbicing = ws.getInfoFromUrl(URLBICING);
        if (!ws.isFunciona()){
            return;
        }
        JsonArray jabicing = lj.stringToBicing(sbicing);
        for (int i = 0; i < jabicing.size(); i++){
            llbicing.add(new EstacionBicing((JsonObject)jabicing.get(i)));
        }
        for (int i = 0; i < llbicing.size(); i++){
            llbicing.get(i).assignaestacio(llbicing);
        }

        mp.okBicing();

        int opcion;

        do {
            mp.imprimirMenu();
            mp.opcion();
            try {
                opcion = sc.nextInt();
            } catch (InputMismatchException e){
                opcion = 0;
            }
            sc.nextLine();
            switch (opcion){
                case 1:
                    mp.ubicacion();
                    String subi = sc.nextLine();
                    String sjubi = ws.getSiteUrl(subi);
                    lj.stringToUbicacion(sjubi);
                    if (!lj.existeubicacion()){
                        mp.error("L'ubicacio introduida no existeix");
                        break;
                    }
                    mp.carregant();
                    ws.showOnePlace(subi);
                    break;
                case 2:
                    mp.ubicacion();
                    subi = sc.nextLine();
                    String sjubi2 = ws.getSiteUrl(subi);
                    JsonObject joubi2 = lj.stringToUbicacion(sjubi2);
                    if (!lj.existeubicacion()){
                        mp.error("L'ubicació introduida no existeix.");
                        break;
                    }
                    mp.carregant();
                    Ubicacion sitio = new Ubicacion (joubi2);
                    EstacionBicing estacionMasCercana = sitio.calcularEstacionMasCercana(llbicing);
                    mp.listarBicing(estacionMasCercana);
                    break;
                case 3:
                    mp.salida();
                    String ssalida = sc.nextLine();
                    mp.llegada();
                    String sllegada = sc.nextLine();

                    Ruta ruta = new Ruta ();

                    if (!(ruta.iniciarRuta(ssalida, sllegada, llbicing))){
                        mp.error("Dades Inválides.");
                        break;
                    }
                    String sinforuta = ws.getRuta(ruta);
                    ruta = lj.infoToRuta(sinforuta, ruta);
                    ruta.comprobarEBSalida();
                    mp.infoRuta(ruta);
                    ws.showRute(ruta);
                    break;
                case 4:
                    mp.minimo();
                    int minimo = Integer.parseInt(sc.nextLine());
                    LinkedList<EstacionBicing> estacionesmostrar = new LinkedList<>();

                    for (int i = 0; i < llbicing.size(); i++){
                        if (llbicing.get(i).getBikes() > minimo){
                            estacionesmostrar.add(llbicing.get(i));
                        }
                    }
                    ws.estacionesBicing(estacionesmostrar);
                    break;
                case 5:
                    mp.ubicacion();
                    String subi5 = sc.nextLine();
                    String sjubi5 = ws.getSiteUrl(subi5);
                    JsonArray jasites = lj.stringToSites(sjubi5);
                    if (!lj.existeubicacion()){
                        mp.error("L'ubicacio introduida no existeix");
                        break;
                    }
                    LinkedList<Site> llsites= new LinkedList<>();
                    for (int i = 0; i < jasites.size(); i++){
                        llsites.add(new Site ((JsonObject)jasites.get(i)));
                    }
                    mp.infoSites(llsites);
                    for (int i = 0; i < llsites.size(); i++){
                        mp.mostrarSite(llsites.get(i));
                        boolean aux = true;
                        while (aux) {
                            mp.guardaFavorits();
                            String guardar = (sc.nextLine()).toLowerCase();
                            if (guardar.equals("si")) {
                                llsites.get(i).escribirFavoritos(llsites.get(i).toJson(), FAVORITOS);
                                aux = false;
                            } else if (guardar.equals("no")) {
                                aux = false;
                            } else {
                                mp.error("Valor no válido.");
                            }
                        }
                    }
                    break;
                case 6:
                    JsonArray favs = lj.leerFavoritos(FAVORITOS);
                    llsites = new LinkedList<>();
                    for (int i = 0; i < favs.size(); i++){
                        llsites.add(new Site ((JsonObject)favs.get(i)));
                    }
                    mp.mostrarFavoritos(llsites);
                    int sitio = 0;
                    try {
                        sitio = sc.nextInt();
                        JsonObject aux = favs.get(sitio).getAsJsonObject();
                        ws.showOnePlace(aux.get("address").getAsString());
                    } catch (InputMismatchException e) {
                        System.out.println("Error al introduir les dades.");
                    }
                    break;
                default:
                    mp.error("Opció no válida.");
                    break;

            }
        } while (opcion != 9);


    }

}
