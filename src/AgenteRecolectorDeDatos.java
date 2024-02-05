
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.core.AID;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AgenteRecolectorDeDatos extends Agent {

    @Override
    protected void setup() {
        addBehaviour(new RecoleccionDeDatosBehaviour());
    }

    private class RecoleccionDeDatosBehaviour extends Behaviour {

        private boolean encuestaCompletada = false;
        private String porcentajeProblemasComunicacion;

        @Override
        public void action() {
            if (!encuestaCompletada) {
                String correoInstitucional = JOptionPane.showInputDialog("Ingrese su correo institucional: ");
                if (usuarioYaEncuestado(correoInstitucional)) {
                    JOptionPane.showMessageDialog(null, "Usuario ya encuestado");
                    return;
                }

                String datos = correoInstitucional + ": ";
                String[] preguntas = {
                    "¿Tienes problemas para comprender las explicaciones en clase?",
                    "¿Te cuesta expresarte oralmente frente al grupo?",
                    "¿Sientes que tu pronunciación no es clara?",
                    "¿Te confundes con facilidad al hablar en público?"
                };
                for (int i = 0; i < preguntas.length; i++) {
                    int respuesta = JOptionPane.showConfirmDialog(null, preguntas[i], "Encuesta", JOptionPane.YES_NO_OPTION);
                    datos += (respuesta == JOptionPane.YES_OPTION) ? "1" : "0"; // Guardar 1 si es "Sí" y 0 si es "No"
                    if (i < preguntas.length - 1) {
                        datos += ","; // Agregar coma si no es la última pregunta
                    }
                }

                try (FileWriter writer = new FileWriter("datos.txt", true)) {
                    writer.write(datos + "\n");
                } catch (IOException e) {
                }

                encuestaCompletada = true;

                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                msg.setContent("Solicitud de recursos");
                msg.addReceiver(new AID("AgenteRecomendacion", AID.ISLOCALNAME));
                send(msg);
                
                // Recibir
                ACLMessage msg2 = receive();
                if (msg2 != null && msg2.getPerformative() == ACLMessage.INFORM) {
                    porcentajeProblemasComunicacion = msg2.getContent();

                    System.out.println("msg 2: "+ porcentajeProblemasComunicacion);
                }
                
                StringBuilder recursos = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new FileReader("recursos.txt"))) {
                    String linea;
                    recursos.append("Contamos con los siguientes recursos para ti, ").append(correoInstitucional).append(", espero los tengas en consideración:\n");
                    while ((linea = br.readLine()) != null) {
                        recursos.append("- ").append(linea).append("\n");
                    }
                } catch (IOException e) {
                }

                JOptionPane.showMessageDialog(null, recursos.toString(), "Recursos Disponibles", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        @Override
        public boolean done() {
            return false;
        }

        private boolean usuarioYaEncuestado(String correo) {
            try (BufferedReader reader = new BufferedReader(new FileReader("datos.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith(correo + ":")) {
                        return true; // El usuario ya ha sido encuestado
                    }
                }
            } catch (IOException e) {
            }
            return false; // El usuario no ha sido encuestado
        }
    }
}
