# Microservicio Productor de Mensajes

Este microservicio se encarga exclusivamente de la producción de mensajes hacia RabbitMQ.

## Tecnologías
- Spring Boot 3.3.13
- Java 21
- Spring AMQP (RabbitMQ)
- Lombok
- Maven

## Endpoints

### POST /api/mensajes
Envía un mensaje de texto simple a RabbitMQ.

### POST /api/usuarios
Envía un objeto UsuarioDTO a RabbitMQ.

### POST /api/productos
Envía un objeto ProductoDTO a RabbitMQ.

## Puerto
El microservicio corre en el puerto 8081.
