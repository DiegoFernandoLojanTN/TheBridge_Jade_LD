
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AgenteRecomendacion extends Agent {

    @Override
    protected void setup() {
        addBehaviour(new CalcularProblemasComunicacion());
    }

    private class CalcularProblemasComunicacion extends OneShotBehaviour {

        @Override
        public void action() {
            try (BufferedReader br = new BufferedReader(new FileReader("datos.txt"))) {
                String line;
                int totalRespuestas = 0;
                int respuestasPositivas = 0;

                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        totalRespuestas++;
                        int respuesta = Integer.parseInt(parts[1]);
                        if (respuesta == 1) {
                            respuestasPositivas++;
                        }
                    }
                }

                double porcentajeProblemasComunicacion = ((double) respuestasPositivas / totalRespuestas) * 100;

            } catch (IOException e) {
            }
        }
    }
}
