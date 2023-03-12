# Spring Boot RESTful + JPA Archetype

Spring Boot REST API Application Archetype is a Maven archetype for a Spring Boot microservice.

Make sure you have correct tooling:

- Java 17 or later
- Maven 3.6.3 or later
- Make sure `GIT_HOME`, `MVN_HOME` and `JAVA_HOME` are set, and they are in your `PATH` variable.

Run the following to build a project from the archetype:

```
git clone https://github.com/ujar-org/archetype-boot-jpa-restful
cd archetype-boot-jpa-restful
mvn clean package install
mkdir generated-apps
cd generated-apps
mvn archetype:generate -DarchetypeGroupId=org.ujar.archetype -DarchetypeArtifactId=archetype-boot-jpa-restful -DarchetypeVersion=0.2.0-SNAPSHOT
```

#### Example:

```
[INFO] --- maven-archetype-plugin:3.2.1:generate (default-cli) @ standalone-pom ---
[INFO] Generating project in Interactive mode
[INFO] Archetype repository not defined. Using the one from [org.ujar.archetype:archetype-boot-jpa-restful:0.2.0-SNAPSHOT] found in catalog local
Define value for property 'groupId': org.ujar
Define value for property 'artifactId': fruit-packaging-service
[INFO] Using property: version = 0.1.0-SNAPSHOT
Define value for property 'projectName': Fruit Packaging Service            
Define value for property 'projectDescription': Application allows to manage packaging data.
[INFO] Using property: groupId = org.ujar
[INFO] Using property: artifactId = fruit-packaging-service
Define value for property 'artifactIdWithoutHyphens' fruit-packaging-service: : fruitpackagingservice 
Define value for property 'package' org.ujar.fruitpackagingservice: : 
Confirm properties configuration:
groupId: org.ujar
artifactId: fruit-packaging-service
version: 0.1.0-SNAPSHOT
projectName: Fruit Packaging Service
projectDescription: Application allows to manage packaging data.
groupId: org.ujar
artifactId: fruit-packaging-service
artifactIdWithoutHyphens: fruitpackagingservice
package: org.ujar.fruitpackagingservice
 Y: : Y
```

Once it is generated, follow README.md in generated project folder.

#### Additional steps:

Set correct permissions for Maven wrapper:

```
cd fruit-packaging-service
chmod 0777 ./mvnw
```

Initialize the Git repository for the newly created project (this is required to run the integration tests due to the use of the git-commit-id-plugin):

```
git init
git branch -M develop
git commit -a -m "feat: create boilerplate code for RESTful API + JPA  project."
```
