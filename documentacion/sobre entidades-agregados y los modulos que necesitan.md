Un modelo de dominio tiene entidades (o agregados de entidades) como su núcleo central.

Por lo general, todas las entidades y agregados suelen tener unas necesidades bastante similares:

1. Su definición interna: la información que contiene (datos)

2. Su definición interna: lo que podemos hacer con ella (comportamiento)

3. Alguna forma para guardarla en el tiempo (persistencia)

4. Alguna forma para transmitirla de un sitio a otro (serialización/deserialización)

5. Alguna forma para que "el mundo exterior" pueda interactuar con ella (interfaz)

6. Alguna forma de coordinar esa interactividad (adaptador)

Y, como la nomenclatura es muy importante. 

Algunas sugerencias de como llamar a los módulos que van a cubrir cada una de esas necesidades. Por ejemplo, para una entidad BB:

1. Se puede usar directamente el nombre por el que se conoce a la entidad en el dominio de aplicación en que estemos trabajando: BB

2. WorkingWithBBs, BBs_workingWith, ManejoDeBBs, BBs,...

3. PersistenceOfBB , BB_persistence, PersistenciaDeBB, BB_persistencia,...

4. Tiene un nombre bastante estandarizado: DTO (Data Transfer Object). Así es que quedaria algo así como BB_dto

5. ViewForBB, BB_view, VistaParaBB, BB_vista,...  (También se suele utilizar el término API en lugar de vista , cuando la interacción va realizarse con otros programas o dispositivos y no con personas humanas.) APIforBB, APIparaBB, BB_API,...

6. ViewAdapterForBB, BB_viewAdapter, AdaptadorDeVistaParaBB, BB_adaptadorVista,...  (También se suele utilizar el término controller o controlador en lugar de adapter o adaptador. Dependiendo si llamamos a la arquitectura "MVC" o "Ports&Adapters") (nota: En el caso de las API, es raro la necesidad de separar entre vista|controlador, así es que suele haber un único módulo: APIforBB, APIparaBB, BB_API,... o si se quiere se le puede llamar APIadapterForBB, AdaptadorDeAPIparaBB, BB_adaptadorAPI,... a ese módulo.)


