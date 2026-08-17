

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
public class ExtentManager {
	
public static ExtentReports extent;
static String projectpath=System.getProperty("user.dir");
 
public static ExtentReports getinstance()
{
	if(extent==null)
	{
		ExtentSparkReporter reporter=new ExtentSparkReporter(projectpath+"Reports\\loginreport.html");
		reporter.config().setReportName("Test Execution Report");
		
		extent=new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Pooja");
		extent.setSystemInfo("Browser", "Chrome");
		extent.setSystemInfo("Environment", "QA");
	}
	
	return extent;
}
 
 
	
}
