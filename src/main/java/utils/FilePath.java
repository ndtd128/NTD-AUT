package utils;

import java.io.File;

public final class FilePath {

    public final static String clonedProjectPath = (new File("src/main/java/clonedProject")).getAbsolutePath();
    public final static String uploadedProjectPath = (new File("src/main/uploadedProject")).getAbsolutePath();
    public final static String concreteExecuteResultPath = (new File("src/main/java/utils/autoUnitTestUtil/concreteExecuteResult.txt")).getAbsolutePath();
    public final static String generatedTestDataPath = (new File("src/main/java/utils/autoUnitTestUtil/generatedTestData.txt")).getAbsolutePath();
    public final static String targetClassesFolderPath = (new File("target/classes")).getAbsolutePath();
}
