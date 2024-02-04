import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.core.Runtime;

public class Main {

    public static void main(String[] args) {
        try {
            Runtime rt = Runtime.instance();
            Profile p = new ProfileImpl();
            AgentContainer container = rt.createMainContainer(p);

            AgentController agenteRecolector = container.createNewAgent("AgenteRecolector", "AgenteRecolectorDeDatos", null);
            agenteRecolector.start();

            AgentController agenteRecomendacion = container.createNewAgent("AgenteRecomendacion", "AgenteRecomendacion", null);
            agenteRecomendacion.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
