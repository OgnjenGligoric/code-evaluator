package org.example.codeevaluator.codeanalyzer;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethodStyleAnalyzer implements ICodeAnalyzer {
    private List<File> javaFiles;
    private final JavaParser parser;

    public MethodStyleAnalyzer(List<File> javaFiles) {
        this.javaFiles = javaFiles;
        this.parser = new JavaParser();
    }

    public MethodStyleAnalyzer() {
        this.parser = new JavaParser();
    }

    @Override
    public String analyze() {
        return analyze(this.javaFiles);
    }

    @Override
    public String analyze(List<File> javaFiles) {
        int totalMethods = 0;
        int nonConformingMethods = 0;

        for (File file : javaFiles) {
            CompilationUnit cu = parseFile(file.toPath());
            if (cu != null) {
                Map<String, Boolean> methodConformanceMap = analyzeFile(cu);
                totalMethods += methodConformanceMap.size();
                nonConformingMethods += countNonConformingMethods(methodConformanceMap);
            }
        }

        double nonConformingPercentage = calculateNonConformingPercentage(totalMethods, nonConformingMethods);
        return generateReport(nonConformingPercentage);
    }

    private String generateReport(double nonConformingPercentage) {
        return String.format("Code Style Check:\n%.2f%% of methods do not adhere to the naming convention.", nonConformingPercentage);
    }

    private CompilationUnit parseFile(Path filePath) {
        try {
            return parser.parse(filePath).getResult().orElse(null);
        } catch (Exception e) {
            handleParsingError(filePath, e);
            return null;
        }
    }

    private void handleParsingError(Path filePath, Exception e) {
        System.err.println("Failed to parse the file: " + filePath);
        e.printStackTrace();
    }

    private Map<String, Boolean> analyzeFile(CompilationUnit cu) {
        Map<String, Boolean> methodConformanceMap = new HashMap<>();

        cu.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(MethodDeclaration md, Void arg) {
                super.visit(md, arg);
                String methodName = md.getNameAsString();
                boolean conformsToConvention = isCamelCase(methodName);
                methodConformanceMap.put(methodName, conformsToConvention);
            }
        }, null);

        return methodConformanceMap;
    }

    private boolean isCamelCase(String methodName) {
        return methodName.matches("[a-z][a-zA-Z0-9]*");
    }

    private int countNonConformingMethods(Map<String, Boolean> methodConformanceMap) {
        return (int) methodConformanceMap.values().stream().filter(conformsToConvention -> !conformsToConvention).count();
    }

    private double calculateNonConformingPercentage(int totalMethods, int nonConformingMethods) {
        return (double) nonConformingMethods / totalMethods * 100;
    }
}
