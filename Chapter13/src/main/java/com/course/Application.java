package com.course;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PreDestroy;

@EnableScheduling
@SpringBootApplication
public class Application {
    private  static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        try {
            Application.context = SpringApplication.run(Application.class, args);
        }catch(Exception e) {
            System.out.println("SpringApplication----->" + e.getMessage());
        }
    }

    @PreDestroy  //预损毁
    public void close(){
        Application.context.close();
    }
}
