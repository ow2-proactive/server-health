package org.ow2.proactive.serverhealth.service;

import lombok.NonNull;
import org.ow2.proactive.serverhealth.rest.client.HttpEntityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Scope(value = "singleton")
@Service
/**
 * Created by Gaëtan Hurel on 9/1/17.
 *
 * The AuthenticationService bean is used to check whether or not the user asking for
 * health endpoint is authenticated, and is admin.
 *
 */
public class AuthenticationService {

    @NonNull
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private HttpEntityFactory httpEntityFactory;

    /**
     * Check whether or not a user (identified by its sessionid) is logged in or no.
     * To do so, the AuthenticationService bean performs in turn REST request to the scheduler/rm
     * API, forwarding the given sessionid.
     *
     * @param sessionId the sessionid provided by the user when trying to accessing the health endpoint
     * @return the name of the user if the sessionid is valid, null otherwise;
     */
    public String isValidSession(String sessionId) {
        HttpEntity httpEntityReq = httpEntityFactory.createHttpEntity(sessionId);
        try {
            final String urlReq = UriComponentsBuilder.fromUriString("http://localhost:8080/rest/rm/logins/sessionid/" + sessionId)
                    .toUriString();
            String response = restTemplate.exchange(urlReq, HttpMethod.GET, httpEntityReq, String.class).getBody();
            return response;
        } catch (Exception e){
            return null;
        }
    }
}
