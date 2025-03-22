package com.example.monitoring;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api")
public class MonitoringController {
    private static final Logger logger = LoggerFactory.getLogger(MonitoringController.class);
    private final Counter requestCounter;
    private final Timer requestLatency;
    private final Counter logLineCounter;

    public MonitoringController(MeterRegistry registry) {
        this.requestCounter = registry.counter("http_requests_total");
        this.requestLatency = registry.timer("http_request_latency");
        this.logLineCounter = registry.counter("log_lines_total");
    }

    @GetMapping("/hello")
    public String hello() {
        long start = System.nanoTime();
        requestCounter.increment();
        logger.info("Handling request to /hello");
        logLineCounter.increment();
        requestLatency.record(System.nanoTime() - start, TimeUnit.NANOSECONDS);
        return "Hello, world!";
    }
}