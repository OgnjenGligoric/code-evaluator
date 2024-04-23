package org.example.codeevaluator;

import org.example.codeevaluator.client.JavaEvaluatorClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CodeEvaluatorApplication {

    public static void main(String[] args) {
        String directoryPath = System.getenv("CODEBASE_DIRECTORY_PATH");
        if (directoryPath != null) {
            JavaEvaluatorClient javaEvaluatorClient = new JavaEvaluatorClient(directoryPath);
            javaEvaluatorClient.analyzeRepository();
        } else {
            System.out.println("Please set the MYAPP_DIRECTORY_PATH environment variable.");
        }
    }

}
