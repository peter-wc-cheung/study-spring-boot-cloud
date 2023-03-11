### How to start

1. Follow Quick Start for Confluent Platform to start Confluent in local [[Click me]](https://docs.confluent.io/platform/current/platform-quickstart.html#prerequisites)
2. `docker cp elastic-es01-1:/usr/share/elasticsearch/config/certs/es01/es01.crt . `
3. `openssl x509 -fingerprint -sha256 -noout -in ./es01.crt`