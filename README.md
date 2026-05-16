# Product Service

Microservicio reactivo desarrollado con Spring Boot 3.3.x, Spring WebFlux y Spring Data R2DBC. El proyecto sigue los principios de la Arquitectura Hexagonal.

## Stack Tecnológico
* Java 21
* Spring Boot 3.3.0
* Spring WebFlux
* Spring Data R2DBC
* H2 Database
* JUnit 5, Mockito & StepVerifier
* JaCoCo
* Docker & Docker Compose

## Arquitectura
El proyecto usa Arquitectura Hexagonal dividida en 3 capas:
1. Domain: Contiene el modelo de negocio (Product) y los puertos de entrada y salida.
2. Application: Contiene los casos de uso (ProductService).
3. Infrastructure: Contiene los adaptadores web (ProductController) y de persistencia (ProductRepositoryAdapter y base de datos).

## Ejecutar con Docker
Para compilar y levantar la aplicación junto con su entorno, ejecuta en la raíz del proyecto:

```bash
docker-compose up --build
```
La aplicación quedará disponible en el puerto 8080.

## Ejecutar tests y reporte de cobertura
Se implementaron pruebas unitarias y de integración. JaCoCo valida automáticamente que la cobertura en la capa de servicio sea al menos del 70%.

Para ejecutar los tests, usa:

```bash
./mvnw clean verify
```
El reporte detallado se genera en: `target/site/jacoco/index.html`

## Ejemplos cURL

### Crear producto (POST)
```bash
curl -X POST http://localhost:8080/api/products \
-H "Content-Type: application/json" \
-d '{
  "name": "Laptop",
  "description": "Laptop Gamer",
  "price": 1500.00,
  "stock": 10
}'
```

### Obtener todos los productos (GET)
```bash
curl -X GET http://localhost:8080/api/products
```

### Obtener producto por ID (GET)
```bash
curl -X GET http://localhost:8080/api/products/1
```

### Actualizar producto (PUT)
```bash
curl -X PUT http://localhost:8080/api/products/1 \
-H "Content-Type: application/json" \
-d '{
  "name": "Laptop",
  "description": "Laptop Gamer Pro",
  "price": 1600.00,
  "stock": 8
}'
```

### Eliminar producto (DELETE)
```bash
curl -X DELETE http://localhost:8080/api/products/1
```
