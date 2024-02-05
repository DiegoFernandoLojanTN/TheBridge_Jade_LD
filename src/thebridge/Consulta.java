package thebridge;

public class Consulta {

    /**
     * @return the porcentaje
     */
    public double getPorcentaje() {
        return porcentaje;
    }

    /**
     * @param porcentaje the porcentaje to set
     */
    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param porcentaje the mensaje to set
     */
    public void setMensaje(String porcentaje) {
        this.mensaje = porcentaje;
    }

    /**
     * @return the resultado
     */
    public StringBuilder getResultado() {
        return resultado;
    }

    /**
     * @param resultado the resultado to set
     */
    public void setResultado(StringBuilder resultado) {
        this.resultado = resultado;
    }

    private String correo;
    private double porcentaje;
    private String mensaje;
    private StringBuilder resultado;
}
