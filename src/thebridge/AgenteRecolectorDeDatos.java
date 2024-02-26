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
                    double porcentaje = consulta.getPorcentaje();

                    // Predecir la cantidad de recursos necesarios utilizando KNN
                    int cr = predecirRecursos(porcentaje);

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

        /*En este ejemplo, supongamos que queremos predecir la cantidad de recursos necesarios basados en un porcentaje dado. 
        Por ejemplo, si tenemos un porcentaje de completitud de tarea del 70%, queremos predecir cuántos recursos se necesitarán.*/
        
        // Función para predecir la cantidad de recursos utilizando el algoritmo de los k vecinos más cercanos (KNN)
        private int predecirRecursos(double porcentaje) {
            // Definición de los porcentajes conocidos y los recursos correspondientes
            double[] porcentajesConocidos = {0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100};
            int[] recursosCorrespondientes = {1, 3, 4, 5, 7, 8, 10, 11, 12, 13, 15, 16, 17, 18, 20, 21, 22, 23, 25, 26, -2};

            int k = 3; // Número de vecinos más cercanos a considerar

            // Calcular las distancias entre el porcentaje dado y los porcentajes conocidos
            int[] distancias = new int[porcentajesConocidos.length];
            for (int i = 0; i < porcentajesConocidos.length; i++) {
                // Calcula la diferencia absoluta entre el porcentaje dado y el porcentaje conocido actual
                distancias[i] = Math.abs((int) porcentaje - (int) porcentajesConocidos[i]);
            }

            // Encontrar los k vecinos más cercanos
            int[] recursosVecinos = new int[k];
            // Itera k veces para encontrar los k vecinos más cercanos
            for (int i = 0; i < k; i++) {
                // Inicializa la distancia mínima como el valor máximo entero posible
                int minDistancia = Integer.MAX_VALUE;
                // Inicializa el índice del vecino más cercano como -1
                int minIndex = -1;

                // Itera sobre todas las distancias almacenadas en el arreglo
                for (int j = 0; j < distancias.length; j++) {
                    // Comprueba si la distancia actual es menor que la distancia mínima encontrada hasta ahora
                    if (distancias[j] < minDistancia) {
                        // Si es así, actualiza la distancia mínima y el índice correspondiente
                        minDistancia = distancias[j];
                        minIndex = j;
                    }
                }
                // Almacenar el recurso correspondiente al vecino más cercano en el arreglo de recursos vecinos
                recursosVecinos[i] = recursosCorrespondientes[minIndex];
                distancias[minIndex] = Integer.MAX_VALUE; // Marcar como visitado para no ser seleccionado nuevamente
            }

            // Calcular la decisión basada en la mayoría de clases de los vecinos más cercanos
            int sum = 0;
            // Sumar los recursos de los vecinos más cercanos
            for (int i : recursosVecinos) {
                // Agrega el recurso del vecino actual a la suma total
                sum += i;
            }
            return sum / k; // Devolver el promedio de recursos de los vecinos más cercanos como la predicción final
        }
    }
}
