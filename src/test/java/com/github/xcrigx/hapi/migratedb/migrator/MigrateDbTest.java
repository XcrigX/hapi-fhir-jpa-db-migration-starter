package com.github.xcrigx.hapi.migratedb.migrator;

import ca.uhn.fhir.jpa.migrate.DriverTypeEnum;
import com.github.xcrigx.hapi.migratedb.config.SpringConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringConfig.class)
@EnableAutoConfiguration
@ActiveProfiles("test")

public class MigrateDbTest {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MigrateDbTest.class);

    @Autowired
    DataSource datasource;

    @Test
    @Disabled
    public void testDryRun() {
        log.debug("datasource->{}", datasource);
        HapiMigrateDb migrate = new HapiMigrateDb();
        migrate.migrateDb(true, false, false, datasource, DriverTypeEnum.MSSQL_2012);
    }
}
