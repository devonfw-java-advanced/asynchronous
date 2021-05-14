package com.devonfw.java.training.asynchronous.service;

import java.util.List;
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

    public List<Pi> computeMultiPis(int timeToComputeInSeconds, int numberOfProbes) {
        logger.info("Start computeMultiPis");

        List<Pi> pis = Stream.generate(() -> timeToComputeInSeconds).limit(numberOfProbes)
                .map(piSingleService::computeSinglePi).collect(Collectors.toList());

        logger.info("End computeMultiPis");
        return pis;
    }
}
