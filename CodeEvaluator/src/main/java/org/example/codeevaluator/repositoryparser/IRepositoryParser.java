package org.example.codeevaluator.repositoryparser;

import java.io.File;
import java.util.List;

public interface IRepositoryParser {
    public List<File> getFiles();
    public List<File> getFiles(String repositoryPath);
}
