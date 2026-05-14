package com.tt1.simwebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.tt1.simwebapp.logica", "com.tt1.simwebapp"})
public class TrabajoApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(TrabajoApplication.class, args);
    }

}
