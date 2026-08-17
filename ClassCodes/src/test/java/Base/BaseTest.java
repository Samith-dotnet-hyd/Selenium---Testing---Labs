package Base;
 
import java.time.Duration;
 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
 
import Utilities.ConfigReader;
import Utilities.DriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;
 
public class BaseTest {
	
	protected WebDriver driver;
	
	public void setup()
	{
		String browser=ConfigReader.getvalue("browser");
		if(browser.equalsIgnoreCase("chrome"))
		{
			WebDriverManager.chromedriver().setup();
			driver=new ChromeDriver();
			
		}
		
		DriverManager.setDriver(driver);
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get(ConfigReader.getvalue("url"));
	}
	
	public void teardown()
	{
		
		driver.quit();
		
		DriverManager.unload();
		
	}
}
 