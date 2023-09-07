## Los apartados a considerar al definir la arquitectura de una aplicación

Un modelo de dominio tiene entidades (o agregados de entidades) como su núcleo central. Y, por lo general, todas ellas suelen tener unas necesidades bastante similares:

1. La información que contiene -> *modelo de datos*

2. Lo que podemos hacer con ella -> *modelo de comportamiento*

3. Alguna forma para guardarla en el tiempo -> *persistencia*

4. Alguna forma para transportar su información de un sitio a otro -> *serialización/deserialización*

5. Alguna forma para que "el mundo exterior" pueda interactuar con ella -> *interfaz*

6. Alguna forma de coordinar esa interactividad entre el modelo y el interfaz -> *adaptador o controlador* 


## Algunas sugerencias de nomenclatura

¿Cómo llamar a los módulos o clases que van a cubrir cada una de esas necesidades?. 

Por ejemplo, para una entidad XXXXX:

1. Clase para modelar datos: se puede usar directamente el nombre por el que se conoce a la entidad en el dominio de aplicación en que estemos trabajando -> XXXXX

2. Clase para modelar comportamiento -> WorkingWithXXXXXs, XXXXXs_workingWith, ManejoDeXXXXXs, XXXXXs,...

3. Clase para guardar/recuperar -> PersistenceOfXXXXX , XXXXX_persistence, PersistenciaDeXXXXX, XXXXX_persistencia,...

4. Clase para el transporte datos de un sitio a otro: tiene un nombre bastante estandarizado -> DTO (Data Transfer Object); quedaria algo así como XXXXX_dto

5. Clase para el interfaz -> ViewForXXXXX, XXXXX_view, VistaParaXXXXX, XXXXX_vista,...  (También se suele utilizar el término API en lugar de vista , cuando la interacción va realizarse con otros programas o dispositivos y no con personas humanas.)

6. Clase para la coordinación entre el interfaz y el modelo -> ViewAdapterForXXXXX, XXXXX_viewAdapter, AdaptadorDeVistaParaXXXXX, XXXXX_adaptadorVista,...  (También se suele utilizar el término controller o controlador en lugar de adapter o adaptador; dependiendo si llamamos a la arquitectura "MVC" o "Ports&Adapters" -> ViewControllerForXXXXX, XXXXX_viewController, ControladorDeVistaParaXXXXX, XXXXX_controladorVista,...) (nota: En el caso de las API, es raro la necesidad de separar entre vista|controlador, así es que suele haber un único módulo -> APIforXXXXX, APIparaXXXXX, XXXXX_API,..., APIcontrollerForXXXXX, ControladorAPIparaXXXXX, XXXXX_API_controller,...)

Una muestra: https://github.com/JuanMuruaOlalde/Hotel/blob/main/documentacion/prototipo%20preliminar%20de%20nomenclarura%20y%20organizacion%20del%20codigo.png


## En el framework Spring Boot de Java

El propio framework provee las etiquetas para cada apartado:

1. Clase para modelar datos: @Entity   (Nota: estas clases, en Java, se conocen como POJOs)

2. Clase para modelar comportamiento: @Service

3. Clase para guardar/recuperar: @Repository

4. Clase para el transporte datos de un sitio a otro o de una capa a otra: como se ha dicho, se suelen denominar DTOs (Data Transfer Object); el proceso de transformar de Entity a DTO se suele denominar serializar, y el de DTO a Entity deserializar.
 
5. Clase para el interfaz: normalmente suelen ser páginas HTML/CSS/Javascript; generadas con un motor de plantillas ("template engine"), como por ejemplo Thymeleaf, JSP, FreeMarker, Groovy, etc.

6. Clase para la coordinación entre el interfaz y el modelo: @Controller

Una muestra: https://github.com/JuanMuruaOlalde/Albaranes

