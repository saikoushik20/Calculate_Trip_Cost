package Utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Waits {
    public static WebElement waitForElementToBeClickable(WebDriver driver, WebElement element, int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitForElementToBeClickable(WebDriver driver, By locator, int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForVisibility(WebDriver driver, WebElement element, int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForVisibility(WebDriver driver, By locator, int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static boolean waitForInvisibility(WebDriver driver, WebElement element, int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.invisibilityOf(element));
    }

    public static boolean waitForUrlContains(WebDriver driver, String urlFragment, int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.urlContains(urlFragment));
    }
}
