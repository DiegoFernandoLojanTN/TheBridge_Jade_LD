/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class AgenteRecopiladorDatos extends Agent {

    @Override
    protected void setup() {
        addBehaviour(new RecopilacionComunicacionBehaviour());
    }

    private class RecopilacionComunicacionBehaviour extends CyclicBehaviour {

        @Override
        public void action() {
            // Ventanas de JOptionPane para preguntas cerradas
            String nombre = JOptionPane.showInputDialog("Ingrese su nombre:");
            int respuestasPositivas = 0;

            respuestasPositivas += JOptionPane.showConfirmDialog(null, "¿Tiene dificultades para expresar sus ideas de manera clara y efectiva?", "Pregunta 1", JOptionPane.YES_NO_OPTION);
            respuestasPositivas += JOptionPane.showConfirmDialog(null, "¿Le cuesta entender lo que le dicen, especialmente en entornos académicos?", "Pregunta 2", JOptionPane.YES_NO_OPTION);
            respuestasPositivas += JOptionPane.showConfirmDialog(null, "¿Suele experimentar interrupciones en el flujo del habla (tartamudeo)?", "Pregunta 3", JOptionPane.YES_NO_OPTION);
            respuestasPositivas += JOptionPane.showConfirmDialog(null, "¿Tiene dificultades para pronunciar ciertas palabras o sonidos correctamente?", "Pregunta 4", JOptionPane.YES_NO_OPTION);
            respuestasPositivas += JOptionPane.showConfirmDialog(null, "¿Experimenta ansiedad al comunicarse en público o durante presentaciones?", "Pregunta 5", JOptionPane.YES_NO_OPTION);
            respuestasPositivas += JOptionPane.showConfirmDialog(null, "¿Siente que su capacidad para expresarse verbalmente se ve afectada por condiciones médicas?", "Pregunta 6", JOptionPane.YES_NO_OPTION);
            respuestasPositivas += JOptionPane.showConfirmDialog(null, "¿Prefiere la comunicación escrita sobre la verbal en situaciones académicas?", "Pregunta 7", JOptionPane.YES_NO_OPTION);
            respuestasPositivas += JOptionPane.showConfirmDialog(null, "¿Ha recibido retroalimentación negativa sobre su comunicación en el entorno académico?", "Pregunta 8", JOptionPane.YES_NO_OPTION);
            respuestasPositivas += JOptionPane.showConfirmDialog(null, "¿Suele tener dificultades para comprender las instrucciones verbales dadas en clases?", "Pregunta 9", JOptionPane.YES_NO_OPTION);
            respuestasPositivas += JOptionPane.showConfirmDialog(null, "¿Encuentra que su voz no refleja adecuadamente su estado emocional durante la comunicación?", "Pregunta 10", JOptionPane.YES_NO_OPTION);
            respuestasPositivas += JOptionPane.showConfirmDialog(null, "¿Experimenta dificultades para adaptar su estilo de comunicación según la audiencia en contextos académicos?", "Pregunta 11", JOptionPane.YES_NO_OPTION);
            respuestasPositivas += JOptionPane.showConfirmDialog(null, "¿Suele utilizar muletillas o palabras vacías durante la comunicación formal en la universidad?", "Pregunta 12", JOptionPane.YES_NO_OPTION);
            respuestasPositivas += JOptionPane.showConfirmDialog(null, "¿Le resulta incómodo el contacto visual durante presentaciones en la universidad?", "Pregunta 13", JOptionPane.YES_NO_OPTION);
            respuestasPositivas += JOptionPane.showConfirmDialog(null, "¿Ha buscado apoyo para mejorar sus habilidades de comunicación en la universidad?", "Pregunta 14", JOptionPane.YES_NO_OPTION);
            respuestasPositivas += JOptionPane.showConfirmDialog(null, "¿Considera que sus habilidades de comunicación impactan negativamente en su rendimiento académico?", "Pregunta 15", JOptionPane.YES_NO_OPTION);

            // Calcular el porcentaje de fallas en la comunicación
            double porcentajeFallas = (respuestasPositivas / 15.0) * 100;

            // Enviar datos al AgenteTraduccion
            ACLMessage mensaje = new ACLMessage(ACLMessage.INFORM);
            mensaje.setContent(nombre + "," + porcentajeFallas);
            mensaje.addReceiver(getAID("AgenteTraduccion"));
            send(mensaje);

            // Finalizar el agente después de enviar el mensaje
            doDelete();
        }
    }
}
