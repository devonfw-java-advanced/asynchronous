package com.devonfw.java.training.asynchronous.service;

import com.devonfw.java.training.asynchronous.entity.Pi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PiMultiService {

    @Autowired
    private PiSingleService piSingleService;

    public List<Pi> computeMultiPis(int timeToComputeInSeconds, int numberOfProbes) {
        return Stream.generate(() -> timeToComputeInSeconds).
                limit(numberOfProbes)
                .map(piSingleService::computeSinglePi)
                .collect(Collectors.toList());
    }

    @Async("async")
    public void computeMultiPis(int timeToComputeInSeconds, int numberOfProbes, DeferredResult<List<Pi>> result) {
        result.setResult(
                Stream.generate(() -> timeToComputeInSeconds).
                        limit(numberOfProbes)
                        .map(piSingleService::computeSinglePi)
                        .collect(Collectors.toList()));
    }
}
