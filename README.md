# H V2
__Description:__ The project is kinda a basic CRUD, which will contain the structure including security, exception handling, entity mapping, multiple datasources, and so on features to learn spring boot, spring security, spring batch (in the future), neo4j, h2, apache ignite, and so on (complete list of things being used below).

## DB schemes
__H2__ data related schema
__Neo4j__ security schema

## Instructions
1. Create neo4j admin user
```
CALL dbms.security.createUser('admin', 'your_pass', false)
```


3. Generate the classes needed from the mapper and the DSLQuery
```
mvn clean install -DskipTests=true
```

4. You can run the app now

### IDE configuration
Add the target/generated-sources to the build path in order the IDE to find out the generated classes 