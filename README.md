
# jaipro-authentication

### Generate image
```  
mvn clean package  
docker build -t ${image-name}:${tag} --file ./src/main/devops/Dockerfile ./target  
```  

### 

# Keycloak configuration
You need to set this properties in your app.yml:
```
spring:  
  security:  
    oauth2:  
      resourceserver:  
        jwt:  
          issuer-uri: http://host.docker.internal:30010/auth/realms/eureka
```
Be aware if you are running in a local environment you could have access to the Keycloak login page via either http://localhost dns or a custom dns like config above(http://host.docker.internal...). This is important because according to which URL you've logged-in you going to need to specify that same dns in the `spring.security...jwt.issuer-uri` property