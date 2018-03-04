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

## Examples

### Account/User Registration

```
curl -X POST \
  http://localhost:8081/registrations \
  -H 'Content-Type: application/json' \
  -d '{
	"contactEmailAddress": "test@example.com",
	"userName": "Test User",
	"userEmailAddress": "test@example.com",
	"userPassword": "password"
}'
```

```
{
    "id": "cCsW87yjRXqZfYl5cALq",
    "createdAt": "2018-03-03T21:30:47.049Z",
    "account": {
        "id": "Nrb4JgJH64wsP1tOJJCk",
        "contactEmailAddress": "test@example.com",
        "isContactEmailAddressVerified": false,
        "createdAt": "2018-03-03T21:30:47.003Z",
        "users": [
            {
                "id": "SZ0wxXj5c3qPPBLYLFyG",
                "name": "Test User",
                "emailAddress": "test@example.com",
                "isEmailAddressVerified": false
            }
        ]
    }
}
```

### Getting Oauth2 token

Get an oauth2 access token using basic auth header with clientId and secret (configured
in application.yml) and the user id and password in the POST body:

```
curl -X POST \
  http://localhost:8081/oauth/token \
  -H 'Authorization: Basic bWFya2V0cGxhY2U6c2VjcmV0' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'grant_type=password&username=SZ0wxXj5c3qPPBLYLFyG&password=password'
```
```
{
    "access_token": "30f718ad-6fd1-4cfa-a49a-9d883d34d76e",
    "token_type": "bearer",
    "refresh_token": "cd3e7879-b4fa-4ea6-8ed4-810a7919cc38",
    "expires_in": 3599,
    "scope": "read write"
}
```

### Creating an ACES Service

```
curl -X POST \
  http://localhost:8081/account/services \
  -H 'Authorization: Bearer 30f718ad-6fd1-4cfa-a49a-9d883d34d76e' \
  -H 'Content-Type: application/json' \
  -d '{
	"url": "http://34.215.227.239:9191/"
}'
```
```
{
    "service": {
        "id": "OeLwfytoIws5sP1UpTdm",
        "url": "http://34.215.227.239:9191/",
        "name": "Testnet Aces ARK-BTC Channel Service",
        "version": "1.0.0",
        "description": "Testnet ACES ARK to BTC Channel service for value transfers",
        "websiteUrl": "https://arkaces.com",
        "createdAt": "2018-03-03T21:33:01.041Z"
    },
    "createdAt": "2018-03-03T21:33:01.063Z"
}
```