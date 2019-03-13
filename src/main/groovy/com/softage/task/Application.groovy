package com.softage.task

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan
class Application {

    static void main(String[] args) {
        SpringApplication.run( Application, args )
    }

}
