import com.google.gson.JsonArray;

import javax.print.DocFlavor;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedList;

/**
 * Created by Usuario on 12/12/2016.
 */
public class WebService {
    private final static String GEOKEY = "AIzaSyDZWnwHprHfDxVOat8YSRrURyVRGOR8ZpE";
    private final static String SITESKEY = "AIzaSyCKK029V7HWJe8Js1TjJEZKFk2Ya__ViCI";
    private final static String DISTANCEKEY = "AIzaSyD54KlALnI2X0WBdge1fv2ZB5Xc9OXUVPU";
    private final static String MAPKEY = "AIzaSyB-fqUSCD9_XiZ96iKwRyE5RPtCUD6_t5c";

    private MostrarPantalla mp = new MostrarPantalla();
    private boolean funciona;

    public WebService(){
        funciona = true;
    }

    public String getInfoFromUrl (String url){
        String msg = new String();
        String inputLine = new String();

        try {
            URL myurl = new URL (url);
            BufferedReader in = new BufferedReader (new InputStreamReader(myurl.openStream()));
            while ((inputLine = in.readLine()) != null){
                msg = msg + inputLine;
            }
        } catch (IOException e) {
            this.funciona = false;
            mp.error("Al demanar informació al sistema. Exception:" + e);
        }

        return msg;
    }

    public String getSiteUrl (String ubicacion){
        try {
            ubicacion = URLEncoder.encode(ubicacion, "UTF-8");
        } catch (IOException e){
            e.printStackTrace();
        }
        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?";
        url = url + "query="+ubicacion+"&language=es"+"&key="+ SITESKEY;

        String msg = getInfoFromUrl(url);

        return msg;
    }

    public void showOnePlace (String s) {
        try {
            s = URLEncoder.encode(s, "UTF-8");
        } catch (IOException e){
            e.printStackTrace();
        }
        String link = "http://maps.google.com/?q=" + s;

        try {
            Desktop dk = Desktop.getDesktop();
            dk.browse(new URI(link));
        } catch (Exception e) {
            System.out.println("Error al abrir URL: " + e.getMessage());
        }
    }

    public String getRuta (Ruta ruta){
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?" +
                "origins=" +ruta.getUsalida().getLatitud() + "," + ruta.getUsalida().getLongitud() + "|" +
                ruta.getEbsalida().getLatitud() + "," + ruta.getEbsalida().getLongitud() + "|" +
                ruta.getEbllegada().getLatitud() + "," + ruta.getEbllegada().getLongitud() + "&" +
                "destinations=" + ruta.getEbsalida().getLatitud() + "," + ruta.getEbsalida().getLongitud() + "|" +
                ruta.getEbllegada().getLatitud() + "," + ruta.getEbllegada().getLongitud() + "|" +
                ruta.getUllegada().getLatitud() + "," + ruta.getUllegada().getLongitud() + "&" +
                "key=" + DISTANCEKEY;
        String msg = getInfoFromUrl(url);
        return msg;
    }

    public void showRute (Ruta ruta){
        String link = "";
        try {
            link = "http://maps.google.com/?" +
                    "saddr=" + URLEncoder.encode(ruta.getSsalida(), "UTF-8") +
                    "&daddr=" + URLEncoder.encode(ruta.getEbsalida().getCalle(), "UTF-8") +
                    "+to:" + URLEncoder.encode(ruta.getEbllegada().getCalle(), "UTF-8") +
                    "+to:" + URLEncoder.encode(ruta.getSllegada(), "UTF-8") + "&dirflg=b";
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.carregant();
        try {
            Desktop dk = Desktop.getDesktop();
            dk.browse(new URI(link));
        } catch (Exception e) {
            System.out.println("Error al abrir URL: " + e.getMessage());
        }

    }

    public void estacionesBicing (LinkedList<EstacionBicing> estaciones){
        String url = "https://maps.googleapis.com/maps/api/staticmap?size=800x600&markers=color:red";

        for (int i = 0; i < estaciones.size(); i++){
            try {
                url = url + URLEncoder.encode("|" + estaciones.get(i).getLatitud()+","+estaciones.get(i).getLongitud(), "UTF-8");
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        url = url + "&key=" + MAPKEY;

        mp.carregant();

        try {
            Desktop dk = Desktop.getDesktop();
            dk.browse(new URI(url));
        } catch (Exception e) {
            System.out.println("Error al abrir URL: " + e.getMessage());
        }
    }

    public boolean isFunciona() {
        return funciona;
    }
}
