package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import Utilities.Waits;

import java.util.List;

public class HotelResultsPage extends BasePage {
    public HotelResultsPage(WebDriver driver)
    {
        super(driver);
    }

    @FindBy(xpath = "//div[contains(@class,'hotel-count-tip') and @role='heading']")
    WebElement resultsCount;
    @FindBy(xpath="//div[contains(@class,'style_radio-item__6Kt3T') and @role='checkbox' and @aria-label='Pool']")
    WebElement poolFilter;
    @FindBy(xpath="//div[contains(@class,'hotel-card')]")
    List<WebElement> hotelCards;
    @FindBy(xpath="//span[contains(@class,'style_dropdown-selector__iZHJ2') and .//span[contains(text(),'Trip.com recommended')]]")
    WebElement sortDropdown;
    @FindBy(xpath="//span[contains(@class,'style_dropdown-selector-options__BelBO') and normalize-space(text())='Top reviewed']")
    WebElement topReviewedOption;

    public String getResultsCount() {
        try {
            return resultsCount.getAttribute("aria-label");
        } catch (Exception e) {
            System.out.println("Hotel results count element not found: " + e.getMessage());
            return "N/A";
        }
    }
    public void applyPoolFilter() {
        Waits.waitForElementToBeClickable(driver, poolFilter, 10).click();
    }
    public void refreshPage() {
        driver.navigate().refresh();
    }
    public List<WebElement> getHotelCards() {
        return hotelCards;
    }
    public void sortByTopReviewed() {
        Waits.waitForVisibility(driver, sortDropdown, 10);
        Waits.waitForElementToBeClickable(driver, sortDropdown, 10).click();
        Waits.waitForVisibility(driver, topReviewedOption, 10);
        Waits.waitForElementToBeClickable(driver, topReviewedOption, 10).click();
    }
}
