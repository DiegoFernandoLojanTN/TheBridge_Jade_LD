/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
/**
 *
 * @author Usuario
 */
public class Main {
     public static void main(String[] args) {
        // Configurar el perfil del contenedor
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.GUI, "true"); // Habilitar la interfaz gráfica de JADE

        // Crear el contenedor principal
        AgentContainer mainContainer = jade.core.Runtime.instance().createMainContainer(profile);

        try {
            // Iniciar el AgenteRecopiladorDatos
            AgentController recopiladorController = mainContainer.createNewAgent("AgenteRecopiladorDatos", AgenteRecopiladorDatos.class.getName(), new Object[]{});
            recopiladorController.start();

            // Iniciar el AgenteTraduccion
            AgentController traduccionController = mainContainer.createNewAgent("AgenteTraduccion", AgenteTraduccion.class.getName(), new Object[]{});
            traduccionController.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }
}
