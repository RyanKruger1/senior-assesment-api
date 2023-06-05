package core;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class APITestListener implements ITestListener {

    static PrintWriter pw;

    @Override
    public void onFinish(ITestContext Result) {
        pw.append("</table>");
        pw.append("</html>");
        pw.close();
    }

    @Override
    public void onStart(ITestContext Result) {
        createFileAndFolder();
    }

    @Override
    public void onTestFailure(ITestResult Result) {
        addToReportTable("FAIL", Result.getTestClass() + ":" + Result.getName(), Result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult Result) {
        addToReportTable("Skipped", Result.getName(), Result.getThrowable().getMessage());
    }

    @Override
    public void onTestStart(ITestResult Result) {
        addToReportTable("START", Result.getName(), null);
    }

    @Override
    public void onTestSuccess(ITestResult Result) {
        addToReportTable("SUCCESS", Result.getName(), null);
    }

    public void addToReportTable(String status, String testName, String info) {
        pw.append("<tr><td>" + getDate(false) + "</td><td>" + status + "</td><td>" + testName + "</td><td>" + info + "</td></tr>");
    }

    public void createFileAndFolder() {
        try {
            File outputDirectory = new File(System.getProperty("user.dir") + File.separator + "out");
            if (!outputDirectory.exists()) {
                outputDirectory.mkdir();
            }

            File f = new File(System.getProperty("user.dir") + File.separator + "out" + File.separator + getDate(true) + ".html");
            if (!f.exists()) {
                f.createNewFile();
            }
            pw = new PrintWriter(f);
            pw.append("<html>");
            addStyle();
            pw.append("<header>Test run for " + getDate(false) + "</header>");
            pw.append("<table>");
            pw.append("<tr><th>Time</th><th>Status</th><th>Test Name</th><th>Info</th></tr>");
        } catch (FileNotFoundException e) {
            System.out.println("Could not find file");
        } catch (IOException e) {
            System.out.println("Could not create file");
        }
    }

    public String getDate(boolean isFileName) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter;
        if (isFileName) {
            formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        } else {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        }
        String formattedDateTime = currentDateTime.format(formatter);
        return formattedDateTime;
    }

    public void addStyle() {
        pw.println("<style>");
        pw.println("table {");
        pw.println("    border-collapse: collapse;");
        pw.println("    width: 100%;");
        pw.println("}");
        pw.println("th, td {");
        pw.println("    border: 1px solid #ddd;");
        pw.println("    padding: 8px;");
        pw.println("}");
        pw.println("th {");
        pw.println("    background-color: #f2f2f2;");
        pw.println("}");
        pw.println("</style>");
    }
}
