package com.dzablotny.morgue;

import com.dzablotny.morgue.jms.configuration.MorgueJMSConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@ComponentScan(basePackages = "com.dzablotny")
@Import(MorgueJMSConfiguration.class)
@SpringBootApplication
public class MorgueApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MorgueApplication.class, args);
    }
}