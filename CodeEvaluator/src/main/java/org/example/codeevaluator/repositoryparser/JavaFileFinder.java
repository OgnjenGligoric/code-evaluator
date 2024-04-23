package org.example.codeevaluator.repositoryparser;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Searches for Java source files in a given directory and its subdirectories.
 */
public class JavaFileFinder implements IFileFinder {
    private Path repositoryPath;
    private List<File> javaFiles;

    /**
     * Constructs a new JavaFileFinder.
     * @param repositoryPath the root directory path to search for Java files.
     */
    public JavaFileFinder(String repositoryPath) {
        this.repositoryPath = Paths.get(repositoryPath);
        this.javaFiles = new ArrayList<>();
        verifyPath();
    }

    public JavaFileFinder(){}

    /**
     * Ensures the provided path exists.
     */
    private void verifyPath() {
        if (!Files.exists(repositoryPath)) {
            throw new IllegalArgumentException("The provided path does not exist: " + repositoryPath);
        }
    }

    /**
     * Returns the list of Java files found.
     * @return a list of Java files.
     */
    public List<File> getFiles(String repositoryPath) {
        this.repositoryPath = Paths.get(repositoryPath);
        this.javaFiles = new ArrayList<>();
        verifyPath();
        try {
            searchFiles();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return javaFiles;
    }

    public List<File> getFiles() {
        this.javaFiles = new ArrayList<>();
        verifyPath();
        try {
            searchFiles();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return javaFiles;
    }


    /**
     * Initiates the search for Java files in the directory path.
     * @throws IOException if an I/O error is encountered.
     */
    private void searchFiles() throws IOException {
        Files.walkFileTree(repositoryPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (file.toString().endsWith(".java")) {
                    javaFiles.add(file.toFile());
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                System.err.println("Failed to access file: " + file + " due to: " + exc);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}