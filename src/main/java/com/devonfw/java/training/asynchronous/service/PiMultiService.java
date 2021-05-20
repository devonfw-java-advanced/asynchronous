package com.devonfw.java.training.asynchronous.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.devonfw.java.training.asynchronous.entity.Pi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PiMultiService {

    @Autowired
    private PiSingleService piSingleService;

    public List<Pi> computeMultiPis(int timeToComputeInSeconds, int numberOfProbes) {
        return Stream.generate(() -> timeToComputeInSeconds).limit(numberOfProbes).map(piSingleService::computeSinglePi)
                .collect(Collectors.toList());
    }
}
