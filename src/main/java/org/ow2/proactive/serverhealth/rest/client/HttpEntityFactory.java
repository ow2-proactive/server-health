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
package org.ow2.proactive.serverhealth.rest.client;

import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;


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
