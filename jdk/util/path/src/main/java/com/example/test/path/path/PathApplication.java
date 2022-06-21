package com.example.test.path.path;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PathApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(PathApplication.class, args);
        Path.testPath();
        //Path.getResource();

        System.exit(0);

    }

}
