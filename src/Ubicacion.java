import com.google.gson.JsonObject;

import java.util.LinkedList;

/**
 * Created by Usuario on 12/12/2016.
 */
public class Ubicacion {
    private String id;
    private float latitud;
    private float longitud;
    private float altitud;
    private String calle;
    private int cont;


    public Ubicacion (){}

    public Ubicacion (float latitud, float longitud, float altitud, String calle, String num){
        this.latitud = latitud;
        this.longitud = longitud;
        this.altitud = altitud;
        this.calle = calle + ", " + num;
        this.cont = 0;
    }

    public Ubicacion (JsonObject ubicacion) {
        this.id = ubicacion.get("id").getAsString();
        this.calle = ubicacion.get("formatted_address").getAsString();
        JsonObject aux = ubicacion.get("geometry").getAsJsonObject();
        JsonObject location = aux.get("location").getAsJsonObject();
        this.latitud = location.get("lat").getAsFloat();
        this.longitud = location.get("lng").getAsFloat();
        this.cont = 0;
    }

    public EstacionBicing calcularEstacionMasCercana (LinkedList<EstacionBicing> llbicing) {
        int tam = llbicing.size();
        Ubicacion aux = llbicing.get(0);

        float distancia = (float) Math.sqrt((Math.pow(this.getLatitud() - aux.getLatitud(), 2.0) + Math.pow (this.getLongitud() - aux.getLongitud(), 2.0)));

        float menordistancia = distancia;
        int posicionmenordist = 0;

        for (int i = 0; i < tam; i++) {
            aux = llbicing.get(i);
            distancia = (float) Math.sqrt((Math.pow(this.getLatitud() - aux.getLatitud(), 2.0) + Math.pow (this.getLongitud() - aux.getLongitud(), 2.0)));
            if (distancia < menordistancia) {
                menordistancia = distancia;
                posicionmenordist = i;
            }
        }

        return llbicing.get(posicionmenordist);
    }

    public void sumCont (){
        cont = cont + 1;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public float getLatitud() {
        return latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public int getCont() {
        return cont;
    }


}
