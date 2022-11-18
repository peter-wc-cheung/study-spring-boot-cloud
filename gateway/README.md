# Starting the server

- Pre-requisite components:
  - ServerApplication (Either one)
  - ProviderApplication

## Get Started

### (No Authentication)

1. Access provider via gateway 
```
  curl --location --request GET 'http://localhost:5001/provider/test'
```

### (With Authentication)

#### 1) Access provider via gateway (With Authentication)
```
  curl --location --request GET 'http://localhost:5001/provider-with-auth/test' \
  --header 'x-api-key: api-key'
```
#### 2) Access provider via gateway (Reject as API-KEY invalid)
```
  curl --location --request GET 'http://localhost:5001/provider-with-auth/test' \
  --header 'x-api-key: invalid-api-key'
```
#### 3) Access provider via gateway (Reject as No API-KEY)
```
  curl --location --request GET 'http://localhost:5001/provider-with-auth/test'
```