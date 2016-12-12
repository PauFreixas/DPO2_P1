import com.google.gson.JsonObject;

import java.util.LinkedList;

/**
 * Created by Usuario on 12/12/2016.
 */
public class EstacionBicing extends Ubicacion {
    private int id;
    private String tipo;
    private int slots;
    private int bikes;
    private int[] id_estaciones;
    private LinkedList<EstacionBicing> estaciones;
    private String estacionescercanas;

    public EstacionBicing () {}

    public EstacionBicing (JsonObject estacion){

        super(estacion.get("latitude").getAsFloat(), estacion.get("longitude").getAsFloat(), estacion.get("altitude").getAsFloat(), estacion.get("streetName").getAsString(), estacion.get("streetNumber").getAsString());
        String sestaciones= estacion.get("nearbyStations").getAsString();
        id = estacion.get("id").getAsInt();
        tipo = estacion.get("type").getAsString();
        slots = estacion.get("slots").getAsInt();
        bikes = estacion.get("bikes").getAsInt();
        id_estaciones = new int[sestaciones.length()];
        String aux[];
        aux = sestaciones.split(", ");
        for (int i = 0; i < aux.length ; i++) {
            id_estaciones[i] = Integer.parseInt(aux[i]);
        }
    }
    public void assignaestacio (LinkedList<EstacionBicing> bicing){
        estaciones = new LinkedList<>();
        for (int i = 0; i < id_estaciones.length; i++){
            for (int j = 0; j < bicing.size(); j++){
                if (id_estaciones[i] == bicing.get(j).getId()){
                    EstacionBicing eb = bicing.get(j);
                    estaciones.add(eb);
                }
            }
        }
    }

    public LinkedList<EstacionBicing> getEstaciones() {
        return estaciones;
    }

    public void setEstaciones(LinkedList<EstacionBicing> estaciones) {
        this.estaciones = estaciones;
    }

    public int getSlots() {
        return slots;
    }

    public int getBikes() {
        return bikes;
    }

    public int getId (){
        return id;
    }
}