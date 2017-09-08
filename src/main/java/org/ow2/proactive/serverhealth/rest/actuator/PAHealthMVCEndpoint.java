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

import java.util.Optional;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.ow2.proactive.serverhealth.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.HealthEndpoint;
import org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by Gaëtan Hurel on 9/1/17.
 *
 * The PAHealthMVCEndpoint is the MVC-enabled counterpart of the PAHealthEndpoint.
 * See PAHealthEndpoint comments for more details.
 */
@Component
public class PAHealthMVCEndpoint extends EndpointMvcAdapter {

    private final PAHealthEndpoint delegate;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private HealthEndpoint paHealthEndpoint;

    @Autowired
    public PAHealthMVCEndpoint(PAHealthEndpoint delegate) {
        super(delegate);
        this.delegate = delegate;
    }

    /*
     * The 'real', effective REST endpoint.
     * Typically, the full URL would be:
     * http(s)://<SCHEDULER_FQDN>/<PREFIX>/ID/_health
     * where ID is the ID of the non-MVC endpoint.
     * 
     * This endpoint is used to retrieve internal state information.
     * It performs first some authentication check to know wether or not it
     * should send back the requested information.
     */
    @RequestMapping(value = "/_health", method = RequestMethod.GET)
    @ResponseBody
    public Response filter(@RequestHeader(value = "sessionid", required = true) String sessionId) {
        Optional<String> currentUser = Optional.ofNullable(authenticationService.isValidSession(sessionId));
        if (currentUser.isPresent() && currentUser.get().equals("admin")) {
            // user is admin, sending health information
            return Response.ok(delegate.invoke()).build();
        }
        // user is not admin, doing nothing
        return Response.status(Response.Status.UNAUTHORIZED)
                       .entity("Unauthorized. Log on the scheduler as admin first.")
                       .type(MediaType.APPLICATION_JSON)
                       .build();
    }
}
