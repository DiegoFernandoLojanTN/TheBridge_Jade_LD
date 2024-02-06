package thebridge;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
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
                double ppc;

                // Recibir solicitud de datos
                ACLMessage msg = receive();
                if (msg != null && msg.getContent().equals("Solicitud de datos")) {
                    // Algoritmo
                    String ultimaLinea = null;
                    while ((line = br.readLine()) != null) {
                        ultimaLinea = line; // Guardar la última línea
                    }

                    if (ultimaLinea != null) {
                        String[] valores = ultimaLinea.split(":")[1].split(",");
                        System.out.println("Respuestas: " + Arrays.toString(valores));
                        consulta.setRespuestas(valores);
                        totalRespuestas = valores.length;

                        for (String valor : valores) {
                            if (valor.trim().equals("1")) {
                                respuestasPositivas++;
                            }
                        }

                        ppc = ((double) respuestasPositivas / totalRespuestas) * 100; // Porcentaje de problemas de comunicación

                        // Crear el mensaje descriptivo
                        String mensajeDescriptivo = String.format((ppc == 0) ? "Hemos detectado un %.2f%% de problemas de comunicación. Todo parece estar correcto..." : "Hemos detectado un %.2f%% de problemas de comunicación. Lamentamos saber eso, ahora te ayudamos...", ppc);

                        // Guardar datos
                        consulta.setPorcentaje(ppc);
                        consulta.setMensaje(mensajeDescriptivo);
                    }
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
            return consulta.getMensaje() != null;
        }
    }
}
