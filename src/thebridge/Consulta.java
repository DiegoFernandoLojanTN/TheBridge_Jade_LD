package thebridge;

public class Consulta {

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
     * @return the preguntas
     */
    public String[] getPreguntas() {
        return preguntas;
    }

    /**
     * @param preguntas the preguntas to set
     */
    public void setPreguntas(String[] preguntas) {
        this.preguntas = preguntas;
    }

    /**
     * @return the respuestas
     */
    public String[] getRespuestas() {
        return respuestas;
    }

    /**
     * @param respuestas the respuestas to set
     */
    public void setRespuestas(String[] respuestas) {
        this.respuestas = respuestas;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
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
    private String[] preguntas;
    private String[] respuestas;
    private String mensaje;
    private StringBuilder resultado;
}
