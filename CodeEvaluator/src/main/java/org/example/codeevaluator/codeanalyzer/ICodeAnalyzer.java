package org.example.codeevaluator.codeanalyzer;

import java.io.File;
import java.util.List;

public interface ICodeAnalyzer {
    public String analyze();
    public String analyze(List<File> javaFiles);
}
