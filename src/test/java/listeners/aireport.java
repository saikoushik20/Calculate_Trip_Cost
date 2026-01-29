package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class aireport implements ITestListener {

    private static String reportFile = "TrendyReport.html";
    private static FileWriter writer;
    private static ByteArrayOutputStream consoleOut = new ByteArrayOutputStream();
    private static PrintStream ps = new PrintStream(consoleOut);

    @Override
    public void onStart(ITestContext context) {
        // Redirect console output
        System.setOut(ps);
        System.setErr(ps);

        try {
            writer = new FileWriter(reportFile);
            writer.write("<html><head><title>Selenium Trendy Report</title>");
            writer.write("<style>");
            writer.write("body{font-family:'Segoe UI',sans-serif;background:#f4f6f9;margin:20px;}");
            writer.write("h1{color:#333;text-align:center;}");
            writer.write("table{border-collapse:collapse;width:100%;box-shadow:0 4px 12px rgba(0,0,0,0.1);}");
            writer.write("th{background:#4CAF50;color:white;padding:12px;text-transform:uppercase;}");
            writer.write("td{padding:10px;border-bottom:1px solid #ddd;transition:background 0.3s ease;}");
            writer.write("tr:hover{background:#f1f1f1;}");
            writer.write(".pass{color:#2e7d32;font-weight:bold;animation:fadeIn 1s;}");
            writer.write(".fail{color:#c62828;font-weight:bold;animation:shake 0.5s;}");
            writer.write(".skip{color:#f9a825;font-weight:bold;animation:fadeIn 1s;}");
            writer.write("img{border-radius:8px;box-shadow:0 2px 6px rgba(0,0,0,0.2);transition:transform 0.3s;}");
            writer.write("img:hover{transform:scale(1.5);}");
            writer.write("details{margin-top:5px;} summary{cursor:pointer;font-weight:bold;color:#1976d2;}");
            writer.write("@keyframes fadeIn{from{opacity:0;}to{opacity:1;}}");
            writer.write("@keyframes shake{0%{transform:translateX(0);}25%{transform:translateX(-5px);}50%{transform:translateX(5px);}75%{transform:translateX(-5px);}100%{transform:translateX(0);}}");
            writer.write("</style></head><body>");
            writer.write("<h1>🚀 Selenium Test Execution Report</h1>");
            writer.write("<table>");
            writer.write("<tr><th>Test Name</th><th>Status</th><th>Screenshot</th><th>Timestamp</th><th>Logs</th><th>AI Suggestion</th></tr>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logResult(result, "PASSED", null);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = (WebDriver) result.getTestContext().getAttribute("driver");
        String screenshotBase64 = captureScreenshotBase64(driver);
        logResult(result, "FAILED", screenshotBase64);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logResult(result, "SKIPPED", null);
    }

    @Override
    public void onFinish(ITestContext context) {
        try {
            writer.write("</table></body></html>");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logResult(ITestResult result, String status, String screenshotBase64) {
        try {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            writer.write("<tr>");
            writer.write("<td>" + result.getName() + "</td>");
            writer.write("<td class='" + status.toLowerCase() + "'>" + status + "</td>");

            if (screenshotBase64 != null) {
                writer.write("<td><img src='data:image/png;base64," + screenshotBase64 + "' width='200'/></td>");
            } else {
                writer.write("<td>N/A</td>");
            }

            writer.write("<td>" + timeStamp + "</td>");

            // Logs
            String logs = "";
            if (result.getThrowable() != null) {
                logs = result.getThrowable().toString().replace("\n", "<br>");
            }
            logs += "<br>" + consoleOut.toString().replace("\n", "<br>");
            writer.write("<td><details><summary>View Logs</summary><div style='max-height:150px;overflow:auto;'>" + logs + "</div></details></td>");

            // AI heuristic suggestion
            writer.write("<td>" + getSuggestion(result.getThrowable()) + "</td>");

            writer.write("</tr>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String captureScreenshotBase64(WebDriver driver) {
        if (driver == null) return null;
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getSuggestion(Throwable t) {
        if (t == null) return "✅ All good!";
        String msg = t.toString();
        if (msg.contains("NoSuchElementException")) {
            return "🔍 Suggestion: Verify locator or add explicit waits.";
        } else if (msg.contains("TimeoutException")) {
            return "⏱ Suggestion: Increase wait time or check page load.";
        } else if (msg.contains("AssertionError")) {
            return "📊 Suggestion: Validate expected values and test data.";
        }
        return "🤔 Suggestion: Review stack trace for root cause.";
    }
}
