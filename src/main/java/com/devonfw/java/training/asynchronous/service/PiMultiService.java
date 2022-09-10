package com.devonfw.java.training.asynchronous.service;

import com.devonfw.java.training.asynchronous.entity.Pi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PiMultiService {

    @Autowired
    private PiSingleService piSingleService;

    public List<Pi> computeMultiPisAsync(int timeToComputeInSeconds, int numberOfProbes) {
        CompletableFuture<Pi>[] completableFuturePis = Stream.generate(() -> timeToComputeInSeconds)
                .limit(numberOfProbes).map(piSingleService::computeSinglePiAsync).toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(completableFuturePis).join();

        List<Pi> pis = Arrays.stream(completableFuturePis)
                .map(completableFuturePi -> callCatchingErrors(completableFuturePi::get, null)).filter(Objects::nonNull)
                .collect(Collectors.toList());

        return pis;
    }

    public List<Pi> computeMultiPis(int timeToComputeInSeconds, int numberOfProbes) {
        return Stream.generate(() -> timeToComputeInSeconds).
                limit(numberOfProbes)
                .map(piSingleService::computeSinglePi)
                .collect(Collectors.toList());
    }

    private <T> T callCatchingErrors(Callable<T> callable, T valueOnError) {
        try {
            return callable.call();
        } catch (Exception e) {
            return valueOnError;
        }
    }
}
