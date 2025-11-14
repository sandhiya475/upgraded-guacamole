package mock;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class FlipKartProduct {
	
public static void main(String[] args) throws IOException {
WebDriver driver = new ChromeDriver();
driver.manage().window().maximize();
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

driver.get("https://www.flipkart.com/");
driver.findElement(By.name("q")).sendKeys("iphone",Keys.ENTER);
List<WebElement> productName = driver.findElements(By.xpath("//div[@class='KzDlHZ']"));
List<WebElement> priceName = driver.findElements(By.xpath("//div[@class='Nx9bqj _4b5DiR']"));

Workbook wb=new XSSFWorkbook();
Sheet sheet = wb.createSheet("iphone");
Row row = sheet.createRow(0);
row.createCell(0).setCellValue("productName");
row.createCell(1).setCellValue("priceName");

int i=1,j=1;
for (WebElement pro : productName) {
	System.out.println(pro.getText());
	sheet.createRow(i++).createCell(0).setCellValue(pro.getText());
	
}

for (WebElement pri : priceName) {
	System.out.println(pri.getText());
	sheet.getRow(j++).createCell(1).setCellValue(pri.getText());

}

sheet.autoSizeColumn(0);
sheet.autoSizeColumn(1);
FileOutputStream fos=new FileOutputStream("./testData1/mock.xlsx");
wb.write(fos);
wb.close();
driver.quit();

}
}