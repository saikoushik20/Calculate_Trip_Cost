package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.waits;

import java.util.List;

public class CruiseResultsPage extends basePage {
    public CruiseResultsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath="//div[contains(@class,'product-name')]")
    List<WebElement> cruiseProducts;
    @FindBy(xpath="//div[@class='ship-parames']/span[contains(text(),'Guest capacity:')]")
    WebElement guestCapacityElem;
    @FindBy(xpath="//div[@class='ship-parames']/span[contains(text(),'Renovated:')]")
    WebElement renovatedYear;
    @FindBy(xpath="//div[contains(@class,'cruise-ids')]/span[contains(@class,'cruise-id') and contains(text(),'Cruise ID:')]")
    WebElement cruiseId;
    @FindBy(xpath="//i[contains(@class,'fi-close')]")
    List<WebElement> closeButtons;

    public void switchToLastWindow() {
        List<String> windowList = new java.util.ArrayList<>(driver.getWindowHandles());
        if (windowList.size() > 1) {
            driver.switchTo().window(windowList.getLast());
        }
    }

    public void selectFirstCruiseProduct() {
        if (!cruiseProducts.isEmpty()) {
            WebElement firstCruise = cruiseProducts.getFirst();
            waits.waitForElementToBeClickable(driver, firstCruise, 10).click();
        }
    }

    public String getGuestCapacity() {
        try {
            waits.waitForVisibility(driver, guestCapacityElem, 10);
            return guestCapacityElem.getText().replace("Guest capacity:", "").trim();
        } catch (Exception e) {
            return "N/A";
        }
    }

    public String getRenovatedYear() {
        try {
            waits.waitForVisibility(driver, renovatedYear, 10);
            return renovatedYear.getText().replace("Renovated:", "").trim();
        } catch (Exception e) {
            return "N/A";
        }
    }

    public String getCruiseId() {
        try {
            String cruiseIdText = cruiseId.getText();
            String value = cruiseIdText.replace("Cruise ID:", "").trim();
            return value;
        } catch (Exception e) {
            System.out.println("[DEBUG] Cruise ID not found: " + e.getMessage());
            return "N/A";
        }
    }
    public void closeAllPopups() {
        for (WebElement btn : closeButtons) {
            if (btn.isDisplayed() && btn.isEnabled()) {
                btn.click();
            }
        }
    }
    public void waitForCruiseProductList() {
        waits.waitForVisibility(driver, By.xpath("//div[contains(@class,'product-name')]"), 10);
    }

    public void waitForCruiseDetailsPage() {
        waits.waitForVisibility(driver, By.xpath("//div[@class='ship-parames']"), 10);
    }

}
