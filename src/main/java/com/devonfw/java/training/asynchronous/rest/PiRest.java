package com.devonfw.java.training.asynchronous.rest;

import com.devonfw.java.training.asynchronous.entity.Pi;
import com.devonfw.java.training.asynchronous.service.PiMultiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.concurrent.Callable;

@RestController
@RequestMapping("rest")
public class PiRest {

    @Autowired
    private PiMultiService piService;

    @GetMapping("pi")
    public List<Pi> pi(
            @RequestParam(name = "timeToComputeInSeconds", required = false, defaultValue = "0") int timeToComputeInSeconds,
            @RequestParam(name = "numberOfProbes", required = false, defaultValue = "0") int numberOfProbes) {
        return piService.computeMultiPisAsync(timeToComputeInSeconds, numberOfProbes);
    }

    @GetMapping("pi2")
    public List<Pi> pi2(
            @RequestParam(name = "timeToComputeInSeconds", required = false, defaultValue = "0") int timeToComputeInSeconds,
            @RequestParam(name = "numberOfProbes", required = false, defaultValue = "0") int numberOfProbes) {
        return piService.computeMultiPisAsyncWithExecutor(timeToComputeInSeconds, numberOfProbes);
    }

    @GetMapping("pi3")
    public List<Pi> pi3(
            @RequestParam(name = "timeToComputeInSeconds", required = false, defaultValue = "0") int timeToComputeInSeconds,
            @RequestParam(name = "numberOfProbes", required = false, defaultValue = "0") int numberOfProbes) {
        return piService.computeMultiPisAsyncWithExecutorService(timeToComputeInSeconds, numberOfProbes);
    }

    @GetMapping("pi-async")
    public Callable<List<Pi>> piAsync(
            @RequestParam(name = "timeToComputeInSeconds", required = false, defaultValue = "0") int timeToComputeInSeconds,
            @RequestParam(name = "numberOfProbes", required = false, defaultValue = "0") int numberOfProbes) {
        return () -> piService.computeMultiPisAsync(timeToComputeInSeconds, numberOfProbes);
    }

    @GetMapping("pi-async2")
    public DeferredResult<List<Pi>> piAsync2(
            @RequestParam(name = "timeToComputeInSeconds", required = false, defaultValue = "0") int timeToComputeInSeconds,
            @RequestParam(name = "numberOfProbes", required = false, defaultValue = "0") int numberOfProbes) {
        DeferredResult<List<Pi>> result = new DeferredResult<>();

        piService.computeMultiPis(timeToComputeInSeconds, numberOfProbes, result);

        return result;
    }


}
