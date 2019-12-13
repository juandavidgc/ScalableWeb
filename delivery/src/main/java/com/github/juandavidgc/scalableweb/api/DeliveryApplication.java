package com.github.juandavidgc.scalableweb.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Jgutierrez on 13/12/2019.
 */
@SpringBootApplication
@ComponentScan(value = "com.github.juandavidgc.scalableweb")
public class DeliveryApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DeliveryApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(DeliveryApplication.class, args);
    }

}
