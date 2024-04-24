# demo-crud

### Initialize Postgres

Docker image
```dtd
docker run -it --name demo-postgres -e POSTGRES_PASSWORD=mysecretpassword -p 5432:5432 -d postgres
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
