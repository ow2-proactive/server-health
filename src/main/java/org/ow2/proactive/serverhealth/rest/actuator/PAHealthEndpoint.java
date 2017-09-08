/*
 * ProActive Parallel Suite(TM):
 * The Open Source library for parallel and distributed
 * Workflows & Scheduling, Orchestration, Cloud Automation
 * and Big Data Analysis on Enterprise Grids & Clouds.
 *
 * Copyright (c) 2007 - 2017 ActiveEon
 * Contact: contact@activeeon.com
 *
 * This library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation: version 3 of
 * the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 */
package org.ow2.proactive.serverhealth.rest.actuator;

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
