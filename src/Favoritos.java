import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.LinkedList;

/**
 * Created by jorti on 05/01/2017.
 */
public class Favoritos {
    LinkedList<Site> favoritos;
    MostrarPantalla mp;

    public Favoritos (String archivo){
        favoritos = new LinkedList<Site>();
        String info;
        try {
            FileReader fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr);
            info = br.readLine();

            JsonParser parser = new JsonParser();
            JsonObject jofavoritos = (JsonObject) parser.parse(info);
            JsonArray jafavoritos = jofavoritos.get("favorites").getAsJsonArray();
            for (int i = 0; i < jafavoritos.size(); i++){
                JsonObject aux = jafavoritos.get(i).getAsJsonObject();
                favoritos.add(new Site(aux.get("name").getAsString(), aux.get("street").getAsString()));
            }
        } catch (FileNotFoundException file){
            mp.error("El archivo favorites_places.json no existe."+
                    "\nEl programa creará uno automáticamente.");
            try {
                FileWriter fw = new FileWriter(archivo);
                fw.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        } catch (IOException io){
            mp.error("Lectura erronea en el archivo faovirtes_places.json");
        } catch (NullPointerException nullex){
            mp.error("El archivo favorites_places.json se guardo de forma errónea. " +
                    "\nY no se ha podido acceder a la información que contenia." +
                    "\nSe pide al usuario cerrar el bien el programa la próxima vez.");
        }


    }

    public void addFavoritos (Site site){
        favoritos.add(site);
    }

    public void saveFavoritos (String archivo){
        JsonArray ja = new JsonArray();
        for (int i = 0; i < favoritos.size(); i++){
            JsonObject joaux = new JsonObject ();
            joaux.addProperty("name", favoritos.get(i).getName());
            joaux.addProperty("street", favoritos.get(i).getCalle());
            ja.add((JsonElement) joaux);
        }
        try {
            FileWriter fw = new FileWriter (archivo);
            BufferedWriter bw = new BufferedWriter (fw);
            bw.write("{\"favorites\":" + ja.toString() + "}");
            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException e){
            mp.error("Error en la escritura del archivo "+archivo+".");
        }
    }

    public LinkedList<Site> getFavoritos (){return favoritos;}
}
