DB Server
=========
Download hsqlserver and start it like this
```
java -cp lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:/tmp/hsql_db --dbname.0 data-extraction-unit
```

Database setup
```
mvn clean install liquibase:update
```