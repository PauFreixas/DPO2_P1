import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Usuario on 12/12/2016.
 */
public class LecturaJson {
    private boolean existeubicacion;

    public LecturaJson (){
        existeubicacion = true;
    }

    public JsonArray stringToBicing(String info){

        JsonParser parser = new JsonParser();
        JsonObject obj = (JsonObject) parser.parse(info);
        JsonArray jarray = (JsonArray) obj.get("stations");

        return jarray;
    }

    public JsonObject stringToUbicacion (String info){
        existeubicacion = true;

        JsonParser parser = new JsonParser();
        JsonObject obj = (JsonObject) parser.parse(info);
        JsonArray jarray = (JsonArray) obj.get("results");
        JsonObject joubi = new JsonObject();
        if (!("OK".equals(obj.get("status").getAsString()))){
            existeubicacion = false;
        } else {
            joubi = (JsonObject) jarray.get(0);
        }
        return joubi;

    }

    public JsonArray stringToSites (String info){
        existeubicacion = true;

        JsonParser parser = new JsonParser();
        JsonObject obj = (JsonObject) parser.parse(info);
        JsonArray jarray = (JsonArray) obj.get("results");
        JsonArray joubi = new JsonArray();
        if (!("OK".equals(obj.get("status").getAsString()))){
            existeubicacion = false;
        } else {
            joubi = jarray;
        }
        return joubi;

    }

    public void escribirFavoritos (JsonObject obj, String archivo){
        try {
            FileWriter fw = new FileWriter (archivo);
            fw.write(obj.toString());
            fw.flush();
            fw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public JsonArray leerFavoritos (String archivo){
        JsonArray ja = new JsonArray();
        String sja = new String();
        try {
            FileReader fr = new FileReader (archivo);
            BufferedReader br = new BufferedReader (fr);
            String linea;
            while ((linea = br.readLine()) != null){
                sja = sja + linea;
            }
            br.close();
            fr.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        JsonParser parser = new JsonParser ();
        JsonElement aux = parser.parse(sja);
        JsonArray sites = new JsonArray();
        return sites;
    }

    public Ruta infoToRuta (String info, Ruta ruta){
        int tiempo = 0;
        int distancia = 0;

        JsonParser parser = new JsonParser();
        JsonObject obj = (JsonObject) parser.parse(info);
        JsonArray inici = obj.get("origin_addresses").getAsJsonArray();
        ruta.setSsalida(inici.get(0).getAsString());
        JsonArray llegada = obj.get("destination_addresses").getAsJsonArray();
        ruta.setSllegada(llegada.get(llegada.size()-1).getAsString());

        JsonArray casillas = (JsonArray) obj.get("rows");

        for (int i = 0; i < casillas.size(); i++){
            JsonObject elements = casillas.get(i).getAsJsonObject();
            JsonArray asdf = elements.get("elements").getAsJsonArray();
            JsonObject joinfo = asdf.get(i).getAsJsonObject();

            JsonObject distance = joinfo.get("distance").getAsJsonObject();
            distancia = distancia + distance.get("value").getAsInt();

            JsonObject duration = joinfo.get("duration").getAsJsonObject();
            tiempo = tiempo + duration.get("value").getAsInt();
        }

        ruta.setDistacia(distancia);
        ruta.setTiempo(tiempo);

        return ruta;
    }

    public boolean existeubicacion (){
        return existeubicacion;
    }
}
