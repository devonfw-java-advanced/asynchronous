package com.devonfw.java.training.asynchronous.service;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;

import com.devonfw.java.training.asynchronous.entity.Pi;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class PiSingleService {

    @Async
    public CompletableFuture<Pi> computeSinglePiAsync(int timeToComputeInSeconds) {
        Pi pi = computeSinglePi(timeToComputeInSeconds);
        return CompletableFuture.completedFuture(pi);
    }

    public Pi computeSinglePi(int timeToComputeInSeconds) {
        long nThrows = 0;
        long nHits = 0;

        Instant now = Instant.now();
        Instant end = now.plusSeconds(timeToComputeInSeconds);
        while (end.isAfter(now)) {
            // trow dart and count
            nThrows++;
            if (whetherTheDartHit()) {
                nHits++;
            }

            now = Instant.now();
        }

        Pi pi = computePiUsingThrowsAndHits(nThrows, nHits);

        return pi;
    }

    private boolean whetherTheDartHit() {
        // throwing a dart takes some time
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            // ignore
        }

        double x = Math.random(), y = Math.random();
        return x * x + y * y <= 1.0;
    }

    private Pi computePiUsingThrowsAndHits(long nThrows, long nSuccess) {
        double computedPi = 4 * (double) nSuccess / (double) nThrows;
        double error = Math.abs(Math.PI - computedPi);
        return new Pi(computedPi, error);
    }
}
