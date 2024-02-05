package thebridge;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import static thebridge.Main.consulta;

public class AgenteRecomendacion extends Agent {

    @Override
    protected void setup() {
        addBehaviour(new CalcularProblemasComunicacion());
    }

    private class CalcularProblemasComunicacion extends Behaviour {

        @Override
        public void action() {
            try (BufferedReader br = new BufferedReader(new FileReader("datos.txt"))) {
                String line;
                int totalRespuestas = 0;
                int respuestasPositivas = 0;
                double porcentajeProblemasComunicacion = 0;

                // Recibir solicitud de datos
                ACLMessage msg = receive();
                if (msg != null && msg.getContent().equals("Solicitud de datos")) {
                    // Algoritmo
                    while ((line = br.readLine()) != null) {
                        String[] parts = line.split(":");
                        if (parts.length == 2) {
                            totalRespuestas++;
                            String[] respuestas = parts[1].split(",");
                            for (String respuesta : respuestas) {
                                int valor = Integer.parseInt(respuesta.trim());
                                if (valor == 1) {
                                    respuestasPositivas++;
                                }
                            }
                        }
                    }

                    porcentajeProblemasComunicacion = ((double) respuestasPositivas / totalRespuestas) * 100;

                    // Crear el mensaje descriptivo
                    String mensajeDescriptivo = String.format("Hemos detectado un %.2f%% de problemas de comunicación. Lamentamos saber eso, ahora te ayudamos...", porcentajeProblemasComunicacion);

                    // Guardar datos
                    consulta.setPorcentaje(porcentajeProblemasComunicacion);
                    consulta.setMensaje(mensajeDescriptivo);
                }
            } catch (IOException e) {
            }

            // Enviar solicitud de recursos
            ACLMessage msg2 = new ACLMessage(ACLMessage.REQUEST);
            msg2.setContent("Solicitud de recursos");
            msg2.addReceiver(new AID("AgenteRecolector", AID.ISLOCALNAME));
            send(msg2);
        }

        @Override
        public boolean done() {
            return false;
        }
    }
}
