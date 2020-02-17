## Installation

Install package first-time using maven package on `root` folder

```bash
mvn clean install
```

## Run modules

Need to run modules

- route-service
- sales-service

using command

```bash
mvn clean package

# run 1st instance
java -jar target/sales-tools.jar --server.port=4040
# run 2th instance
java -jar target/sales-tools.jar --server.port=4041


# run route-service
java -jar target/route-service.jar
```
