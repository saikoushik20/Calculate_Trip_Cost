package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utilities.waits;

import java.util.*;

public class homepage extends basePage{

    public homepage(WebDriver driver)
    {
        super(driver);
    }

    @FindBy(xpath="//i[contains(@class,'fi-close')]")
    List<WebElement> closeButtons;
    @FindBy(id="destinationInput")
    WebElement destinationInput;
    @FindBy(id="checkInInput")
    WebElement checkInInput;
    @FindBy(id="checkOutInput")
    WebElement checkOutInput;
    @FindBy(css = "div.c-calendar-month__title")
    WebElement calendarTitle;
    @FindBy(xpath="//div[@class='hBD91TxW6Uv2IEd2ZK_X']/i")
    WebElement guestSelector;
    @FindBy(xpath="(//i[@class='smarticon u-icon u-icon-ic_bestir_plus u-icon_ic_bestir_plus K6h5Q4uKXnll47o5TCH7'])[2]")
    WebElement addAdultsButton;
    @FindBy(xpath="//div[@class='OVZS4orBSAXrR1ijARZy']")
    WebElement confirmGuestsButton;
    @FindBy(xpath="//span[@class='tripui-online-btn-content-children ' and text()='Search']")
    WebElement finalSearch;

    public void closePopup() {
        try {
            for (WebElement closeIcon : closeButtons) {
                if (closeIcon.isDisplayed() && closeIcon.isEnabled()) {
                    closeIcon.click();
                    break;
                }
            }
        } catch (Exception ignored) {
        }
    }

    public void enterDestination(String destination) {
        destinationInput.sendKeys(destination);
    }

    public void openCheckInDate() {
        waits.waitForVisibility(driver, checkInInput, 10);
        waits.waitForElementToBeClickable(driver, checkInInput, 10).click();
    }

    public void openCheckOutDate() {
        waits.waitForVisibility(driver, checkOutInput, 10);
        waits.waitForElementToBeClickable(driver, checkOutInput, 10).click();
    }

//    public void selectDate(int day) {
//        waits.waitForVisibility(driver, calendarTitle, 10);
//        By daySelector = By.xpath("//li[@role='button' and not(contains(@class,'is-disable'))]//span[@class='day' and text()='" + day + "']");
//        waits.waitForElementToBeClickable(driver, daySelector, 10).click();
//    }
    public void selectDate(int day) {
        waits.waitForVisibility(driver, calendarTitle, 10);
        By daySelector = By.xpath("//li[@role='button' and not(contains(@class,'is-disable'))]//span[@class='day' and text()='" + day + "']");
        WebElement dateElement = waits.waitForElementToBeClickable(driver, daySelector, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", dateElement);
    }



    public void openGuestSelector() {
        waits.waitForVisibility(driver, guestSelector, 10);
        WebElement guestElem = waits.waitForElementToBeClickable(driver, guestSelector, 10);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", guestElem);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", guestElem);
    }

    public void addAdults(int count){
        for (int i = 0; i < count; i++) {
            WebElement addBtn = waits.waitForElementToBeClickable(driver, addAdultsButton, 10);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", addBtn);
        }
    }

    public void confirmGuests() {
        WebElement btn = waits.waitForElementToBeClickable(driver, confirmGuestsButton, 10);
        assert btn != null;
        btn.click();
        waits.waitForInvisibility(driver, confirmGuestsButton, 10);
    }

    public void clickSearch() {
        WebElement btn = waits.waitForElementToBeClickable(driver, finalSearch, 10);
        assert btn != null;
        btn.click();
    }

}