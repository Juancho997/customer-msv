# Customer Microservice

Microservicio Java, creado con Spring Boot, que se encarga de almacenar, modificar y eliminar usuarios en una base de datos MySQL.

Cada vez que se realiza una de estas acciones, se emite un evento ("CREATED", "UPDATED" o "DELETED") a un cluster de servidores de [Cloud Karafka](https://www.cloudkarafka.com), un servicio en la nube de Kafka, el cual recibe el evento y lo envía al microservicio JavaScript [Archivist](https://github.com/Juancho997/archivist-msv), creado con NestJS, que consume dicho evento y lo almacena automátiamente en una base de datos MongoDB.



## Esquema de los microservicios
![Microservices Schema](https://user-images.githubusercontent.com/89111705/200440061-6c7fb5a4-8db4-479d-9f3e-e454ff649c5c.png)


### Instrucciones

1º Crear instancia de CloudKarafka -> pueden hacerlo siguiendo este [video](https://www.youtube.com/watch?v=3IKDPa3VaAE&ab_channel=BHIMRAJYADAV)

2º Tomar los datos de la instancia de Cloud Karafka, y añadirlos al archivo "application.properties" en customer-msv/src/main/resources/ -> pueden guiarse del ejemplo provisto por CloudKarafka [aquí](https://github.com/CloudKarafka/springboot-kafka-example)

3º Crear una base de datos en MySQL (pueden usar el [Workbench](https://www.mysql.com/products/workbench/))

4º Añadir los datos de conexión al archivo "application.properties" en customer-msv/src/main/resources/ (líneas 23 a 26)

5º Correr el proyecto, verificar que todas las conexiones sean correctas y utilizar Postman para hacer peticiones a localhost:8080/api/v1/customers
