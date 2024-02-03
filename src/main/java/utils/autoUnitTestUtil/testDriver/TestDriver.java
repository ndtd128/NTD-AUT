package utils.autoUnitTestUtil.testDriver;
import java.io.FileWriter;
public class TestDriver {
private static boolean mark(String statement, boolean isTrueCondition, boolean isFalseCondition) {
StringBuilder markResult = new StringBuilder();
markResult.append(statement).append("===");
markResult.append(isTrueCondition).append("===");
markResult.append(isFalseCondition).append("---end---");
writeDataToFile(markResult.toString(), "src/main/java/utils/autoUnitTestUtil/concreteExecuteResult.txt", true);
if (!isTrueCondition && !isFalseCondition) return true;
return !isFalseCondition;
}
private static void writeDataToFile(String data, String path, boolean append) {
try {
FileWriter writer = new FileWriter(path, append);
writer.write(data);
writer.close();
} catch(Exception e) {
e.printStackTrace();
}
}
public static boolean isPowerTwo(int number)
{
if (((number <= 0) && mark("number <= 0", true, false)) || mark("number <= 0", false, true))
{
{
mark("return false;\n", false, false);
return false;
}
}
mark("int ans=number & (number - 1);\n", false, false);
int ans=number & (number - 1);
mark("return ans == 0;\n", false, false);
return ans == 0;
}

public static void main(String[] args) {
writeDataToFile("", "core-engine/cfg/src/main/java/data/testDriverData/runTestDriverData.txt", false);
long startRunTestTime = System.nanoTime();
Object output = isPowerTwo(0);
long endRunTestTime = System.nanoTime();
double runTestDuration = (endRunTestTime - startRunTestTime) / 1000000.0;
writeDataToFile(runTestDuration + "===" + output, "src/main/java/utils/autoUnitTestUtil/concreteExecuteResult.txt", true);
}
}