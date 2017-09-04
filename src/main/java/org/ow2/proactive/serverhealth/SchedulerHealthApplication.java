package org.ow2.proactive.serverhealth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/**
 * Created by Gaëtan Hurel on 9/1/17.
 */
public class SchedulerHealthApplication {
	public static void main(String[] args) {
		SpringApplication.run(SchedulerHealthApplication.class, args);
	}
}
