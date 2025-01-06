package com.github.xcrigx.springstub.rest;

import ca.uhn.fhir.jpa.migrate.DriverTypeEnum;
import com.github.xcrigx.springstub.hapi.migratedb.HapiMigrateDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

@RestController
//@RequestMapping("/")
public class MigrateDbController {

	private static final Logger log = LoggerFactory.getLogger(MigrateDbController.class);

	@Autowired
	private DataSource datasource;

	@Value("${migratedb.apikey")
	String apiKeyProp;

	private static long lastCalledTs = 0;

	private static Semaphore semaphore = new Semaphore(1);

	@GetMapping(path = "/ping", produces = "application/json")
	public ResponseEntity<ResponseStruct> ping() {
		log.debug("endpoint called");
		return new ResponseEntity<>(new ResponseStruct("pong"), HttpStatus.OK);
	}

	@GetMapping(path = "/migrate-db", produces = "application/json")
	public ResponseEntity<ResponseStruct> migrate(@RequestParam(name = "dryRun", defaultValue = "true", required = false ) String dryRun,
												  @RequestParam(name = "dbType", required = true) String dbType,
												  @RequestParam(name = "apiKey", required = true) String apiKey) {

		log.info("migrate endpoint called. dryRun->{}, dbType->{}", dryRun, dbType);
		boolean dryRunBool = "false".equals(dryRun) ? false : true;

		//added bit of pseudo-security to prevent unauthorized invocations
		if( apiKey == null || !apiKey.equals(apiKeyProp)) {
			String msg = "apiKey is required";
			return new ResponseEntity<>(new ResponseStruct(msg), HttpStatus.UNAUTHORIZED);
		}

		DriverTypeEnum dbEnumType = null;
		try {
			dbEnumType = DriverTypeEnum.valueOf(dbType);
		} catch(IllegalArgumentException ex) {
			List<String> possibleTypes = new ArrayList<>();
			for( DriverTypeEnum v : DriverTypeEnum.values() ){
				possibleTypes.add(v.name());
			}
			String msg = "dbType param required, must be one of: " + String.join(", ", possibleTypes);

			return new ResponseEntity<>(new ResponseStruct(msg), HttpStatus.BAD_REQUEST);
		}
		final DriverTypeEnum dbEnumTypeFinal = dbEnumType;

		if(!dryRunBool &&  System.currentTimeMillis() - lastCalledTs > 10000) {
			lastCalledTs = System.currentTimeMillis();
			String msg = "To prevent accidental invocations, we require that you call this API twice within 10 seconds. Please issue query again to begin execution.";
			return new ResponseEntity<>(new ResponseStruct(msg), HttpStatus.OK);
		}

		boolean gotLock = semaphore.tryAcquire();
		if( !gotLock ) {
			String msg = "A DB Migration is already in progress, please wait for it to complete.";
			return new ResponseEntity<>(new ResponseStruct(msg), HttpStatus.CONFLICT);
		}else {

			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try{
						HapiMigrateDb migrate = new HapiMigrateDb();
						log.info("--DB Migration Task Started--");
						migrate.migrateDb(dryRunBool, false, false, datasource, dbEnumTypeFinal);
						log.info("--DB Migration Task Completed--");
					}
					finally {
						semaphore.release();
					}
				}
			};
			new Thread(runnable).start();
		}

		return new ResponseEntity<>(new ResponseStruct("Process started asynchronously, please monitor the log for the status"), HttpStatus.ACCEPTED);
	}

	private static class ResponseStruct {
		public LocalDateTime timeStamp = LocalDateTime.now();
		public String message = null;

		public ResponseStruct(String msg) {
			this.message = msg;
		}
	}

}
