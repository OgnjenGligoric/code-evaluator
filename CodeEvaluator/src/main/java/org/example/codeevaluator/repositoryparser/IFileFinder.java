package org.example.codeevaluator.repositoryparser;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public interface IFileFinder {
    public List<File> getFiles();
    public List<File> getFiles(String repositoryPath);
}
