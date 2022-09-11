package com.devonfw.java.training.asynchronous.service;

import com.devonfw.java.training.asynchronous.entity.Pi;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.Future;

@Service
public class PiSingleService {

    @Async("async")
    public Future<Pi> computeSinglePiAsync(int timeToComputeInSeconds) {
        return new AsyncResult<>(computeSinglePi(timeToComputeInSeconds));
    }

    public Pi computeSinglePi(int timeToComputeInSeconds) {
        long nThrows = 0;
        long nHits = 0;

        Instant now = Instant.now();
        Instant end = now.plusSeconds(timeToComputeInSeconds);
        while (end.isAfter(now)) {
            // throw dart and count
            nThrows++;
            if (whetherTheDartHit()) {
                nHits++;
            }

            now = Instant.now();
        }

        return computePiUsingThrowsAndHits(nThrows, nHits);
    }

    private boolean whetherTheDartHit() {
        double x = Math.random(), y = Math.random();
        return x * x + y * y <= 1.0;
    }

    private Pi computePiUsingThrowsAndHits(long nThrows, long nSuccess) {
        double computedPi = 4 * (double) nSuccess / (double) nThrows;
        double error = Math.abs(Math.PI - computedPi);
        return new Pi(computedPi, error);
    }
}
