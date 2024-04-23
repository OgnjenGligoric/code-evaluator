package org.example.codeevaluator.codeanalyzer;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class MethodConditionalAnalyzer implements ICodeAnalyzer {
    private List<File> javaFiles;
    private JavaParser parser;

    public MethodConditionalAnalyzer(List<File> javaFiles) {
        this.javaFiles = javaFiles;
        this.parser = new JavaParser();
    }

    public MethodConditionalAnalyzer() {
        this.parser = new JavaParser();
    }

    public String analyze(List<File> javaFiles) {
        this.javaFiles = javaFiles;

        Map<String, Integer> methodConditionsCount = new HashMap<>();

        for (File file : javaFiles) {
            CompilationUnit cu = parseFile(file.toPath());
            if (cu != null) {
                analyzeFile(cu, file, methodConditionsCount);
            }
        }

        return generateReport(methodConditionsCount);
    }

    public String analyze() {
        return this.analyze(this.javaFiles);
    }

    private CompilationUnit parseFile(Path filePath) {
        try {
            return parser.parse(filePath).getResult().orElse(null);
        } catch (IOException e) {
            System.err.println("Failed to parse the file: " + filePath);
            return null;
        }
    }

    private void analyzeFile(CompilationUnit cu, File file, Map<String, Integer> countMap) {
        cu.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(MethodDeclaration md, Void arg) {
                super.visit(md, arg);
                int count = countConditionalStatements(md);
                String identifier = md.getNameAsString() + " in " + file.getName();
                countMap.merge(identifier, count, Integer::sum);
            }
        }, null);
    }

    private List<Map.Entry<String, Integer>> sortAndTrimResults(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> sortedMethods = new ArrayList<>(map.entrySet());
        sortedMethods.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        return sortedMethods.size() > 3 ? sortedMethods.subList(0, 3) : sortedMethods;
    }

    private String generateReport(Map<String, Integer> methodConditionsCount) {
        StringBuilder report = new StringBuilder();
        List<Map.Entry<String, Integer>> sortedMethods = sortAndTrimResults(methodConditionsCount);

        report.append("Top 3 methods with the most conditional statements:\n");
        for (int i = 0; i < sortedMethods.size() && i < 3; i++) {
            Map.Entry<String, Integer> entry = sortedMethods.get(i);
            report.append(String.format("%d. %s: %d conditional statements\n", i + 1, entry.getKey(), entry.getValue()));
        }

        return report.toString();
    }

    private int countConditionalStatements(MethodDeclaration method) {
        final int[] count = {0};

        method.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(IfStmt n, Void arg) { count[0]++; super.visit(n, arg); }
            @Override
            public void visit(SwitchStmt n, Void arg) { count[0]++; super.visit(n, arg); }
            @Override
            public void visit(ForStmt n, Void arg) { count[0]++; super.visit(n, arg); }
            @Override
            public void visit(WhileStmt n, Void arg) { count[0]++; super.visit(n, arg); }
            @Override
            public void visit(DoStmt n, Void arg) { count[0]++; super.visit(n, arg); }
        }, null);

        return count[0];
    }

    public List<File> getJavaFiles() {
        return javaFiles;
    }

    public void setJavaFiles(List<File> javaFiles) {
        this.javaFiles = javaFiles;
    }

    public JavaParser getParser() {
        return parser;
    }

    public void setParser(JavaParser parser) {
        this.parser = parser;
    }
}
