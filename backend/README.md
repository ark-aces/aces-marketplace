# ACES Marketplace API

This application is the API backend for the ACES Marketplace.

## Configuration

### Database

Database configurations can be set in the `application.yml` config file.

Local File database (H2):

```
spring:
  datasource:
    url: "jdbc:h2:/tmp/marketplace.db;DB_CLOSE_ON_EXIT=FALSE;AUTO_RECONNECT=TRUE"
    driver-class-name: "org.h2.Driver"
  jpa:
    hibernate:
      ddl-auto: "update"
```

Local Postgres database using Docker:

```
docker run -d -p 15432:5432 \
--name aces_martketplace_postgres \
-e POSTGRES_PASSWORD=password \
-e POSTGRES_USER=postgres \
-e POSTGRES_DB=aces_marketplace \
postgres:9.6.1
```

```
spring:
  datasource:
    platform: "postgres"
    url: "jdbc:postgresql://localhost:15432/aces_marketplace"
    username: "postgres"
    password: "password"
  jpa:
    hibernate:
      ddl-auto: "validate"
```


## Account Registration

