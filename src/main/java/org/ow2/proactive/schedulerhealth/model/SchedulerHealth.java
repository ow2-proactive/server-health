package org.ow2.proactive.schedulerhealth.model;

import lombok.Data;
import org.ow2.proactive.schedulerhealth.utils.WebPortalsProber;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * This class models the scheduler health.
 */
public @Data class SchedulerHealth {

    long jvmStartTime, jvmUptime, heapMemoryUsage;
    String classpath, libpath;
    String jvmName, jvmVersion;

    private int studioCurlCode, rmCurlCode, schedulerCurlCode;
    private int studioHTTPcode, rmHTTPcode, schedulerHTTPcode;

    public SchedulerHealth() {
        checkHealth();
    }

    private void checkHealth() {

        /* GET GENERAL INFO */
        RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
        jvmStartTime = rb.getStartTime();
        jvmUptime = rb.getUptime();
        classpath = rb.getClassPath();
        libpath = rb.getLibraryPath();
        jvmName = rb.getVmName();
        jvmVersion = rb.getVmVersion();
        heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed();

        /* CHECK WEB PORTALS */
        WebPortalsProber webPortalsProber = new WebPortalsProber();
        // Using curl
        studioCurlCode = webPortalsProber.getCurlretcode("localhost", "studio", 8080);
        rmCurlCode = webPortalsProber.getCurlretcode("localhost", "rm", 8080);
        schedulerCurlCode = webPortalsProber.getCurlretcode("localhost", "scheduler", 8080);
        // Using Java
        studioHTTPcode = webPortalsProber.getHTTPretcode("localhost", "studio", 8080);
        rmHTTPcode = webPortalsProber.getHTTPretcode("localhost", "rm", 8080);
        schedulerHTTPcode = webPortalsProber.getHTTPretcode("localhost", "scheduler", 8080);
    }
}
