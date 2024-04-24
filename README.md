Code Evaluator
==============

### Setup

-----

*   Set the environment variable `CODEBASE_REPOSITORY_PATH` in the run configuration to specify the path of the codebase repository you would like to evaluate. Example: `CODEBASE_REPOSITORY_PATH=D:\Example\Path`.
*   The Java Spring Boot application was developed using Java 17.
*   Dependency-wise, this application is using `com.github.javaparser version 3.24.0.`

### Overview

--------

This Spring Boot Java application evaluates basic aspects of code quality within the codebase. The main goal was to create a flexible, maintainable application that follows the **Open Closed Principle**. Achieving this involved extending functionality without modifying existing code by utilizing Dependency Injection.

*   Repository parsing and finding Java files.
*   Parsing the files and counting conditional statements using the **Visitor Pattern**.
*   Calculating the percentage of methods that do not adhere to the naming convention.
*   Utilizing **Dependency Injection** inside the client to orchestrate and analyze the codebase.

### Client Workflow

-----------------------------------------

1.  **File Parsing**:

    *   Initializes a `JavaRepositoryParser` instance with the `repositoryPath`. This parser is responsible for parsing Java files from the specified repository path.
2.  **Code Analyzers Initialization**:

    *   Instantiates two code analyzers: `MethodConditionalAnalyzer` and `MethodStyleAnalyzer`. These analyzers implement the same `ICodeAnalyzer` interface.
3.  **Dependency Injection**:

    *   Injects the `MethodConditionalAnalyzer` and `MethodStyleAnalyzer` instances into the `analyzeRepository()` method.
4.  **Analysis Execution**:

    *   Creates a list of `ICodeAnalyzer` instances (`codeAnalyzers`) and adds the `methodConditionalAnalyzer` and `methodStyleAnalyzer` to it.
    *   Iterates over each `ICodeAnalyzer` instance in the list and calls the `analyze()` method, passing the list of Java files (`javaFiles`) as a parameter.
    *   Each analyzer performs its specific analysis on the codebase and prints the result.