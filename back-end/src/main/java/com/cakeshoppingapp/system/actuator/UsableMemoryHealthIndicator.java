package com.cakeshoppingapp.system.actuator;

import java.io.File;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
@Component
public class UsableMemoryHealthIndicator implements HealthIndicator {
	@Override
	public Health health() {
		File path = new File("."); // current directory
		Long usablespace =  path.getUsableSpace();
		Boolean isHealth = usablespace > 10*1024*1024; //10MB
		Status status = isHealth ? Status.UP : Status.DOWN;
		return Health.status(status)
				.withDetail("usableSpace",usablespace) // in addition to the status , we can add more key value pairs to the health object 
				.withDetail("threshold",10*1024*1024)
				.build();
	}

}
