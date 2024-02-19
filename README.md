# Spring Boot RESTful + JPA Archetype

Spring Boot REST API Application Archetype is a Maven archetype for a Spring Boot microservice.

Make sure you have correct tooling:

- java 21 or later
- Maven 3.6.3 or later
- Make sure `GIT_HOME`, `MVN_HOME` and `JAVA_HOME` are set, and they are in your `PATH` variable.

Run the following to build a project from the archetype:

```
git clone https://github.com/IQKV/archetype-boot-jpa-restful
cd archetype-boot-jpa-restful
./mvnw clean package install
mkdir generated-apps
cd generated-apps
mvn archetype:generate -DarchetypeGroupId=org.iqkv.archetype -DarchetypeArtifactId=archetype-boot-jpa-restful -DarchetypeVersion=24.0.0-SNAPSHOT
```

#### Example:

```
[INFO] --- maven-archetype-plugin:3.2.1:generate (default-cli) @ standalone-pom ---
[INFO] Generating project in Interactive mode
[INFO] Archetype repository not defined. Using the one from [org.iqkv.archetype:archetype-boot-jpa-restful:24.0.0-SNAPSHOT] found in catalog local
Define value for property 'groupId': com.example
Define value for property 'artifactId': fruit-packaging-service
[INFO] Using property: version = 1.0.0-SNAPSHOT
Define value for property 'projectName': Fruit Packaging Service     
Define value for property 'projectDescription': RESTful API service on top of Spring Boot, JPA, Hibernate (Postgres), Liquibase.
[INFO] Using property: groupId = com.example
[INFO] Using property: artifactId = fruit-packaging-service
Define value for property 'artifactIdWithoutHyphens' fruitpackagingservice: : 
Define value for property 'package' com.example.fruitpackagingservice: : 
Define value for property 'mainClass' FruitPackagingServiceApplication: : 
Confirm properties configuration:
groupId: com.example
artifactId: fruit-packaging-service
version: 1.0.0-SNAPSHOT
projectName: Fruit Packaging Service
projectDescription: RESTful API service on top of Spring Boot, JPA, Hibernate (Postgres), Liquibase.
groupId: com.example
artifactId: fruit-packaging-service
artifactIdWithoutHyphens: fruitpackagingservice
package: com.example.fruitpackagingservice
mainClass: FruitPackagingServiceApplication
 Y: : Y
```

Once it is generated, follow README.md in generated project folder.

#### Additional steps:

Set correct permissions for executable files:

```
cd fruit-packaging-service
chmod 0777 ./mvnw
chmod 0777 ./.husky/pre-commit
```

(Optional) Install pre-commit hook (using locally installed npm):

```
npm install
```

Check Java imports ordering regarding checkstyle & editorconfig rules, and enable checkstyle plugin in pom.xml:

```
    <properties>
        ...
        <checkstyle.skip>false</checkstyle.skip>
    </properties>
```

Initialize the Git repository for the newly created project (this is required to run tests due to the use of the git-commit-id-plugin):

```
git init
git branch -M dev`
find . -type f | grep -vP '^.\/.git' |  xargs git add
git commit -a -m "feat: create boilerplate code for RESTful API + JPA  project."
```

Finally, test & run your application:

```
mvn test

docker-compose -f docker-compose.dev.yml up -d

mvn spring-boot:run -Dspring-boot.run.profiles=dev,local

```

*Happy coding!*

## Versioning

Project uses a three-segment [CalVer](https://calver.org/) scheme, with a short year in the major version slot, short month in the minor version slot, and micro/patch version in the third
and final slot.

```
YY.MM.MICRO
```

1. **YY** - short year - 6, 16, 106
1. **MM** - short month - 1, 2 ... 11, 12
1. **MICRO** -  "patch" segment
