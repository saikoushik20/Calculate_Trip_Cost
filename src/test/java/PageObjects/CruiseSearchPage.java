package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import Utilities.Waits;

import java.util.List;

public class CruiseSearchPage extends BasePage {
    public CruiseSearchPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id="header_action_nav_cruises")
    WebElement cruiseTab;
    @FindBy(xpath="//div[contains(text(),'Departure city')]/following-sibling::div//div[contains(@class,'cru-form-select-text')]")
    WebElement departureCityBox;
    @FindBy(xpath="//div[contains(@class,'c-dropdown') and contains(@class,'show')]")
    WebElement dropdown;
    @FindBy(xpath="//i[contains(@class,'fi-close')]")
    List<WebElement> closeButtons;
    @FindBy(xpath="//button[contains(@class,'cru-btn') and .//span[normalize-space()='Search']]")
    WebElement searchButton;

    public void goToCruiseMenu() {
        WebElement cruiseMenu = Waits.waitForElementToBeClickable(driver, cruiseTab, 15);
        assert cruiseMenu != null;
        cruiseMenu.click();
        Waits.waitForUrlContains(driver, "/cruises", 15);
    }

    public void selectDepartureCity(String city) {
        Waits.waitForVisibility(driver, departureCityBox, 15);
        Waits.waitForElementToBeClickable(driver, departureCityBox, 15).click();
        By cityOption = By.xpath("//div[contains(@class,'c-dropdown') and contains(@class,'show')]//span[normalize-space()='" + city + "']");
        WebElement cityElem = Waits.waitForElementToBeClickable(driver, cityOption, 15);
        assert cityElem != null;
        cityElem.click();
        Waits.waitForInvisibility(driver, dropdown, 15);
    }

    public void closeAllPopups() throws InterruptedException {
        for (WebElement btn : closeButtons) {
            if (btn.isDisplayed() && btn.isEnabled()) {
                btn.click();
                Thread.sleep(500);
            }
        }
    }
    public void clickSearchButton() {
        WebElement btn = Waits.waitForElementToBeClickable(driver, searchButton, 15);
        assert btn != null;
        btn.click();
    }
}
