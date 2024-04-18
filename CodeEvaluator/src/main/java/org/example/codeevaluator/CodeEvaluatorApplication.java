package org.example.codeevaluator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CodeEvaluatorApplication {

    public static void main(String[] args) {
        String directoryPath = System.getenv("CODEBASE_DIRECTORY_PATH");
        if (directoryPath != null) {
            System.out.println(directoryPath);
        } else {
            System.out.println("Please set the MYAPP_DIRECTORY_PATH environment variable.");
        }
    }

}
