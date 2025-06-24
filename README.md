# Ejercicio Guiado: Automatización Profesional con Maven
## Proyecto TaskMaster - Gestión de Tareas desde Consola

En este ejercicio trabajarás como parte del equipo de desarrollo de "TaskMaster", una herramienta simple que gestiona tareas desde consola. Aprenderás a automatizar el ciclo de construcción del proyecto usando Maven, una herramienta ampliamente usada en la industria.

## 🎯 Objetivos
- Comprender qué es Maven y cómo automatiza la construcción del software
- Instalar y configurar Maven en tu equipo
- Crear un proyecto con estructura profesional
- Agregar dependencias externas y gestionar versiones
- Crear y ejecutar pruebas unitarias
- Empaquetar la aplicación como .jar lista para distribución

---

## 📋 Paso 1: Instalación y configuración de Maven

### Windows
1. Descarga Maven desde: https://maven.apache.org/download.cgi
2. Descomprime en `C:\maven`
3. Configura variables de entorno:
   - `MAVEN_HOME`: `C:\maven`
   - Agrega `%MAVEN_HOME%\bin` al `PATH`

### Linux/macOS
```bash
# Ubuntu/Debian
sudo apt update
sudo apt install maven

# macOS con Homebrew
brew install maven

# Verificación
mvn -v
```

**Salida esperada:**
```
Apache Maven 3.x.x
Maven home: /path/to/maven
Java version: 17.x.x
```

---

## 📋 Paso 2: Creación del proyecto "TaskMaster"

```bash
mvn archetype:generate \
  -DgroupId=com.equipo.taskmaster \
  -DartifactId=taskmaster \
  -DarchetypeArtifactId=maven-archetype-quickstart \
  -DinteractiveMode=false

cd taskmaster
```

### Estructura del proyecto generada:
```
taskmaster/
├── pom.xml
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/equipo/taskmaster/
│   │           └── App.java
│   └── test/
│       └── java/
│           └── com/equipo/taskmaster/
│               └── AppTest.java
└── target/
```

---

## 📋 Paso 3: Implementación de la clase principal

Edita `src/main/java/com/equipo/taskmaster/App.java`:

```java
package com.equipo.taskmaster;

import java.util.ArrayList;

public class App {
 	public static ArrayList<String> tasks = new ArrayList<>();
 	public static void main(String[] args) {
 		System.out.println("Bienvenido a TaskMaster!");
 		addTask("Estudiar Maven");
 		addTask("Leer sobre CI/CD");
 		printTasks();
 	}
 
 	public static void addTask(String task) {
 		tasks.add(task);
 	}
 	public static void printTasks() {
 		System.out.println("Tareas pendientes:");
		System.out.println("Ambiente: " + System.getProperty("env.name"));
 		for (String t : tasks) {
 			System.out.println("- " + t);
 		}
 	}
}
```

---

## 📋 Paso 4: Gestión de dependencias

Actualiza el archivo `pom.xml` con las siguientes dependencias:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.equipo.taskmaster</groupId>
  <artifactId>taskmaster</artifactId>
  <packaging>jar</packaging>
  <version>1.0-SNAPSHOT</version>
  <name>taskmaster</name>
  <url>http://maven.apache.org</url>
  <dependencies>
    <dependency>
 	    <groupId>org.apache.commons</groupId>
 	    <artifactId>commons-lang3</artifactId>
 	    <version>3.12.0</version>
    </dependency>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.13.2</version>
      <scope>test</scope>
    </dependency>
  </dependencies>
</project>
```

---

## 📋 Paso 5: Pruebas unitarias con JUnit

Actualiza `src/test/java/com/equipo/taskmaster/AppTest.java`:

```java
package com.equipo.taskmaster;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AppTest {
	 @Test
	 public void testAddTask() {
 		App.tasks.clear();
 		App.addTask("Terminar ejercicio Maven");
 		assertEquals(1, App.tasks.size());
 	}
}
```

### Ejecutar las pruebas:
```bash
mvn test
```

**Salida esperada:**
```
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

---

## 📋 Paso 6: Configuración de plugins y empaquetado

Completa el `pom.xml` agregando la sección `<build>`:

```xml
<build>
 	  <plugins>
 		  <plugin>
 			  <groupId>org.apache.maven.plugins</groupId>
 			  <artifactId>maven-compiler-plugin</artifactId>
 			  <version>3.14.0</version>
 			  <configuration>
 				  <source>17</source>
 				  <target>17</target>
          <release>17</release>
 			  </configuration>
 		  </plugin>
	
		  <plugin>
 			  <groupId>org.codehaus.mojo</groupId>
 			  <artifactId>exec-maven-plugin</artifactId>
 			  <version>3.1.0</version>
 			  <configuration>
 				  <mainClass>com.equipo.taskmaster.App</mainClass>
 			  </configuration>
 		  </plugin>
 	  </plugins>
  </build>
```

### Comandos para compilar y ejecutar:
```bash
# Limpiar y compilar
mvn clean compile

# Ejecutar pruebas
mvn test

# Empaquetar
mvn clean package

# Ejecutar directamente con Maven
mvn exec:java

# Ejecutar el JAR generado
java -jar target/taskmaster-1.0-SNAPSHOT.jar
```

---

## 📋 Paso 7: Perfiles y propiedades avanzadas

Agrega perfiles al `pom.xml` antes de la etiqueta `</project>`:

```xml
<profiles>
 	  <profile>
 		  <id>dev</id>
 		  <properties>
 			  <env.name>Desarrollo</env.name>
 		  </properties>
 	</profile>
 
 	<profile>
 		<id>prod</id>
 		<properties>
 			<env.name>Producción</env.name>
 		</properties>
 	</profile>
</profiles>
```

### Ejecutar con diferentes perfiles:
```bash
# Desarrollo (por defecto)
mvn exec:java

# Testing
mvn exec:java -Ptest

# Producción
mvn exec:java -Pprod

# Con propiedades personalizadas
mvn exec:java -Pdev -Denv.name="Mi Entorno Local"
```

---

## 🚀 Comandos Maven más utilizados

```bash
# Ciclo de vida básico
mvn clean                 # Limpiar archivos generados
mvn compile               # Compilar código fuente
mvn test                  # Ejecutar pruebas
mvn package               # Crear JAR/WAR
mvn install               # Instalar en repositorio local
mvn deploy                # Desplegar a repositorio remoto

# Comandos combinados
mvn clean compile        # Limpiar y compilar
mvn clean test           # Limpiar y probar
mvn clean package        # Limpiar y empaquetar
mvn clean install        # Ciclo completo hasta instalar

# Información del proyecto
mvn dependency:tree      # Ver árbol de dependencias
mvn help:effective-pom   # Ver POM efectivo
mvn versions:display-dependency-updates  # Ver actualizaciones disponibles
```

---

## 📝 Preguntas de reflexión

### ¿Qué aprendiste sobre el ciclo de vida de Maven?
Maven tiene ciclos de vida predefinidos (default, clean, site) con fases específicas que se ejecutan en orden. Cada fase tiene objetivos (goals) que realizan tareas específicas.

### ¿Cómo facilita Maven el trabajo en equipo?
- **Reproducibilidad**: Mismo resultado en cualquier máquina
- **Gestión de dependencias**: Automática y versionada
- **Estructura estándar**: Todos los proyectos Maven siguen la misma estructura
- **Integración continua**: Fácil integración con herramientas CI/CD

### ¿Cuál fue el mayor reto al trabajar con dependencias?
- Conflictos de versiones (dependency hell)
- Entender el scope de las dependencias
- Gestionar dependencias transitivas

### ¿Por qué Maven es tan usado en entornos empresariales?
- Estandarización de proyectos
- Gestión robusta de dependencias
- Integración con IDEs y herramientas CI/CD
- Amplio ecosistema de plugins
- Soporte para proyectos multi-módulo

### ¿Qué harías diferente en otro proyecto?
- Definir perfiles desde el inicio
- Usar más plugins para análisis de código
- Implementar mejor estrategia de versionado
- Configurar reporting y documentación automática

---

## 📦 Entregable del Proyecto

### README.md sugerido:

```markdown
# TaskMaster - Gestión de Tareas

Aplicación de consola para gestionar tareas diarias, desarrollada con Java y Maven.

## 🚀 Ejecución rápida
```bash
git clone <tu-repositorio>
cd taskmaster
mvn clean package
java -jar target/taskmaster-1.0-SNAPSHOT.jar
```

## 🛠️ Comandos Maven utilizados
- `mvn clean compile` - Compilación
- `mvn test` - Pruebas unitarias
- `mvn clean package` - Empaquetado
- `mvn exec:java -Pdev` - Ejecución con perfil

## 📚 Dependencias principales
- Apache Commons Lang 3.12.0 - Utilidades para strings
- JUnit 4.13.2 - Framework de pruebas

## 🔧 Plugins configurados
- maven-compiler-plugin - Compilación Java 11
- exec-maven-plugin - Ejecución directa
- maven-shade-plugin - JAR ejecutable
- maven-surefire-plugin - Ejecución de pruebas

## 💡 Aprendizaje técnico principal
Maven simplifica significativamente la gestión de proyectos Java mediante:
- Automatización del ciclo de construcción
- Gestión declarativa de dependencias
- Estandarización de estructura de proyectos

## 🎯 Principal desafío
Configurar correctamente el plugin shade para generar un JAR ejecutable con todas las dependencias incluidas.
```

---

## 🎓 Recursos adicionales

### Documentación oficial
- [Maven Getting Started Guide](https://maven.apache.org/guides/getting-started/)
- [Maven POM Reference](https://maven.apache.org/pom.html)
- [Maven Plugin Registry](https://maven.apache.org/plugins/)

### Mejores prácticas
1. **Versionado semántico**: Usar MAJOR.MINOR.PATCH
2. **Properties**: Centralizar versiones en `<properties>`
3. **Scopes**: Usar correctamente compile, test, provided, runtime
4. **Multi-módulo**: Para proyectos grandes, usar estructura multi-módulo
5. **Profiles**: Separar configuraciones por ambiente
