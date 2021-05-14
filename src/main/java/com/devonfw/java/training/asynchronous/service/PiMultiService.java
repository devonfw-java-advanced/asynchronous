package com.devonfw.java.training.asynchronous.service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.devonfw.java.training.asynchronous.entity.Pi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PiMultiService {

    Logger logger = LoggerFactory.getLogger(PiMultiService.class);

    @Autowired
    private PiSingleService piSingleService;

    public List<Pi> computeMultiPisAsync(int timeToComputeInSeconds, int numberOfProbes) {
        logger.info("Start computeMultiPisAsync");

        CompletableFuture<Pi>[] completableFuturePis = Stream.generate(() -> timeToComputeInSeconds)
                .limit(numberOfProbes).map(piSingleService::computeSinglePiAsync).toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(completableFuturePis).join();

        List<Pi> pis = Arrays.stream(completableFuturePis)
                .map(completableFuturePi -> callCatchingErrors(completableFuturePi::get, null)).filter(Objects::nonNull)
                .collect(Collectors.toList());

        logger.info("End computeMultiPisAsync");
        return pis;
    }

    public List<Pi> computeMultiPis(int timeToComputeInSeconds, int numberOfProbes) {
        logger.info("Start computeMultiPis");

        List<Pi> pis = Stream.generate(() -> timeToComputeInSeconds).limit(numberOfProbes)
                .map(piSingleService::computeSinglePi).collect(Collectors.toList());

        logger.info("End computeMultiPis");
        return pis;
    }

    private <T> T callCatchingErrors(Callable<T> callable, T valueOnError) {
        try {
            return callable.call();
        } catch (Exception e) {
            return valueOnError;
        }
    }
}
