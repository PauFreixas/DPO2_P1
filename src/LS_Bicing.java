import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by Javier Ortiz on 12/12/2016.
 */


public class LS_Bicing {
    private final static String URLBICING = "http://wservice.viabicing.cat/v2/stations";

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
            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion){
                case 1:
                    mp.ubicacion();
                    String subi = sc.nextLine();
                    String sjubi = ws.getSiteUrl(subi);
                    lj.stringToUbicacion(sjubi);
                    if (!lj.existeubicacion()){
                        mp.error("L'ubicacio introduida no existeix");
                    } else {
                        mp.carregant();
                        ws.showOnePlace(subi);
                    }
                    break;
                case 2:
                    mp.ubicacion();
                    subi = sc.nextLine();
                    String sjubi2 = ws.getSiteUrl(subi);
                    JsonObject joubi2 = lj.stringToUbicacion(sjubi2);
                    if (!lj.existeubicacion()){
                        mp.error("L'ubicació introduida no existeix.");
                    } else {
                        mp.carregant();
                        Ubicacion sitio = new Ubicacion (joubi2);
                        EstacionBicing estacionMasCercana = sitio.calcularEstacionMasCercana(llbicing);
                        mp.listarBicing(estacionMasCercana);
                    }
            }
        } while (opcion != 9);


    }

}
