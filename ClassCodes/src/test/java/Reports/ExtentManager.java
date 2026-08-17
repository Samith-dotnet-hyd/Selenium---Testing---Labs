
package Reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
 
public class ExtentManager {
	static String projectpath=System.getProperty("user.dir");
	public static ExtentReports extent;
	public static ExtentReports getinstance()
	{
 
			ExtentSparkReporter reporter=new ExtentSparkReporter(projectpath+"\\Reports\\loginreport.html");
			reporter.config().setReportName("Test Execution Report");
			
			extent=new ExtentReports();
			extent.attachReporter(reporter);
			extent.setSystemInfo("Tester", "Pooja");
			extent.setSystemInfo("Browser", "Chrome");
			extent.setSystemInfo("Environment", "QA");
 
		
		return extent;
	}
 
}