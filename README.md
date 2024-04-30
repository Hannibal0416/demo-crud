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

### google java format scheme
https://github.com/google/styleguide/blob/gh-pages/intellij-java-google-style.xml


### spotless gradle plugin
https://github.com/diffplug/spotless/blob/main/plugin-gradle/README.md

### Google Java Format
https://github.com/sherter/google-java-format-gradle-plugin

The google-java-format plugin uses some internal classes that aren't available without extra configuration. 

To use the plugin, go to Help→Edit Custom VM Options... and paste in these lines:

```dtd
--add-exports=jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED
--add-exports=jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED
--add-exports=jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED
--add-exports=jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED
--add-exports=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED
--add-exports=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED
```

Execute the task googleJavaFormat to format all *.java files in the project
```dtd
./gradlew goJF
```
Execute the task verifyGoogleJavaFormat to verify that all *.java files are formatted properly
```dtd
./gradlew goJF
```

### CheckStyle
https://checkstyle.org/index.html
https://github.com/jshiell/checkstyle-idea/blob/main/README.md