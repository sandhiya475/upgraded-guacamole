package mock;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SpotifyWithDataProvider {
WebDriver driver;
Workbook wb;
@BeforeClass
public void loginToApp() throws InterruptedException {
   driver=new ChromeDriver();
   driver.manage().window().maximize();
   driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
   driver.get("https://open.spotify.com/");
   
   /*driver.findElement(By.xpath("//span[text()='Log in']")).click();
   driver.findElement(By.xpath("//a[text()='Continue with phone number']")).click();
   WebElement phnoTF = driver.findElement(By.id("phonelogin-phonenumber"));
   phnoTF.sendKeys("8073821956");
   driver.findElement(By.xpath("//span[text()='Next']")).click();
   Thread.sleep(20000);
   driver.findElement(By.xpath("//span[text()='Next']")).click();*/
}
@BeforeTest
public void songsStoreInExcel() {
	wb=new XSSFWorkbook();
}
   @DataProvider 
   public Object[] getData() {
	  return new Object[] {"A.R. Rahman","Shreya Ghoshal","Pritam","Anirudh Ravichander","Udit Narayan"};
   }
  
   @Test(dataProvider = "getData")
   public void toCaptureSongsAndViewers(String artName) {
	   driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	   try {
	   WebElement searchTF = driver.findElement(By.xpath("//input[@type='search']"));
	   WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(30));
	   wait.until(ExpectedConditions.elementToBeClickable(searchTF));
	   searchTF.sendKeys(Keys.CONTROL+"a",Keys.BACK_SPACE);
	   searchTF.sendKeys(artName);
	   searchTF.sendKeys(Keys.ENTER);
	   
	   JavascriptExecutor js=(JavascriptExecutor)driver;
	   js.executeScript("window.scrollBy(0,600)");
	   WebElement artNam = driver.findElement(By.xpath("//div[@data-encore-id='card']/descendant::span[text()='"+artName+"']"));
	   wait.until(ExpectedConditions.elementToBeClickable(artNam));
	   artNam.click();
	   Sheet sheet = wb.createSheet(artName);
	   Row row = sheet.createRow(0);
	   row.createCell(0).setCellValue("SongName");
	   row.createCell(1).setCellValue("Views");
	   
	   for(int i=1;i<=5;i++) {
		   String songName = driver.findElement(By.xpath("(//div[@aria-label='popular tracks']/descendant::div[@aria-rowindex='"+i+"']//div[@data-encore-id='text'])[1]")).getText();
		   String viewers = driver.findElement(By.xpath("(//div[@aria-label='popular tracks']/descendant::div[@aria-rowindex='"+i+"']//div[@data-encore-id='text'])[2]")).getText();
		   System.out.println(songName+" "+viewers);
		   
		   Row rows = sheet.createRow(i);
		   rows.createCell(0).setCellValue(songName);
		   rows.createCell(1).setCellValue(viewers);
	   }
	   sheet.autoSizeColumn(0);
	   sheet.autoSizeColumn(1);
	   }
	   catch(Exception e) {
		   
	   }
	   
   }
   @AfterClass
   public void close() throws IOException {
	   FileOutputStream fos=new FileOutputStream("./testData1/Book1.xlsx");
	   wb.write(fos);
	   wb.close();
	   driver.quit();
   }
   
	}


