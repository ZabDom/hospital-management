package com.dzablotny.fancy;

import com.dzablotny.fancy.jms.configuration.FancyJMSConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@EnableCaching
@ComponentScan(basePackages = "com.dzablotny")
@Import(FancyJMSConfiguration.class)
@SpringBootApplication()
public class FancyApplication {

    public static void main(String[] args) {
        SpringApplication.run(FancyApplication.class, args);
    }
}