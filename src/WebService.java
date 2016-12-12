import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

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
        ubicacion = ubicacion.replace(" ","+");
        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?";
        url = url + "query="+ubicacion+"&language=es"+"&key="+ SITESKEY;

        System.out.println(url);

        String msg = getInfoFromUrl(url);

        return msg;
    }

    public void showOnePlace (String s) {
        s = s.replace(" ", "+");
        String link = "http://maps.google.com/?q=" + s;

        try {
            Desktop dk = Desktop.getDesktop();
            dk.browse(new URI(link));
        } catch (Exception e) {
            System.out.println("Error al abrir URL: " + e.getMessage());
        }
    }

    public boolean isFunciona() {
        return funciona;
    }
}
