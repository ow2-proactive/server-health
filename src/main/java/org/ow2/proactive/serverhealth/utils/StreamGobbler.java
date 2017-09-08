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
package org.ow2.proactive.serverhealth.utils;

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
