# demo-crud

### Initialize MariaDB

Docker image
```dtd
docker run --detach --name demo-mariadb --env MARIADB_ROOT_PASSWORD=my-secret-pw --port 3307:3306  mariadb:latest
```
Create a demo user and database.
```
CREATE DATABASE demo;
CREATE USER 'demo'@localhost IDENTIFIED BY 'Demo@00001111';
GRANT USAGE ON *.* TO 'demo'@localhost IDENTIFIED BY 'Demo@00001111';
GRANT USAGE ON *.* TO 'demo'@'%' IDENTIFIED BY 'Demo@00001111';
GRANT ALL privileges ON demo.* TO 'demo'@localhost 
GRANT ALL privileges ON demo.* TO 'demo'@'%' 
FLUSH PRIVILEGES;
```

### Install JDK21
1. Download JDK
https://www.oracle.com/java/technologies/downloads/

2. Set JAVA_HOME to your environment variable.

### Install IntellIJ
1. The download link.
https://www.jetbrains.com/idea/download/

2. Please choose the community edition.

### Program entry point
Execute the below entry point's main method.
main/java/com/example/demo/DemoApplication

### Swagger url
After you run the project successfully, you're able to access the swagger via http://localhost:8080/swagger-ui/index.html 

### Postman collection
The collection.json is under the assets folder.

### DB initialization
Table and data will be initialized every time you restart the app.
They will be destroyed automatically after the app is stopped.
