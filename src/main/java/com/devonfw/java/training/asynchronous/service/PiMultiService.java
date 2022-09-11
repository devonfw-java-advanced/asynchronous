package com.devonfw.java.training.asynchronous.service;

import com.devonfw.java.training.asynchronous.entity.Pi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PiMultiService {

    @Autowired
    private Executor executor;

    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Autowired
    private PiSingleService piSingleService;

    public List<Pi> computeMultiPisAsync(int timeToComputeInSeconds, int numberOfProbes) {
        return Stream.generate(() -> timeToComputeInSeconds)
                .limit(numberOfProbes)
                .parallel()
                .map(piSingleService::computeSinglePiAsync)
                .map(this::getPi)
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

    public List<Pi> computeMultiPis(int timeToComputeInSeconds, int numberOfProbes) {
        return Stream.generate(() -> timeToComputeInSeconds).
                limit(numberOfProbes)
                .map(piSingleService::computeSinglePi)
                .collect(Collectors.toList());
    }

    public List<Pi> computeMultiPisAsyncWithExecutor(int timeToComputeInSeconds, int numberOfProbes) {
        return Stream.generate(() -> timeToComputeInSeconds)
                .limit(numberOfProbes)
                .parallel()
                .map(t -> new FutureTask<>(() -> piSingleService.computeSinglePi(t)))
                .map(p -> {
                    executor.execute(p);
                    return p;
                })
                .map(this::getPi)
                .collect(Collectors.toList());
    }

    public List<Pi> computeMultiPisAsyncWithExecutorService(int timeToComputeInSeconds, int numberOfProbes) {
        return Stream.generate(() -> timeToComputeInSeconds)
                .limit(numberOfProbes)
                .parallel()
                .map(t -> executorService.submit(() -> piSingleService.computeSinglePi(t)))
                .map(this::getPi)
                .collect(Collectors.toList());
    }

    private Pi getPi(Future<Pi> pi) {
        try {
            return pi.get();
        } catch (Exception e) {
            return null;
        }
    }
}
