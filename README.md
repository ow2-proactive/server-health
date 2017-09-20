# server-health
The goal of the "server-health" project is to provide health REST endpoints to get various information about the health of the ProActive server (scheduler, rm, studio, etc.). It is a work in progress.

# Current state and next steps
The project is based on Spring Actuator to retrieve health information of a java-based application. 

What has been done up to now is:
- deploy and try the default Actuator `/health` endpoint;
- build a custom endpoint based on the default one to integrate the ProActive sessionID-based authentication mechanism (actuator is based on Spring Security, but we do not want to use that) - the default endpoint has been then removed;
- provide a working PoC.

What remains to be done is:
- deploy the custom health endpoint on each microservice (e.g. scheduler, rm, studio...) - the idea is to have each microservice providing a health endpoint; 
- enhance and customize the health information depending on the microservice (see [Spring Actuator documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html#_writing_custom_healthindicators)).


# Build and deploy
First, build using gradlew: `./gradlew build`
Then, go to your scheduler release `dist/war/` directory and do:
```
mkdir server-health
cp <SERVER-HEALTH-PROJECT>/build/libs/server-health.war ./server-health
cd server-health && jar xf server-health.war && rm server-health.war
cd ../../../bin
./proactive-server
```

The scheduler server is started, and the health endpoint will be available at `server-health/health/_health` 

You need a valid sessionID to try the endpoint. 
To this end, log first on the studio (or rm, or scheduler) portal. You can retrieve your session ID using Burp proxy or browser addons (HTTP Trace, HTTP Headers, HTTP Spy...) for example.

Then:
```
curl -H "sessionid: <YOUR_SESSION_ID>" -H "Accept: application/json" -XGET http://localhost:8080/server-health/health/_health

{"entity":{"status":"UP","total":634002350080,"free":346148376576,"threshold":10485760},"status":200,"metadata":{},"annotations":null,"entityClass":"org.springframework.boot.actuate.health.Health","genericType":null,"lastModified":null,"date":null,"closed":false,"length":-1,"language":null,"location":null,"cookies":{},"allowedMethods":[],"mediaType":null,"links":[],"entityTag":null,"stringHeaders":{},"statusInfo":"OK","headers":{}}
```

The health data is stored in the JSON `entity` object:
`{"status":"UP","total":634002350080,"free":346148376576,"threshold":10485760}`

As stated above, this is the default health information and [it can be customized](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html#_writing_custom_healthindicators).

