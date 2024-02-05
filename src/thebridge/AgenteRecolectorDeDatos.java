package thebridge;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.core.AID;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static thebridge.Main.consulta;

public class AgenteRecolectorDeDatos extends Agent {

    @Override
    protected void setup() {
        addBehaviour(new RecoleccionDeDatosBehaviour());
    }

    private class RecoleccionDeDatosBehaviour extends Behaviour {

        private boolean encuestaCompletada = false;

        @Override
        public void action() {
            if (!encuestaCompletada) {
                String correoInstitucional = JOptionPane.showInputDialog("Ingrese su correo institucional: ");
                if (usuarioYaEncuestado(correoInstitucional)) {
                    JOptionPane.showMessageDialog(null, "Usuario ya encuestado");
                    return;
                }

                consulta.setCorreo(correoInstitucional);
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

                // Enviar solicitud de datos
                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                msg.setContent("Solicitud de datos");
                msg.addReceiver(new AID("AgenteRecomendacion", AID.ISLOCALNAME));
                send(msg);
            }

            // Recibir solicitud de recursos
            ACLMessage msg2 = receive();
            if (msg2 != null && msg2.getContent().equals("Solicitud de recursos")) {
                StringBuilder recursos = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new FileReader("recursos.txt"))) {
                    String linea;
                    recursos.append("Contamos con los siguientes recursos para ti, ").append(consulta.getCorreo()).append(", espero los tengas en consideración:\n");
                    while ((linea = br.readLine()) != null) {
                        recursos.append("- ").append(linea).append("\n");
                    }
                    consulta.setResultado(recursos);
                } catch (IOException e) {
                }

                // Imprimir resultados
                if (consulta.getMensaje() != null) {
                    System.out.println(consulta.getMensaje());
                    System.out.println(consulta.getResultado().toString());
                }
            }
        }

        @Override
        public boolean done() {
            return consulta.getMensaje() != null;
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
