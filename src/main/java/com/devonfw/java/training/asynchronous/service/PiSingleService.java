package com.devonfw.java.training.asynchronous.service;

import java.time.Instant;

import com.devonfw.java.training.asynchronous.entity.Pi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PiSingleService {

    Logger logger = LoggerFactory.getLogger(PiSingleService.class);

    public Pi computeSinglePi(int timeToComputeInSeconds) {
        logger.info("Start computeSinglePi");

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

        logger.info("End computeSinglePi");
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
