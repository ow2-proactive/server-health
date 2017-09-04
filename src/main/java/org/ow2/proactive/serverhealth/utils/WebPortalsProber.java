package org.ow2.proactive.serverhealth.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class is used to probe the various web portals of the PA Scheduling Workflows suite.
 */
public class WebPortalsProber {

    private static Logger LOGGER = (Logger) LoggerFactory.getLogger(WebPortalsProber.class);

    public WebPortalsProber() {}

    /**
     * Performs an HTTP connection to the given URL, and returns the HTTP result code.
     * @param host the web server host (IP or hostname)
     * @param path the URL to be probed (excluding the prefix "/")
     * @param port the web server port
     * @return the HTTP result code of the probing or -10 if exception thrown.
     */
    public int getHTTPretcode(String host, String path, int port) {
        String myURL = "http://" + host + ":" + port + "/" + path;
        BufferedReader reader = null;
        StringBuilder stringBuilder;

        try {
            URL url = new URL(myURL);
            LOGGER.debug("HTTP probe URL: " + myURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(2 * 1000);
            connection.setConnectTimeout(2 * 1000);
            connection.connect();
            return connection.getResponseCode();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return -10;
        }
    }

    /**
     * Performs a curl connection to the given URL, and returns the curl result code (bash).
     * @param host the web server host (IP or hostname)
     * @param path the URL to be probed (excluding the prefix "/")
     * @param port the web server port
     * @return the curl result code of the probing or -10 if exception thrown.
     */
    public int getCurlretcode(String host, String path, int port) {
        try {
            Process p = new ProcessBuilder("curl", "--silent", "--fail", "--max-time 2", "http://" + host + ":" + port + "/" + path).start();
            p.waitFor();
            return p.exitValue();
        } catch (Exception e) {
            e.printStackTrace();
            return -10;
        }
    }
}
