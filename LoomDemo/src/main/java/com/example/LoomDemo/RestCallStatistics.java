package com.example.LoomDemo;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class RestCallStatistics {

    private final Map<String, Long> timeMap = Collections.synchronizedMap(new HashMap<>());

    public void addTiming(String storeName, long time) {
        timeMap.put(storeName, time);
    }

    public Map<String, Long> getTimeMap() {
        return timeMap;
    }
}
