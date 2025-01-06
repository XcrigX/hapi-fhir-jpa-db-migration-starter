# HAPI FHIR JPA DB Migration Starter

### Overview and Purpose
This project wraps the HAPI CLI database migration utility in a RESTful API, allowing it to be deployed using a similar CI/CD scheme as the HAPI server and allow the same secrets management, connectivity, etc. which may not be possible with a CLI tool.  

API Parameters:

- dbType - The database type, should match the ca.uhn.fhir.jpa.migrate.DriverTypeEnum types:
  MARIADB_10_1, MYSQL_5_7, POSTGRES_9_4, ORACLE_12C, MSSQL_2012, COCKROACHDB_21_1 
  
- dryRun = true/false - tells the migration utility to whether to perform a dry-run vs actual migration

- apiKey - An api key which is required to invoke the API. Intended as an added measure of psuedo-security to prevent unauthorized invocations. A generated or secret API key can be injected at deploy time to override the default.

Example invocation: 
http://localhost:8080/migratedb/migrate-db?dbType=POSTGRES_9_4&dryRun=true&apiKey=ABC123

Example invocation (connectivity test): 
http://localhost:8080/migratedb/ping

### Core Features
This project adds Azure Cloud Spring dependencies to allow Azure Managed identity auth to databases.  

### Limitations

### Known Issues

### Update Notes
When using for a new version of HAPI, several dependencies in the pom.xml file need to be syncronized. There are notes in the pom file about what these are and where/how to find the appropriate versions. 

### Release notes

#### Version 1.0.0-RELEASE




