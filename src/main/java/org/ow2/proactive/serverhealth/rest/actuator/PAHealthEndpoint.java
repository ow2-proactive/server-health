package org.ow2.proactive.schedulerhealth.rest.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
/**
 * Created by Gaëtan Hurel on 9/1/17.
 *
 * The PAHealthEndpoint is a custom Spring Boot Actuator endpoint used to retrieve
 * application internal state information (e.g. RM state, studio state).
 *
 * From a REST point of view, we do not actually use this endpoint as it is unsecure by nature.
 * (Actuator relies on Spring Boot Security to implement security, but we don't use it).
 * It is therefore disabled inside the application.properties file.
 *
 * However, the real use of this endpoint is to be the basis of its MVC-enabled counterpart.
 * The MVC-enabled endpoint based on this endpoint allows us i) to retrieve the same information
 * as its non-MVC counterpart (that is, this endpoint), and ii) to perform authentication
 * check and behave accordingly.
 *
 *
 */
public class PAHealthEndpoint implements Endpoint {

    private final HealthIndicator healthIndicator;

    @Autowired
    public PAHealthEndpoint(HealthIndicator healthIndicator) {
        this.healthIndicator = healthIndicator;
    }

    public Health health() {
        return this.healthIndicator.health();
    }

    @Override
    // NOTE: this is ID is also used as Request Mapping
    public String getId() {
        return "health";
    }

    @Override
    // We disable this endpoint.
    public boolean isEnabled() {
        return false;
    }

    @Override
    public boolean isSensitive() {
        return false;
    }

    @Override
    public Health invoke() {
        return this.healthIndicator.health();
    }
}