package utilities;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class ExcelUtil {
    public static void writeHotelData(List<WebElement> hotelCards, String filePath) {
        try {
            if(hotelCards == null){
                System.out.println("Hotel cards list is null"); return;
            }else { System.out.println("Writing hotel data Cards first top3"); }
            File resourcesDir = new File("src/test/resources");
            if (!resourcesDir.exists()) { resourcesDir.mkdirs(); }
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Hotel Data");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("S.No");
            header.createCell(1).setCellValue("Hotel Name");
            header.createCell(2).setCellValue("Price per Night");
            header.createCell(3).setCellValue("Total Price");
            for (int i = 0; i < hotelCards.size() && i < 3; i++) {
                WebElement card = hotelCards.get(i);
                String title = "N/A";
                String price = "N/A";
                String totalPrice = "N/A";
                try {
                    WebElement titleElement = card.findElement(org.openqa.selenium.By.xpath(".//div[contains(@class,'hotel-title')]//a"));
                    title = titleElement.getText();
                } catch (Exception ignored) {}
                try {
                    WebElement priceElement = card.findElement(org.openqa.selenium.By.xpath(".//div[contains(@class,'price-line')]//span"));
                    price = priceElement.getText();
                } catch (Exception ignored) {}
                try {
                    List<WebElement> totalPriceElement = card.findElements(org.openqa.selenium.By.xpath(".//p[contains(text(),'Total price')]"));
                    if (!totalPriceElement.isEmpty()) {
                        String totalDetails = totalPriceElement.get(0).getText();
                        totalPrice = totalDetails.replaceAll("(?s).*?(₹\\s?[0-9,]+).*", "$1");
                    }
                } catch (Exception ignored) {}
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue(title);
                row.createCell(2).setCellValue(price);
                row.createCell(3).setCellValue(totalPrice);
                System.out.println("Row " + (i+1) + ": " + title + ", " + price + ", " + totalPrice);
            }
            FileOutputStream file = new FileOutputStream(filePath);
            workbook.write(file);
            workbook.close();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeCruiseData(String guestCapacity, String renovated, String cruiseId, String filePath) {
        try {
            File resourcesDir = new File("src/test/resources");
            if (!resourcesDir.exists()) {
                resourcesDir.mkdirs();
            }
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Cruise Data");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Guest Capacity");
            header.createCell(1).setCellValue("Renovated Year");
            header.createCell(2).setCellValue("Cruise ID");
            Row row = sheet.createRow(1);
            row.createCell(0).setCellValue(guestCapacity);
            row.createCell(1).setCellValue(renovated);
            row.createCell(2).setCellValue(cruiseId);
            FileOutputStream file = new FileOutputStream(filePath);
            workbook.write(file);
            workbook.close();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void writeHotelNames(List<List<String>> hotelNamesLists, String filePath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Hotel Names");
            Row headerRow = sheet.createRow(0);
            String[] headers = {"After Searched", "After Pool Filter", "After Top Reviewed"};
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            int maxRows = hotelNamesLists.stream().mapToInt(List::size).max().orElse(0);
            for (int rowIdx = 0; rowIdx < maxRows; rowIdx++) {
                Row row = sheet.createRow(rowIdx + 1); // +1 to skip header
                for (int colIdx = 0; colIdx < hotelNamesLists.size(); colIdx++) {
                    List<String> names = hotelNamesLists.get(colIdx);
                    if (rowIdx < names.size()) {
                        row.createCell(colIdx).setCellValue(names.get(rowIdx));
                    }
                }
            }
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
