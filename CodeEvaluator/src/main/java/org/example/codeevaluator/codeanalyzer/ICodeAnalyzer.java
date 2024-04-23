package org.example.codeevaluator.codeanalyzer;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ICodeAnalyzer {
    public List<Map.Entry<String, Integer>> analyze();
    public List<Map.Entry<String, Integer>> analyze(List<File> javaFiles);
}
