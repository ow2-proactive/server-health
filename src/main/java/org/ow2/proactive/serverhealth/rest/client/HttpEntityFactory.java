package org.ow2.proactive.schedulerhealth.rest.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

@Service
/**
 * Utilitary class to perform HTTP REST requests embedding the 'sessionid' parameter.
 */
public class HttpEntityFactory {

    public HttpEntity<MultiValueMap<String, Object>> createHttpEntity(MultiValueMap<String, Object> multipartVariables,
                                                                      String sessionId, MediaType contentType) {
        HttpHeaders headers = createHeaders(sessionId, Optional.of(contentType));
        return new HttpEntity<>(multipartVariables, headers);
    }

    public HttpEntity<String> createHttpEntity(String sessionId) {
        HttpHeaders headers = createHeaders(sessionId, Optional.empty());
        return new HttpEntity<String>(headers);
    }

    private HttpHeaders createHeaders(String sessionId, Optional<MediaType> contentType) {
        HttpHeaders sessionIdHeaders = new HttpHeaders();
        if (contentType.isPresent()) {
            sessionIdHeaders.setContentType(contentType.get());
        }
        sessionIdHeaders.add("sessionid", sessionId);
        return sessionIdHeaders;
    }
}