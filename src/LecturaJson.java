import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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

    public boolean existeubicacion (){
        return existeubicacion;
    }
}
