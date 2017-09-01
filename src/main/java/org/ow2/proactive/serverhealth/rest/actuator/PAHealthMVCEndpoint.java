package org.ow2.proactive.schedulerhealth.rest.actuator;

import org.ow2.proactive.schedulerhealth.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.HealthEndpoint;
import org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

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
    The 'real', effective REST endpoint.
    Typically, the full URL would be:
        http(s)://<SCHEDULER_FQDN>/<PREFIX>/ID/_health
    where ID is the ID of the non-MVC endpoint.

    This endpoint is used to retrieve internal state information.
    It performs first some authentication check to know wether or not it
    should send back the requested information.
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
        // TODO: return 401 or default error message
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity("Unauthorized. Log on the scheduler as admin first.")
                .type(MediaType.APPLICATION_JSON)
                .build() ;
    }
}
