package org.example.codeevaluator.client;

import org.example.codeevaluator.codeanalyzer.ICodeAnalyzer;
import org.example.codeevaluator.codeanalyzer.MethodConditionalAnalyzer;
import org.example.codeevaluator.codeanalyzer.MethodStyleAnalyzer;
import org.example.codeevaluator.repositoryparser.IRepositoryParser;
import org.example.codeevaluator.repositoryparser.JavaRepositoryParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JavaEvaluatorClient {

    private String repositoryPath;
    public JavaEvaluatorClient() {
    }
    public JavaEvaluatorClient(String repositoryPath){
        this.repositoryPath = repositoryPath;
    }

    public void analyzeRepository(){
        IRepositoryParser fileParser = new JavaRepositoryParser(repositoryPath);
        List<File> javaFiles = fileParser.getFiles();

        MethodConditionalAnalyzer methodConditionalAnalyzer = new MethodConditionalAnalyzer();
        MethodStyleAnalyzer methodStyleAnalyzer = new MethodStyleAnalyzer();

        List<ICodeAnalyzer> codeAnalyzers = new ArrayList<>();
        codeAnalyzers.add(methodConditionalAnalyzer);
        codeAnalyzers.add(methodStyleAnalyzer);

        // Dependency injection
        for(ICodeAnalyzer codeAnalyzer : codeAnalyzers){
            System.out.println(codeAnalyzer.analyze(javaFiles));
        }
    }
}
