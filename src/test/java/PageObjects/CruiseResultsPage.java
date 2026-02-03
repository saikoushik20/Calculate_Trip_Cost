package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import Utilities.Waits;

import java.util.List;

public class CruiseResultsPage extends BasePage {
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
    @FindBy(xpath="//span[@class='sort-name-text' and contains(text(),\"Best reviews\")]")
    WebElement BestReviewed;
    @FindBy(xpath="//i[contains(@class,'fi-close')]")
    List<WebElement> closeButtons;

    public void switchToLastWindow() {
        List<String> windowList = new java.util.ArrayList<>(driver.getWindowHandles());
        if (windowList.size() > 1) {
            driver.switchTo().window(windowList.getLast());
        }
    }
    public void SelectTopReviewed(){
        BestReviewed.click();
    }
    public void selectFirstCruiseProduct() {
        if (!cruiseProducts.isEmpty()) {
            WebElement firstCruise = cruiseProducts.getFirst();
            Waits.waitForElementToBeClickable(driver, firstCruise, 10).click();
        }
    }
    public String getGuestCapacity() {
        try {
            Waits.waitForVisibility(driver, guestCapacityElem, 10);
            String GuestCapacityText = guestCapacityElem.getText();
            String GuestCapacity = GuestCapacityText.replace("Guest capacity:", "").trim();
            return GuestCapacity.trim();
        } catch (Exception e) {
            return "N/A";
        }
    }

    public String getRenovatedYear() {
        try {
            Waits.waitForVisibility(driver, renovatedYear, 10);
            String RenovatedYearText = renovatedYear.getText();
            String RenovatedYear = RenovatedYearText.replace("Renovated:", "");
            return RenovatedYear.trim();
        } catch (Exception e) {
            return "N/A";
        }
    }
    public String getCruiseId() {
        try {
            String cruiseIdText = cruiseId.getText();
            String cruiseId = cruiseIdText.replace("Cruise ID:", "");
            return cruiseId.trim();
        } catch (Exception e) {
            System.out.println("Cruise ID not found: " + e.getMessage());
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
        Waits.waitForVisibility(driver, By.xpath("//div[contains(@class,'product-name')]"), 10);
    }
    public void waitForCruiseDetailsPage() {
        Waits.waitForVisibility(driver, By.xpath("//div[@class='ship-parames']"), 10);
    }
}
