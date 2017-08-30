package org.ow2.proactive.schedulerhealth.rest.controller;

import java.awt.*;

import org.ow2.proactive.schedulerhealth.model.SchedulerHealth;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @RequestMapping(value = "/general", method = RequestMethod.GET)
    public SchedulerHealth index() {
        return new SchedulerHealth();
    }

}