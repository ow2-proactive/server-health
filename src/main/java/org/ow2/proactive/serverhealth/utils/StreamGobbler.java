package org.ow2.proactive.schedulerhealth.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

/*
 This class is left for debugging purposes.

    How to use it:
    Process p = new ProcessBuilder("any command").start();
    StringBuilder sb = new StringBuilder();
    outputGobbler = new StreamGobbler(p.getInputStream(), line -> sb.append(line));
    errorGobbler = new StreamGobbler(p.getErrorStream(), logger::error);
    new Thread(outputGobbler).start();
    new Thread(errorGobbler).start();

    => error output of the process is logged as error
    => standard output of the process is stored within StringBuilder sb
*/
public class StreamGobbler implements Runnable {
    private InputStream inputStream;
    private Consumer<String> consumeInputLine;

    public StreamGobbler(InputStream inputStream, Consumer<String> consumeInputLine) {
        this.inputStream = inputStream;
        this.consumeInputLine = consumeInputLine;
    }

    public void run() {
        new BufferedReader(new InputStreamReader(inputStream)).lines().forEach(consumeInputLine);
    }
}