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

                String datos = correoInstitucional + ":";
                String[] preguntas = {
                    "¿Ha notado dificultades para comprender conceptos técnicos durante las clases o conferencias?", "¿Experimenta problemas para seguir instrucciones orales complejas en el laboratorio?",
                    "¿Le resulta difícil participar en debates o presentaciones grupales debido a la timidez o el miedo a ser juzgado?", "¿Se siente inseguro al comunicar ideas o presentar informes técnicos a sus compañeros o profesores?", "¿Observa que su acento o dialecto regional dificulta la comprensión por parte de sus compañeros o profesores?", "¿Ha tenido dificultades para comprender las instrucciones de seguridad o los procedimientos de emergencia en la facultad?", "¿Le resulta difícil trabajar en equipo debido a problemas de comunicación con sus compañeros?", "¿Ha notado que tartamudea o repite palabras involuntariamente al hablar en clase o en el laboratorio?", "¿Experimenta temblores o rigidez en la boca o la lengua que afectan su capacidad para hablar con fluidez?", "¿Siente que su voz es demasiado débil o temblorosa al dar una presentación o participar en clase?", "¿Le resulta difícil articular correctamente ciertos sonidos o palabras, lo que genera confusión en sus compañeros o profesores?", "¿Ha notado que su velocidad de habla es demasiado rápida o lenta, dificultando la comprensión de su mensaje?", "¿Experimenta fatiga vocal o dolor al hablar durante largos periodos en clase o en el laboratorio?", "¿Ha tenido dificultades para leer en voz alta o entonar correctamente las palabras al presentar un informe o proyecto?", "¿Considera que los problemas de comunicación o trastornos del habla afectan su rendimiento académico en la facultad?", "¿Ha sentido frustración o ansiedad debido a las dificultades para comunicarse en clase o en el laboratorio?", "¿Ha evitado participar en actividades grupales o presentaciones por miedo a ser juzgado por su forma de hablar?", "¿Ha notado que sus problemas de comunicación afectan su capacidad para establecer relaciones con sus compañeros o profesores?", "¿Considera que la facultad ofrece recursos o apoyo adecuado para estudiantes con dificultades de comunicación o trastornos del habla?", "¿Qué medidas o estrategias le gustaría que la facultad implementara para mejorar la experiencia educativa de estudiantes con problemas de comunicación o trastornos del habla?", "¿Ha sido diagnosticado con algún trastorno del habla o lenguaje?", "¿Ha recibido terapia o tratamiento para mejorar su comunicación o habla?", "¿Qué estrategias utiliza para superar las dificultades de comunicación en el ámbito académico?", "¿Qué recomendaciones le daría a otros estudiantes que experimentan problemas similares?"
                };

                // Guardar datos
                consulta.setPreguntas(preguntas);
                consulta.setCorreo(correoInstitucional);

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
                String linea;
                if (consulta.getMensaje() == null) {
                    return;
                }
                try (BufferedReader br = new BufferedReader(new FileReader("recursos.txt"))) {
                    recursos.append("Contamos con los siguientes recursos para ti, ").append(consulta.getCorreo()).append(", espero los tengas en consideración:\n");
                    int cr = 0; // Cantidad de recomendaciones
                    double porcentaje = consulta.getPorcentaje();

                    cr = (porcentaje == 0) ? 1
                            : (porcentaje <= 5) ? 3
                                    : (porcentaje <= 10) ? 4
                                            : (porcentaje <= 15) ? 5
                                                    : (porcentaje <= 20) ? 7
                                                            : (porcentaje <= 25) ? 8
                                                                    : (porcentaje <= 30) ? 10
                                                                            : (porcentaje <= 35) ? 11
                                                                                    : (porcentaje <= 40) ? 12
                                                                                            : (porcentaje <= 45) ? 13
                                                                                                    : (porcentaje <= 50) ? 15
                                                                                                            : (porcentaje <= 55) ? 16
                                                                                                                    : (porcentaje <= 60) ? 17
                                                                                                                            : (porcentaje <= 65) ? 18
                                                                                                                                    : (porcentaje <= 70) ? 20
                                                                                                                                            : (porcentaje <= 75) ? 21
                                                                                                                                                    : (porcentaje <= 80) ? 22
                                                                                                                                                            : (porcentaje <= 85) ? 23
                                                                                                                                                                    : (porcentaje <= 90) ? 25
                                                                                                                                                                            : (porcentaje <= 95) ? 26
                                                                                                                                                                                    : (porcentaje <= 100) ? -2 : cr;

                    while ((linea = br.readLine()) != null && cr != 0) {
                        recursos.append("- ").append(linea).append("\n");
                        cr = (cr == -2) ? cr : cr - 1;
                    }

                    consulta.setResultado(recursos);
                } catch (IOException e) {
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
