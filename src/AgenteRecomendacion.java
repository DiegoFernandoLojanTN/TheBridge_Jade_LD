
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

                // Recibir
                ACLMessage msg = receive();
                if (msg != null && msg.getContent().equals("Solicitud de recursos")) {
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

                    porcentajeProblemasComunicacion = (respuestasPositivas / totalRespuestas) * 100;

                    System.out.println("msg: " + porcentajeProblemasComunicacion);

                    // Enviar porcentajeProblemasComunicacion
                    ACLMessage msg2 = new ACLMessage(ACLMessage.INFORM);
                    msg2.setContent(String.valueOf(porcentajeProblemasComunicacion));
                    msg2.addReceiver(new AID("AgenteRecolectorDeDatos", AID.ISLOCALNAME));
                    send(msg2);
                }
            } catch (IOException e) {
            }
        }

        @Override
        public boolean done() {
            return false;
        }
    }
}
