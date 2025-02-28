# Prueba técnica Nequi - API Franquicias 💻
Este proyecto consiste en desarrollar una **API de gestión de franquicias**, permitiendo manejar una lista de franquicias, cada una con sus sucursales y productos. La API permitirá realizar operaciones CRUD sobre franquicias, sucursales y productos, además de consultas específicas sobre el stock de los productos por sucursal, buscar el producto con mas stock por sucursal para una franquicia puntal etc.
## Estructura del proyecto
```
prueba-practica-backend-nequi/
|--src/
|   |--main/
|         |--java/
|               |--com.appication.prueba/
|                  |--controllers         
|                  |--dtos
|                  |--exceptions
|                  |--mappers
|                  |--models
|                  |--repositories
|                  |--services              
|                  |--validations
|                  |--config
|
|         |--resources/
|            |--application.yml
|
|         |--test/
|               |--java/
|                     |--com.application.prueba/
|                        |--services
|
|--.gitignore
|--Dockerfile
|--mvnw
|--mvnw.cmd
|--pom.xml
|--README.md
```
## Componentes principales
-   `controllers/`: Controladores REST que manejan las solicitudes HTTP.
-   `dtos/`: Objetos de Transferencia de Datos (DTOs) para la comunicación entre capas.
-   `exceptions/`: Manejo centralizado de excepciones.
-   `mappers/`: Componentes encargados de transformar objetos de una capa a otra (por ejemplo, convertir entidades a DTOs o viceversa).
-   `models/`: Clases que representan los objetos de dominio de la aplicación, mapeados a las colecciones de MongoDB.
-   `repositories/`: Interfaces de repositorio para el acceso a los datos.
-   `services/`: Servicios que contienen la lógica de negocio.
-   `validators/`: Clases o componentes encargados de realizar validaciones específicas en los métodos de los servicios.
-   `configs/`: Configuraciones generales de la aplicación.


## Base de datos


 Este proyecto utiliza **MongoDB** como sistema de gestión de bases de datos **NoSQL.** MongoDB es una base de datos orientada a documentos que almacena los datos en formato **BSON** (similar a JSON), lo que permite una gran flexibilidad y escalabilidad. En este caso, se utiliza **MongoDB Atlas** como proveedor de base de datos en la nube.

#### **Estructura de la Base de Datos**

-   **Franquicia**: Contiene el id, el nombre de la franquicia y un listado de sucursales asociadas a ella.
-   **Sucursal**: Cada sucursal tiene su id,  un nombre y un listado de productos disponibles.
-   **Producto**: Cada producto tiene su id, un nombre y una cantidad de stock disponible en esa sucursal.

 <a href='https://postimages.org/' target='_blank'><img src='https://i.postimg.cc/h47GvCqz/Captura-de-pantalla-2024-11-07-131347.png' border='0' alt='Captura-de-pantalla-2024-11-07-131347'/></a>


**Colecciones en MongoDB**

-   `franchises`: Colección que almacena la información de las franquicias.
-   `stores`: Colección que almacena las sucursales asociadas a cada franquicia.
-   `products`: Colección que almacena los productos disponibles en cada sucursal, junto con su cantidad de stock.

**Asociaciones unidireccionales**
-   La asociación entre **Franchise** y **Store** es unidireccional, donde Franchise puede acceder a Store, pero no viceversa.
-   La asociación entre **Store** y **Product** también es unidireccional, donde Store puede acceder a Product, pero no al revés.

**Importante:** En este diseño, los productos están embebidos dentro del documento de cada tienda, lo que significa que no existen como documentos independientes en una colección separada. Esto establece una relación unidireccional e independiente, donde los productos solo pertenecen a una tienda específica 
# Guía de instalación
## Requisitos previos: 

 -  **Java 21 o superior**: Compilación y ejecución del proyecto.
 - **Maven**: Gestión de dependencias y construcción  del proyecto.
 - **Docker 27.2.0 o superior**: Administración de contenedores y servicios en tu máquina local.
 - **MongoDB 2.2.10** o superior (utilizando MongoDB Atlas para la base de datos en la nube).
 
 Si no se tiene algunos de estos programas instalados, se pueden obtener directamente desde su página oficial.
## Paso #1: Clona el repositorio
Primero, clona el repositorio a tu máquina local utilizando git:

    git clone https://github.com/SR-Botero/prueba-tecnica-nequi-franquicias

    
    cd prueba-tecnica-nequi-franquicias

## Paso #2: Configuración de MongoDB Atlas
Este proyecto utiliza MongoDB Atlas como base de datos. Si aún no tienes una cuenta, puedes crear una desde [MongoDB Atlas](https://www.mongodb.com/cloud/atlas).

1. **Crea un cluster** en MongoDB Atlas.

2. Una vez creado el cluster, ahora crea un usuario de acceso a la base de datos con las siguientes credenciales: 
- **Username**: `invitado`
- **Password**: `invitado`
3.  **Obtén la cadena de conexión** desde los drivers de MongoDB Atlas.

4.  **Configura el archivo** `application.yml` en el proyecto para que apunte a tu base de datos de MongoDB Atlas. Ejemplo para mi caso en específico:

<a href='https://postimg.cc/CBZdrhSP' target='_blank'><img src='https://i.postimg.cc/fLCXLyFN/Captura-de-pantalla-2025-02-27-223419.png' border='0' alt='Captura-de-pantalla-2025-02-27-223419'/></a>

- Asegúrate de reemplazar en la propiedad "database", por la base de datos que hayas creado en el cluster de Mongo.
- También reemplaza la URI por la cadena de conexión que te brinde Mongo.		

## Paso #3: Configuración del Dockerfile
En este punto, pasaremos a dockerizar la app, creando un archivo Dockerfile en la raíz del proyecto, tal que así: 

<a href='https://postimages.org/' target='_blank'><img src='https://i.postimg.cc/2yc5S4XN/Captura-de-pantalla-2025-02-27-231616.png' border='0' alt='Captura-de-pantalla-2025-02-27-231616'/></a>

- Para este punto, ya debiste haber generado el jar, usando el comando `mvn clean package`

## Paso #4: Crear la imagen Docker 
Se construye la imagen Docker: 

    docker build -t nombre-de-tu-imagen .
    


## Paso #5: Ejecutar el contenedor
Ahora que está la imagen construida, puedes correr el contenedor en la aplicación con el siguiente comando: 

    docker run -p 8080:8080 nombre-de-tu-imagen    
   
## Paso #6: Verificar que todo esté funcionando

 **Verifica los contenedores en ejecución**, para asegurarte de que el contenedor se está ejecutando correctamente, usa el siguiente comando: 
 
`docker ps`  

 Esto te mostrará los contenedores activos. Asegúrate de que tu contenedor esté corriendo en el puerto 8080
 

    http://localhost:8080

Deberías ver la respuesta de tu aplicación en Spring Boot.

## Paso #7: Ahora que todo está listo, prueba la API
Una vez que hayas confirmado que la aplicación está funcionando, puedes probar los endpoints de la API utilizando herramientas como Postman o cURL para asegurarte de que interactúan correctamente con MongoDB Atlas.


# Buenas prácticas implementadas en el proyecto

## - Arquitectura en capas
La arquitectura en capas es un enfoque de organización del código que divide la aplicación en distintas capas, cada una responsable de un aspecto específico.

**¿En qué beneficia?** Mejorar la separación de responsabilidades, facilitando el mantenimiento y la escalabilidad de la aplicación.

-   `controllers/`
-   `dtos/`
-   `exceptions/`
-   `mappers/`
-   `models/`
-   `repositories/`
-   `services/`
-   `validators/`
-   `configs/`

## - Aplicación principios Clean Code
La aplicación de los principios de **Clean Code** busca escribir código claro, legible y fácil de mantener, siguiendo buenas prácticas de desarrollo.

**¿En qué beneficia?** Mejorar la calidad del código, facilitando su comprensión, mantenimiento y evolución a largo plazo.

    @Service  
    @RequiredArgsConstructor  
    public class ProductServiceImpl implements ProductService {  
      
        private final ProductRepository productRepository;  
      
        private final StoreRepository storeRepository;  
      
      @Override  
      public Mono<Product> findProductById(String productId) {  
      
            ProductValidations.validateProductId(productId);  
      
            return productRepository.findById(productId);  
        }  
      
      @Override  
      public Flux<Product> findAllProducts() {  
      
            return productRepository.findAll();  
        }        
        
## - Uso de Lombok
Lombok es una biblioteca que reduce el código repetitivo al generar automáticamente métodos comunes como getters, setters, constructores, y más.

**¿En qué beneficia?** Mejorar la legibilidad y reducir el código boilerplate en el proyecto, simplificando el desarrollo y mantenimiento.

    @AllArgsConstructor  
    @NoArgsConstructor  
    @Data
        
## - Uso de DTOs
Los DTOs (Data Transfer Objects) son objetos utilizados para transferir datos entre capas de una aplicación, generalmente entre la capa de presentación y la de servicio

**¿En qué beneficia?** Desacoplar las capas de la aplicación y optimizar el paso de datos, asegurando que solo se expongan los campos necesarios.

    @AllArgsConstructor  
    @NoArgsConstructor  
    @Data  
    @Builder
    public class StoreDTO {  
      
        @NotNull(message = "Id cannot be null")  
        private String idDTO;  
      
        @NotBlank(message = "Store name cannot be null")  
        @Size(min = 2, max = 50, message = "Store name must be between 2 and 50 characters")  
        @Indexed(unique = true)  
        private String storeNameDTO;  
      
        @NotNull(message = "Product list cannot be null")  
        @Size(min = 1, message = "There must be at least 1 product in the list")  
        @Valid  
        private List<ProductDTO> productDTOList;  
        }
        
## - Uso de MapStruct
MapStruct es una herramienta para la generación automática de mapeos entre objetos, facilitando la conversión entre DTOs y entidades sin escribir código manual.

**¿En qué beneficia?** Simplificar y optimizar la conversión de datos entre diferentes capas de la aplicación, mejorando la mantenibilidad y reduciendo errores.

    @Mapper  
    public interface ProductMapper {  
      
        ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);  
      
        @Mapping(source = "id", target = "idDTO")  
        @Mapping(source = "productName", target = "productNameDTO")  
        @Mapping(source = "stock", target = "stockDTO")  
        ProductDTO productToProductDTO(Product product);  
      
      @InheritInverseConfiguration  
      Product productDTOToProduct(ProductDTO productDTO);   
      
## - Manejo de excepciones centralizado
El manejo de excepciones centralizado es una estrategia para capturar y gestionar errores de manera consistente en toda la aplicación, utilizando un solo punto de control.

**¿En qué beneficia?** Mejorar la gestión de errores, proporcionando respuestas estandarizadas y asegurando un flujo de control limpio y coherente en toda la aplicación.

    @RestControllerAdvice  
    public class GlobalExceptionHandler {  
      
        private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);  
      
        @ExceptionHandler(NotFoundException.class)  
        public Mono<ResponseEntity<String>> handleNotFoundException(NotFoundException ex) {  
            logger.error("NotFoundException: {}", ex.getMessage());  
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()));  
        }
        
## - Logging Adecuado
El logging adecuado implica registrar información relevante sobre el funcionamiento de la aplicación, como errores, eventos importantes y datos de depuración, para facilitar el monitoreo y la resolución de problemas

**¿En qué beneficia?** Mejorar la visibilidad y el diagnóstico de la aplicación, permitiendo un análisis eficiente de su comportamiento y facilitando la identificación de errores y problemas en producción.

    @RestController  
    @RequestMapping("api/franchises")  
    @RequiredArgsConstructor  
    public class FranchiseController {  
      
        private static final Logger logger = LoggerFactory.getLogger(FranchiseController.class);  
      
        private final FranchiseService franchiseService;  
      
        private final FranchiseMapper franchiseMapper;  
      
      
        @GetMapping("/{franchiseId}")  
        public Mono<ResponseEntity<FranchiseDTO>> getFranchiseById(@PathVariable String franchiseId) {  
            logger.info("Request received to get franchise with ID: {}", franchiseId);  
            return franchiseService.findFranchiseById(franchiseId)  
                    .map(franchise -> {  
                        logger.info("Franchise found: {}", franchiseId);  
                        return ResponseEntity.ok(franchiseMapper.INSTANCE.franchiseToFranchiseDTO(franchise));  
                    })  
                    .defaultIfEmpty(ResponseEntity.notFound().build());  
        }
     
## - Creación de validadores
La creación de validadores en una aplicación consiste en implementar lógica personalizada para verificar que los datos de entrada cumplen con los requisitos esperados antes de ser procesados por la aplicación. 

**¿En qué beneficia?** Mejorar la integridad y calidad de los datos, asegurando que las entradas del usuario o las solicitudes externas sean válidas y consistentes antes de que interactúen con el sistema o la base de datos.

    public static void validateProductId(String productId) {  
      
        if (productId == null || productId.isBlank()) {  
            throw new BadRequestException("Product ID must not be null or empty");  
        }  
    }
      
## - Uso del patrón Builder
El uso del patrón **Builder** permite crear objetos complejos de manera escalonada y controlada, separando la construcción de un objeto de su representación final. Este patrón es útil cuando un objeto tiene muchos atributos o configuraciones opcionales, evitando un constructor con demasiados parámetros.

**¿En qué beneficia?** Facilita la adición de nuevos atributos sin modificar el código cliente, promoviendo la inmutabilidad y seguridad de los objetos.

    Franchise expectedFranchise = Franchise.builder()  
            .id(franchiseId)  
            .franchiseName("Franchise One")  
            .storeList(new ArrayList<>())  
            .build();
