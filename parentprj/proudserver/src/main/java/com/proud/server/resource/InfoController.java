package com.proud.server.resource;

import com.proud.commons.AppHelper;
import com.proud.commons.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Info controlelr
 * Created by Fabrizio Faustinoni on 02/10/2016.
 */
@RestController
public class InfoController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private ResourceHelper resourceHelper;


    /*@RequestMapping("/greeting")
    //http://localhost:8080/greeting?name=aaa
    public Info greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Info(counter.incrementAndGet(),
                String.format(template, name));
    }*/

    @RequestMapping("/info")
    public Info greeting() {
        AppHelper helper = new AppHelper();
        return new Info(counter.incrementAndGet(), new DateUtils().getNowDate().toString(),
                resourceHelper.getAppVersion(), helper.getJRE(), helper.getJVM());
    }
}
