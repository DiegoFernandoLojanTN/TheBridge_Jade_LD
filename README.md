# THE BRIDGE - PROPOSITO
_El objetivo principal de nuestro sistema inteligente, "The Bridge/El Puente", es crear un entorno inclusivo en la Facultad de Energías no Renovables, específicamente dirigido a estudiantes con diversas condiciones de comunicación, se busca proporcionar un "puente" para que estos estudiantes superen barreras comunicativas, participen activamente en el ámbito académico y expresen sus ideas sin restricciones._

# MODELO CONCEPTUAL DEL SISTEMA INTELIGENTE "THE BRIDGE" 🚀
[![Modelo-conceptual-Darwin-Center-1-drawio.png](https://i.postimg.cc/nrX90mJk/Modelo-conceptual-Darwin-Center-1-drawio.png)](https://postimg.cc/kRd55BD6)

# ESTRUCTURA DEL PROYECTO EN JAVA-JADE CON EL IDE NEATBEANS  📦
[![imagen-2024-02-07-102009722.png](https://i.postimg.cc/d0XhD1dZ/imagen-2024-02-07-102009722.png)](https://postimg.cc/p9fW6PsP)

 + ***La clase Consulta***: Sirve como un contenedor para almacenar información relacionada con consultas. Permite guardar datos como el correo electrónico, un porcentaje, preguntas, respuestas, un mensaje y un resultado. También proporciona métodos para acceder y modificar estos datos
 + ***La clase AgenteRecolectordeDatos***: Su función principal es recopilar datos de los usuarios a través de una encuesta interactiva y responder a solicitudes de recursos de otros agentes. El agente solicita al usuario que ingrese su correo electrónico, realiza una encuesta mediante una serie de preguntas con opciones de respuesta "Sí" o "No", y guarda los datos en un archivo de texto. Además, cuando recibe una solicitud de recursos de otro agente, proporciona una lista de recursos disponibles desde un archivo llamado "recursos.txt".
 + ***La clase AgenteRecomendacion***: Este calcula el porcentaje de problemas de comunicación a partir de respuestas de encuestas y solicita recursos a otros agentes. Además, guarda los detalles de las consultas realizadas en archivos de texto.

# REQUERIMIENTOS 📢
1. **Ubicación de archivos TXT**:
  * Asegúrate de que los archivos TXT estén ubicados en la carpeta raíz fuera del programa, esto garantizará que puedan ser accedidos fácilmente por la aplicación.
  [![imagen-2024-02-07-135514601.png](https://i.postimg.cc/vT0S4s44/imagen-2024-02-07-135514601.png)](https://postimg.cc/FkJx6wgv)

2. **Configuración en NetBeans**:
+ Abre el proyecto "The Bridge" en NetBeans 8.2.
+ Verifica que todas las dependencias de JADE estén correctamente configuradas en el proyecto. Esto incluye las bibliotecas JADE necesarias para el desarrollo de agentes.

3. **Implementación de agentes JADE**:
+ Asegúrate de que los agentes en JADE estén configurados y funcionando correctamente. Verifica que las rutas de comunicación entre los agentes estén configuradas correctamente para que puedan intercambiar mensajes.

# EJECUCIÓN DEL PROGRAMA 🔨
 1. Podemos observar cómo se lleva a cabo la ejecución de JADE. En el inicio del proceso, se activa la fase de _recolección de datos_. Durante esta fase, el sistema solicita al usuario ingresar su correo electrónico institucional, siguiendo un proceso similar al SGA/EVA de la Universidad Nacional De Loja. En caso de que el usuario ya haya completado la encuesta previamente, se mostrará un mensaje indicando que el usuario ya ha sido encuestado.
[![imagen-2024-02-07-143625685.png](https://i.postimg.cc/MTLNKwNd/imagen-2024-02-07-143625685.png)](https://postimg.cc/9RGJJ6W7)
[![imagen-2024-02-07-143709817.png](https://i.postimg.cc/3xBKryB1/imagen-2024-02-07-143709817.png)](https://postimg.cc/CRRymx9n)
2. Sea continua el proceso de recolección de datos, donde se presentan 25 preguntas sobre problemas de comunicación. Las respuestas proporcionadas por el usuario se analizarán para diagnosticar y recomendar _recursos_ según sea necesario.
[![imagen-2024-02-07-143804439.png](https://i.postimg.cc/0jpTt3Xn/imagen-2024-02-07-143804439.png)](https://postimg.cc/QKdYC63K)
3. Al terminar la fase de recolección de datos, se emplea un _algoritmo_ para efectuar la recomendación de los recursos disponibles. Se calcula un porcentaje específico, el cual el algoritmo utiliza para hacer recomendaciones adecuadas al usuario.
[![imagen-2024-02-07-143849314.png](https://i.postimg.cc/bvW4q8KL/imagen-2024-02-07-143849314.png)](https://postimg.cc/Ny8d4hGr)
5.  Al concluir todo el proceso, el estudiante/usuario no tiene acceso directo al documento en sí, sino que puede visualizar únicamente las recomendaciones de forma digital. Como parte de este proceso, se crea una carpeta llamada _resultados_ en la raíz del proyecto. Dentro de esta carpeta, se genera un documento para cada usuario, el cual contiene sus respuestas y las recomendaciones correspondientes para un uso futuro.
[![imagen-2024-02-07-143933049.png](https://i.postimg.cc/hvrCQq3k/imagen-2024-02-07-143933049.png)](https://postimg.cc/YLvNTT7R)
   
# ENLACES A DOCUMENTACION:
### REPORTE TECNICÓ: https://drive.google.com/file/d/1gg2jKyYi2nlmnCpnkTFrN3-K3ETw8QlY/view?usp=sharing
### PRESENTACIÓN:

DESARROLLO REALIZADO POR [DIEGOFERNANDOLOJAN](https://diegofernandolojantn.github.io/PortfolioLD/)

