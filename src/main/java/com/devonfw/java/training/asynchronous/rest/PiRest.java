package com.devonfw.java.training.asynchronous.rest;

import java.util.List;
import java.util.concurrent.Callable;

import com.devonfw.java.training.asynchronous.entity.Pi;
import com.devonfw.java.training.asynchronous.service.PiMultiService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rest")
public class PiRest {

    Logger logger = LoggerFactory.getLogger(PiRest.class);

    @Autowired
    private PiMultiService piService;

    @GetMapping("pi")
    public List<Pi> pi(
            @RequestParam(name = "timeToComputeInSeconds", required = false, defaultValue = "0") int timeToComputeInSeconds,
            @RequestParam(name = "numberOfProbes", required = false, defaultValue = "0") int numberOfProbes) {
        return piService.computeMultiPisAsync(timeToComputeInSeconds, numberOfProbes);
    }

    @GetMapping("pi-async")
    public Callable<List<Pi>> piAsync(
            @RequestParam(name = "timeToComputeInSeconds", required = false, defaultValue = "0") int timeToComputeInSeconds,
            @RequestParam(name = "numberOfProbes", required = false, defaultValue = "0") int numberOfProbes) {
        return () -> {
            return piService.computeMultiPisAsync(timeToComputeInSeconds, numberOfProbes);
        };
    }

}
