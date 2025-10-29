package com.example.crewstation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class CrewStationApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrewStationApplication.class, args);
    }

}
