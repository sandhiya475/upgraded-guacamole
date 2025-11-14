package mock;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import com.crm.basetest.BaseClass1;
import com.crm.generic.fileutility.ExcelUtility;

public class SpotifyDataWriteToExcel extends BaseClass1 {
	public static void main(String[] args) throws Throwable {
	WebDriver driver=new ChromeDriver();
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	driver.get("https://open.spotify.com/");
	
	//SpotifyPage sp=new SpotifyPage(driver);
	driver.findElement(By.xpath("//div[@class='wuQsKLTukGW5aeEAHxiu']/descendant::div[@aria-labelledby='card-title-spotify:artist:1mYsTxnqsietFxj1OgoGbG-1 card-subtitle-spotify:artist:1mYsTxnqsietFxj1OgoGbG-1']")).click();
	List<WebElement> artist1songs = driver.findElements(By.xpath("//div[@class='hb8C1VAjyUg0VMxrwpix']"));
	System.out.println("Total songs found: " + artist1songs.size());
	
	ExcelUtility eutil=new ExcelUtility();
    int i=0;
    try {
	for (WebElement songs : artist1songs) {
    System.out.println(songs.getText());
    eutil.setDataIntoExcelSpotify("Sheet1", i+1, 0,songs.getText());
    i++;
    }
    }
    catch(Exception e) {
    	
    }
    
    List<WebElement> viewers = driver.findElements(By.xpath("//div[@class='e-91000-text encore-text-body-small htbmhRXsxePzCR3HsX0V']"));
	i=0;
    for (WebElement view : viewers) {
		System.out.println(view.getText());
	    eutil.setDataIntoExcelSpotify("Sheet1", i+1, 1,view.getText());
	    i++;
	}
    System.out.println("viewers inserted successfully");
     driver.quit();
}
}