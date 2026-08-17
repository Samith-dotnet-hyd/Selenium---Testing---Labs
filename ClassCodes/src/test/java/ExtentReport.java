

import java.time.Duration;
 
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
 
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
 
import io.github.bonigarcia.wdm.WebDriverManager;
 
public class ExtentReport {
 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String projectpath=System.getProperty("user.dir");
		ExtentReports extent;
		ExtentSparkReporter reporter=new ExtentSparkReporter(projectpath+"\\Reports\\loginreport.html");
		reporter.config().setReportName("Test Execution Report");
		
		extent=new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Samith");
		extent.setSystemInfo("Browser", "Chrome");
		extent.setSystemInfo("Environment", "QA");
		   ExtentTest test=extent.createTest("Login Test");
		
		   WebDriverManager.chromedriver().setup();
		WebDriver driver=new ChromeDriver();
		
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(5));
		
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
		//driver.manage().window().maximize();
	//	Thread.sleep(5000);
	WebElement username=wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
	
		
		//driver.findElement(By.name("username")).sendKeys("Admin");
	username.sendKeys("Admin");
	driver.findElement(By.name("password")).sendKeys("admin123");
	driver.findElement(By.xpath("//button[@type='submit']")).click();
	test.info("Login passed");
	extent.flush();
 
	}
 
}
 