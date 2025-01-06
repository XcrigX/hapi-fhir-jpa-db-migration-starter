package com.github.xcrigx.springstub.hapi.migratedb;

import ca.uhn.fhir.jpa.migrate.DriverTypeEnum;
import ca.uhn.fhir.jpa.migrate.HapiMigrator;
import ca.uhn.fhir.jpa.migrate.MigrationTaskList;
import ca.uhn.fhir.jpa.migrate.SchemaMigrator;
import ca.uhn.fhir.jpa.migrate.tasks.HapiFhirJpaMigrationTasks;
import ca.uhn.fhir.util.VersionEnum;

import javax.sql.DataSource;
import java.util.HashSet;

public class HapiMigrateDb {

    public void migrateDb(boolean dryRun, boolean noColumnShrink, boolean runHeavyweight, DataSource dataSource, DriverTypeEnum driverType) {
        HapiMigrator migrator = new HapiMigrator(SchemaMigrator.HAPI_FHIR_MIGRATION_TABLENAME, dataSource, driverType);

        migrator.createMigrationTableIfRequired();
        migrator.setDryRun(dryRun);
        migrator.setRunHeavyweightSkippableTasks(runHeavyweight);
        migrator.setNoColumnShrink(noColumnShrink);
        //String skipVersions = theCommandLine.getOptionValue(BaseFlywayMigrateDatabaseCommand.SKIP_VERSIONS);
        String skipVersions = null;
        addTasks(migrator, skipVersions);
        migrator.migrate();

    }

    private void addTasks(HapiMigrator theMigrator, String theSkipVersions) {
        //MigrationTaskList taskList = new HapiFhirJpaMigrationTasks(getFlags()).getAllTasks(VersionEnum.values());
        MigrationTaskList taskList = new HapiFhirJpaMigrationTasks(new HashSet<String>()).getAllTasks(VersionEnum.values());
        taskList.setDoNothingOnSkippedTasks(theSkipVersions);
        theMigrator.addTasks(taskList);
    }

}
