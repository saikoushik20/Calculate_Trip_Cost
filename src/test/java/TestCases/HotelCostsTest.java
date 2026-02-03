package TestCases;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import PageObjects.HotelResultsPage;
import PageObjects.Homepage;
import BaseTest.BaseTestClass;
import utilities.ExcelUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class HotelCostsTest extends BaseTestClass {

    @Test (priority = 1)
    public void homepageDashboard () {
        logger.info("***** Starting HotelCostsTest  ****");
        Homepage hp = new Homepage(driver);
        hp.closePopup();
        hp.enterDestination("Nairobi");
        hp.openCheckInDate();
        LocalDate checkIn = LocalDate.now().plusDays(1);
        hp.selectDate(checkIn.getDayOfMonth());
        hp.openCheckOutDate();
        LocalDate checkOut = LocalDate.now().plusDays(6);
        hp.selectDate(checkOut.getDayOfMonth());
        hp.openGuestSelector();
        hp.addAdults(2);
        hp.confirmGuests();
        hp.clickSearch();
        driver.manage().deleteAllCookies();
        logger.info("***** Searched for required hotel details  ****");
    }
    @Test (priority = 2)
    public void hotelResultsPage() {
        logger.info("***** HotelResultsPage Started  ****");
        HotelResultsPage hr = new HotelResultsPage(driver);
        String resultsCount = hr.getResultsCount();
        System.out.println("Total hotel results: " + resultsCount);

        List<WebElement> hotelCards = hr.getHotelCards();
        List<String> afterSearch = new ArrayList<>();
        for (WebElement card : hotelCards) {
            afterSearch.add(card.getText());
        }
        hr.applyPoolFilter();
        hr.refreshPage();

        hotelCards = hr.getHotelCards();
        List<String> afterPool = new ArrayList<>();

        for (WebElement element : hotelCards) {
            afterPool.add(element.getText());
        }
        hotelCards = hr.getHotelCards();
        if (hotelCards.isEmpty()) {
            System.out.println("No hotel results found after applying Pool filter.");
        }
        String resultsCountAfterPool = hr.getResultsCount();
        System.out.println("Total hotel results after Pool filter: " + resultsCountAfterPool);
        hr.sortByTopReviewed();
        hr.refreshPage();

        hotelCards = hr.getHotelCards();
        List<String> afterTopReviewed = new ArrayList<>();
        for (WebElement element : hotelCards) {
            afterTopReviewed.add(element.getText());
        }
        hotelCards = hr.getHotelCards();
        ExcelUtil.writeHotelData(hotelCards, "./Excel Output/Hotel Details.xlsx");
        List<List<String>> allLists = List.of(afterSearch, afterPool, afterTopReviewed);
        ExcelUtil.writeHotelNames(allLists, "./Excel Output/HotelSortedName.xlsx");//add file names to properties file

        driver.navigate().back();
        logger.info("***** HotelCostsTest Completed  ****");
    }
}
