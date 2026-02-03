package TestCases;

import org.testng.annotations.Test;
import PageObjects.CruiseResultsPage;
import PageObjects.CruiseSearchPage;
import BaseTest.BaseTestClass;
import utilities.ExcelUtil;

public class CruiseDetailsTest extends BaseTestClass {
    @Test(priority = 1)
    public void cruiseDetailsPage() throws InterruptedException {
        logger.info("***** Starting Cruise Details Test ****");
        CruiseSearchPage cruisePage = new CruiseSearchPage(driver);
        cruisePage.goToCruiseMenu();
        cruisePage.closeAllPopups();
        cruisePage.selectDepartureCity("Abu Dhabi");
        cruisePage.closeAllPopups();
        cruisePage.clickSearchButton();
        logger.info("***** Cruise Details Test Completed ****");
    }
    @Test(priority = 2)
    public void cruiseResultsPage() {
        logger.info("***** Starting Cruise Results Test ****");
        CruiseResultsPage cruiseResultsPage = new CruiseResultsPage(driver);
        cruiseResultsPage.switchToLastWindow();
        cruiseResultsPage.waitForCruiseProductList();
        cruiseResultsPage.closeAllPopups();
        cruiseResultsPage.selectFirstCruiseProduct();
        cruiseResultsPage.switchToLastWindow();
        cruiseResultsPage.waitForCruiseDetailsPage();
        String guestCapacity = cruiseResultsPage.getGuestCapacity();
        String renovated = cruiseResultsPage.getRenovatedYear();
        String cruiseId = cruiseResultsPage.getCruiseId();
        logger.info("Guest capacity: {}", guestCapacity);
        logger.info("Renovated: {}", renovated);
        logger.info("Cruise ID: {}", cruiseId);
        System.out.println("Cruise ID: " + cruiseId);
        System.out.println("Guest Capacity: " + guestCapacity + " members");
        System.out.println("Renovated Year: " + renovated);
        ExcelUtil.writeCruiseData(guestCapacity, renovated, cruiseId, "./Excel Output/CruiseDetails.xlsx");
        logger.info("***** Cruise Results Test Completed ****");
    }

}




