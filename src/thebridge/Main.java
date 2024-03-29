package thebridge;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.core.Runtime;
import jade.wrapper.StaleProxyException;

public class Main {

    static Consulta consulta = new Consulta();

    public static void main(String[] args) {
        try {
            Runtime rt = Runtime.instance();
            Profile p = new ProfileImpl();
            // p.setParameter(Profile.GUI, "true");
            AgentContainer container = rt.createMainContainer(p);

            AgentController agenteRecolector = container.createNewAgent("AgenteRecolector", "thebridge.AgenteRecolectorDeDatos", null);
            agenteRecolector.start();

            AgentController agenteRecomendacion = container.createNewAgent("AgenteRecomendacion", "thebridge.AgenteRecomendacion", null);
            agenteRecomendacion.start();
        } catch (StaleProxyException e) {
        }
    }
}
