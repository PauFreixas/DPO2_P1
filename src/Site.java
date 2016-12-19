import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by jorti on 19/12/2016.
 */
public class Site extends Ubicacion {
    private String name;
    private String open;
    private String rating;
    private boolean fav;

    public Site (){};

    public Site (JsonObject jo){
        super(jo);
        name = jo.get("name").getAsString();
        try {
            rating = jo.get("rating").getAsString();
        } catch (NullPointerException e){
            rating = "No Valorado";
        }

        try {
            JsonObject aux = jo.get("opening_hours").getAsJsonObject();
            open = Boolean.toString(aux.get("open_now").getAsBoolean());
        } catch (NullPointerException npe){
            open = "Informació no donada.";
        }

        fav = false;
    }

    public JsonObject toJson (){
        JsonObject site = new JsonObject();

        site.addProperty("address", super.getCalle());
        site.addProperty("name", getName());

        return site;
    }


    public String getName() {
        return name;
    }

    public boolean isOpen() {
        return open.equals("true");
    }

    public boolean isNotOpen(){
        return open.equals("false");
    }

    public String getOpen(){
        return open;
    }

    public String getRating() {
        return rating;
    }

    public void setFav (boolean fav){this.fav = fav;}

}
