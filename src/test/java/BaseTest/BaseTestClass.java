package BaseTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

public class BaseTestClass {
    public static WebDriver driver;
    public static Logger logger;
    public static Properties configProp;

    @BeforeSuite
    @Parameters({"browser","os"})
    public void setup(String browser, String os) {
        logger= LogManager.getLogger(this.getClass());

        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("edge")) {
            EdgeOptions options = new EdgeOptions();
            driver = new EdgeDriver(options);
        } else {
            logger.error("Browser not supported: ", browser);
            System.out.println("Invalid browser! Supported browsers are: chrome, edge.");
            return;
        }
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        configProp = new Properties();
        try (FileReader file = new FileReader(".//src//test//resources//config.properties")) {
            configProp.load(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        driver.get(configProp.getProperty("URL"));

    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public String captureScreen(String tName) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String targetFilePath=System.getProperty("user.dir")+"\\Failed Screenshots\\" + tName + "_" + timeStamp + ".png";
        File targetFile=new File(targetFilePath);
        sourceFile.renameTo(targetFile);
        return targetFilePath;
    }
}
