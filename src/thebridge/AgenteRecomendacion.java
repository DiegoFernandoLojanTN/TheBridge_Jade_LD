package thebridge;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import javax.swing.JOptionPane;
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
                        consulta.setRespuestas(valores);
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

            // Mostrar resultados y guardar consulta
            if (consulta.getMensaje() != null && consulta.getResultado() != null) {
                String resultados = consulta.getMensaje() + "\n" + consulta.getResultado();
                System.out.println(resultados);

                // Mostrar mensaje en una ventana emergente
                JOptionPane.showMessageDialog(null, resultados, "Resultados", JOptionPane.INFORMATION_MESSAGE);

                // Guardar consulta
                guardarConsulta();
            }
        }

        @Override
        public boolean done() {
            return consulta.getResultado() != null;
        }

        private void guardarConsulta() {
            try {
                String nombreArchivo = "consulta_" + consulta.getCorreo() + "_"
                        + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss")) + ".txt";

                File directorio = new File("resultados");
                directorio.mkdirs(); // Asegurarse de que el directorio existe

                File archivo = new File(directorio, nombreArchivo);

                StringBuilder preguntas = new StringBuilder();

                for (int i = 0; i < consulta.getPreguntas().length; i++) {
                    // Formatear la pregunta
                    preguntas.append(i + 1).append(". ").append(consulta.getPreguntas()[i]).append("\n");

                    // Formatear la respuesta (considerando que es 0 o 1)
                    String respuestaFormateada = (consulta.getRespuestas()[i].equals("1")) ? "Si" : "No";
                    preguntas.append(respuestaFormateada).append("\n");
                }

                try (FileWriter writer = new FileWriter(archivo, true)) {
                    writer.write("----------------------------------------------------------------\nUsuario: " + consulta.getCorreo() + "\n----------------------------------------------------------------\n"
                            + "Preguntas:\n----------------------------------------------------------------\n" + preguntas + "----------------------------------------------------------------\n"
                            + "Resultados:\n----------------------------------------------------------------\n" + consulta.getMensaje() + "\n"
                            + consulta.getResultado()
                    );
                }
            } catch (IOException e) {
            }
        }
    }
}
