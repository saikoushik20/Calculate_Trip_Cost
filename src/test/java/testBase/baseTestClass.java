package testBase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.io.FileReader;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class baseTestClass {
    public static WebDriver driver;
    public static Logger logger;
    public Properties configProp;



    @BeforeSuite
    public void setup()
    {
        logger= LogManager.getLogger(this.getClass());

        configProp = new Properties();
        try (FileReader file = new FileReader(".//src//test//resources//config.properties")) {
            configProp.load(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ChromeOptions options = new ChromeOptions();
        driver=new ChromeDriver(options);
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(configProp.getProperty("URL"));

    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
