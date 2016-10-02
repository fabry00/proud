package com.proud.server.domain;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Info controlelr
 * Created by fabry on 02/10/2016.
 */
public class InfoController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Info greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Info(counter.incrementAndGet(),
                String.format(template, name));
    }
}
