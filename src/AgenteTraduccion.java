/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author Usuario
 */
public class AgenteTraduccion extends Agent {

    @Override
    protected void setup() {
        addBehaviour(new TraduccionRecomendacionBehaviour());
    }

    private class TraduccionRecomendacionBehaviour extends CyclicBehaviour {

        @Override
        public void action() {
            // Recibir datos del AgenteRecopiladorDatos
            ACLMessage mensaje = receive();
            if (mensaje != null) {
                String[] datos = mensaje.getContent().split(",");
                String nombre = datos[0];
                double porcentajeFallas = Double.parseDouble(datos[1]);

                // Realizar recomendaciones según el porcentaje de fallas y tipo de problema
                String recomendacion = obtenerRecomendacion(porcentajeFallas);

                // Mostrar mensajes con colores
                System.out.println("\u001B[36m--------------------------------------------------\u001B[0m");
                System.out.println("\u001B[36m        |     |\u001B[0m");
                System.out.println("\u001B[36m        |     |\u001B[0m");
                System.out.println("\u001B[36m        |     |\u001B[0m");
                System.out.println("\u001B[36m        |     |\u001B[0m");
                System.out.println("\u001B[36m-----|\u001B[34mHola soy The Bridge\u001B[36m|-----\u001B[0m");
                System.out.println("\u001B[36m        |     |\u001B[0m");
                System.out.println("\u001B[36m-------------------------------------------------------\u001B[0m");
                System.out.println("\u001B[33mSoy un asistente inteligente que te ayudará a saber tus condiciones comunicativas.\u001B[0m");
                System.out.println("\nRecomendación para " + nombre + ":");
                System.out.println(recomendacion);
            } else {
                block();
            }
        }

        private String obtenerRecomendacion(double porcentajeFallas) {
            StringBuilder recomendacion = new StringBuilder("\u001B[34mSegún los resultados de tus respuestas, eres propenso o tienes un alto porcentaje de tener:\u001B[0m");

            // Determinar el tipo de trastorno comunicativo
            String tipoTrastorno = "";
            if (porcentajeFallas > 30) {
                tipoTrastorno = "Disartria";
            } else if (porcentajeFallas > 25) {
                tipoTrastorno = "Trastorno del lenguaje expresivo";
            } else if (porcentajeFallas > 20) {
                tipoTrastorno = "Trastorno del lenguaje receptivo";
            } else if (porcentajeFallas > 15) {
                tipoTrastorno = "Tartamudeo (disfemia)";
            } else if (porcentajeFallas > 10) {
                tipoTrastorno = "Trastorno fonológico";
            } else {
                tipoTrastorno = "Ningún trastorno significativo";
            }

            // Agregar información sobre el tipo de trastorno
            recomendacion.append("\n- \u001B[35m").append(tipoTrastorno).append(" ").append((int) porcentajeFallas).append("%: \u001B[0m");

            // Agregar descripciones específicas de cada trastorno
            switch (tipoTrastorno) {
                case "Disartria":
                    recomendacion.append("\n\u001B[31mLa disartria es un trastorno neuromuscular que afecta la claridad en la articulación del habla debido a daño en el sistema nervioso central o periférico, provocando dificultades en la pronunciación y el control vocal.\u001B[0m");
                    break;
                case "Trastorno del lenguaje expresivo":
                    recomendacion.append("\n\u001B[31mEste trastorno afecta la capacidad para expresar ideas de manera clara y efectiva.\u001B[0m");
                    break;
                case "Trastorno del lenguaje receptivo":
                    recomendacion.append("\n\u001B[31mPodrías tener dificultades para entender lo que te dicen.\u001B[0m");
                    break;
                case "Tartamudeo (disfemia)":
                    recomendacion.append("\n\u001B[31mEl tartamudeo se caracteriza por interrupciones en el flujo del habla, afectando la fluidez verbal.\u001B[0m");
                    break;
                case "Trastorno fonológico":
                    recomendacion.append("\n\u001B[31mEste trastorno puede afectar la correcta pronunciación de palabras y sonidos.\u001B[0m");
                    break;
                case "Ningún trastorno significativo":
                    recomendacion.append("\n\u001B[32mNo se identificaron problemas significativos en tus respuestas. ¡Bien hecho!\u001B[0m");
                    break;
                // Puedes agregar más casos según sea necesario.
                default:
                    break;
            }

            // Mensajes adicionales según el porcentaje de fallas
            if (porcentajeFallas > 30) {
                recomendacion.append("\n\nLamento estos resultados, te puedo recomendar:");
                recomendacion.append("\n- Consultar con el especialista en lenguaje de la Facultad para una evaluación detallada.");
                recomendacion.append("\n- Participar en terapia del habla ofrecida por la Facultad para mejorar la claridad en la articulación.");
                recomendacion.append("\n- Explorar opciones de apoyo académico proporcionadas por la Facultad.");
            } else if (porcentajeFallas > 25) {
                recomendacion.append("\n\nTe sugiero tomar medidas para mejorar tu expresión verbal:");
                recomendacion.append("\n- Practicar ejercicios de expresión oral y presentación en público.");
                recomendacion.append("\n- Comunicarte con profesores para discutir posibles adaptaciones en las evaluaciones.");
                recomendacion.append("\n- Explorar talleres de comunicación y presentación de la Facultad.");
            } else if (porcentajeFallas > 20) {
                recomendacion.append("\n\nAunque no se identificaron problemas significativos, aún puedes trabajar en mejorar tu comunicación:");
                recomendacion.append("\n- Participar en actividades extracurriculares que fomenten la comunicación interpersonal.");
                recomendacion.append("\n- Tomar cursos de comunicación efectiva ofrecidos por la Facultad.");
                recomendacion.append("\n- Explorar opciones para mejorar la autoconfianza en la comunicación.");
            } else {
                recomendacion.append("\n\nNo se identificaron problemas significativos en tus respuestas. ¡Bien hecho!");
                recomendacion.append("\nConsidera seguir practicando la escucha activa y mejorando tus habilidades de comunicación para un desarrollo continuo.");
            }

            return recomendacion.toString();
        }
    }
}
