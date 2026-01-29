package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.waits;

import java.util.List;


public class HotelResultsPage extends basePage{
    public HotelResultsPage(WebDriver driver)
    {
        super(driver);
        // ...existing code...
    }

    // Updated selector to be more robust (try a generic heading or fallback to xpath)
    @FindBy(xpath = "//div[contains(@class,'hotel-count-tip') and @role='heading']")
    WebElement resultsCount;
    @FindBy(xpath="//div[contains(@class,'style_radio-item__6Kt3T') and @role='checkbox' and @aria-label='Pool']")
    WebElement poolFilter;
    @FindBy(xpath="//div[contains(@class,'hotel-card')]")
    List<WebElement> hotelCards;
    @FindBy(xpath="//span[contains(@class,'style_dropdown-selector__iZHJ2') and .//span[contains(text(),'Trip.com recommended')]]")//Recommended for Families
    WebElement sortDropdown;
    @FindBy(xpath="//span[contains(@class,'style_dropdown-selector-options__BelBO') and normalize-space(text())='Top reviewed']")
    WebElement topReviewedOption;


    public String getResultsCount() {
        try {
            // Wait for the element to be visible before accessing
            WebElement countElem = waits.waitForVisibility(driver, By.xpath("//div[contains(@class,'hotel-count-tip') and @role='heading']"), 10);
            return countElem.getAttribute("aria-label");
        } catch (Exception e) {
            System.out.println("[DEBUG] Hotel results count element not found: " + e.getMessage());
            return "N/A";
        }
    }
    public void applyPoolFilter() {
        waits.waitForElementToBeClickable(driver, poolFilter, 10).click();
    }
    public void refreshPage() {
        driver.navigate().refresh();
    }
    public List<WebElement> getHotelCards() {
        return hotelCards;
    }
    public void sortByTopReviewed() {
        waits.waitForVisibility(driver, sortDropdown, 10);
        waits.waitForElementToBeClickable(driver, sortDropdown, 10).click();
        waits.waitForVisibility(driver, topReviewedOption, 10);
        waits.waitForElementToBeClickable(driver, topReviewedOption, 10).click();
    }

}
