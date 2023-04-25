package com.nineplus.localhand;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
public class LocalhandApplication {
    private static final Logger logger = LogManager.getLogger(LocalhandApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(LocalhandApplication.class, args);
    }

}
