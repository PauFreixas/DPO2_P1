import com.google.gson.JsonObject;

import java.util.LinkedList;

/**
 * Created by Usuario on 12/12/2016.
 */
public class Ruta {
    private String ssalida;
    private String sllegada;
    private Ubicacion usalida;
    private Ubicacion ullegada;
    private EstacionBicing ebsalida;
    private EstacionBicing ebllegada;
    private int tiempo;
    private int distacia;

    public Ruta (){}

    public Ruta (int i){
        distacia = i;
    }

    public boolean iniciarRuta (String ssalida, String sllegada, LinkedList<EstacionBicing> llestacion){
        LecturaJson lj = new LecturaJson();
        WebService ws = new WebService();

        JsonObject aux = lj.stringToUbicacion(ws.getSiteUrl(ssalida));

        if (!lj.existeubicacion()){
            return false;
        }

        usalida = new Ubicacion(aux);

        aux = lj.stringToUbicacion(ws.getSiteUrl(sllegada));

        if (!lj.existeubicacion()){
            return false;
        }
        ullegada = new Ubicacion(aux);

        ebsalida = usalida.calcularEstacionMasCercana(llestacion);
        ebllegada = ullegada.calcularEstacionMasCercana(llestacion);

        return true;
    }

    public void comprobarEBSalida (){
        while (ebsalida.getBikes() < 1){
            ebsalida = ebsalida.getEstaciones().get(0);
        }
    }

    public Ubicacion getUsalida() {
        return usalida;
    }

    public Ubicacion getUllegada() {
        return ullegada;
    }

    public EstacionBicing getEbsalida() {
        return ebsalida;
    }

    public EstacionBicing getEbllegada() {
        return ebllegada;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public int getDistacia() {
        return distacia;
    }

    public void setDistacia(int distacia) {
        this.distacia = distacia;
    }

    public String getSsalida() {
        return ssalida;
    }

    public void setSsalida(String ssalida) {
        this.ssalida = ssalida;
    }

    public String getSllegada() {
        return sllegada;
    }

    public void setSllegada(String sllegada) {
        this.sllegada = sllegada;
    }
}
