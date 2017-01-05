import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by Javier Ortiz on 12/12/2016.
 */


public class LS_Bicing {
    private final static String FAVORITOS = "favorite_places.json";
    private final static String BINCING = "http://wservice.viabicing.cat/v2/stations";

    public static void main (String[] args){
        MostrarPantalla mp = new MostrarPantalla();
        WebService ws = new WebService();
        LecturaJson lj = new LecturaJson();
        LinkedList<EstacionBicing>  llbicing = new LinkedList<>();
        Scanner sc = new Scanner(System.in);
        Favoritos fav = new Favoritos(FAVORITOS);
        LinkedList<Ubicacion> usados = new LinkedList<>();
        Ruta maslarga = new Ruta (0);

        String sbicing = ws.getInfoFromUrl(BINCING);
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
                    Ubicacion ubi = new Ubicacion (lj.stringToUbicacion(sjubi));
                    if (!lj.existeubicacion()){
                        mp.error("L'ubicacio introduida no existeix");
                        break;
                    }
                    mp.carregant();
                    ws.showOnePlace(subi);

                    boolean u1 = false;
                    for (int i = 0; i < usados.size(); i++){
                        if (usados.get(i).getCalle().equals(ubi.getCalle())){
                            usados.get(i).sumCont();
                            u1 = true;
                        }
                    }
                    if (!u1){
                        usados.add(ubi);
                    }
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

                    boolean u2 = false;
                    for (int i = 0; i < usados.size(); i++){
                        if (usados.get(i).getCalle().equals(sitio.getCalle())){
                            usados.get(i).sumCont();
                            u2 = true;
                        }
                    }
                    if (!u2){
                        usados.add(sitio);
                    }
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
                    boolean u3 = false;
                    for (int i = 0; i < usados.size(); i++){
                        if (usados.get(i).getCalle().equals(ruta.getUllegada().getCalle())){
                            usados.get(i).sumCont();
                            u3 = true;
                        }
                    }
                    if (!u3){
                        usados.add(ruta.getUllegada());
                    }
                    for (int i = 0; i < usados.size(); i++){
                        if (usados.get(i).getCalle().equals(ruta.getUsalida().getCalle())){
                            usados.get(i).sumCont();
                            u3 = true;
                        }
                    }
                    if (!u3){
                        usados.add(ruta.getUsalida());
                    }
                    if (ruta.getDistacia() > maslarga.getDistacia()){
                        maslarga = ruta;
                    }
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
                    mp.resultadostoShow();
                    int resultados = sc.nextInt();
                    sc.nextLine();
                    for (int i = 0; i < resultados; i++){
                        mp.mostrarSite(llsites.get(i));
                        boolean aux = true;
                        while (aux) {
                            mp.guardaFavorits();
                            String guardar = (sc.nextLine()).toLowerCase();
                            if (guardar.equals("si")) {
                                fav.addFavoritos(llsites.get(i));
                                aux = false;
                            } else if (guardar.equals("no")) {
                                aux = false;
                            } else {
                                mp.error("Valor no válido.");
                            }
                        }
                    }
                    fav.saveFavoritos(FAVORITOS);
                    break;
                case 6:
                    mp.sitios(fav.getFavoritos());
                    break;
                case 7:
                    EstacionBicing salida = new EstacionBicing(), llegada = new EstacionBicing();
                    float mdistancia = 0;
                    for (int i = 0; i < llbicing.size(); i++){
                        if (llbicing.get(i).masLegana(llbicing) > mdistancia){
                            mdistancia = llbicing.get(i).masLegana(llbicing);
                            salida = llbicing.get(i);
                            llegada = llbicing.get(salida.getLegana());
                        }
                    }
                    mp.mayorDistacian(salida, llegada);
                    break;
                case 8:
                    mp.info(maslarga, usados);
                    break;
                case 9:
                    fav.saveFavoritos(FAVORITOS);
                    mp.adios();
                    break;
                default:
                    mp.error("Opció no válida.");
                    break;

            }
        } while (opcion != 9);


    }

}
