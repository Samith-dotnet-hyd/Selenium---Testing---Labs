
	import java.io.File;
	import java.io.IOException;
	import java.time.Duration;

	import org.apache.commons.io.FileUtils;
	import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
	import org.apache.poi.xssf.usermodel.XSSFSheet;
	import org.apache.poi.xssf.usermodel.XSSFWorkbook;
	import org.openqa.selenium.By;
	import org.openqa.selenium.OutputType;
	import org.openqa.selenium.TakesScreenshot;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.edge.EdgeDriver;
	import org.openqa.selenium.edge.EdgeOptions;
	import org.openqa.selenium.support.ui.ExpectedConditions;
	import org.openqa.selenium.support.ui.WebDriverWait;
	import org.testng.Assert;
	import org.testng.annotations.AfterClass;
	import org.testng.annotations.AfterMethod;
	import org.testng.annotations.BeforeClass;
	import org.testng.annotations.BeforeMethod;
	import org.testng.annotations.DataProvider;
	import org.testng.annotations.Test;
	import com.aventstack.extentreports.ExtentReports;
	import com.aventstack.extentreports.ExtentTest;

	public class extentManagerwithScreenshot {

	    ExtentReports extent;
	    WebDriver driver;
	    WebDriverWait wait;
	    String projectpath = System.getProperty("user.dir");

	    @BeforeClass
	    public void setupReport() {
	        // Initialize extent report instance before running tests
	        extent = ExtentManager.getinstance();
	    }

	    @BeforeMethod
	    public void beforeMethod() {
	        System.setProperty("webdriver.edge.driver", "./msedgedriver.exe");
	        EdgeOptions options = new EdgeOptions();
	        options.addArguments("--remote-allow-origins=*");

	        driver = new EdgeDriver(options);
	        driver.manage().window().maximize();
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	        driver.get("https://opensource-demo.orangehrmlive.com/");
	    }

	    @Test(dataProvider = "dp")
	    public void testLogin(String uname, String pword) throws IOException {
	        ExtentTest test = extent.createTest("OrangeHRM Login Test - " + uname);

	        // Direct Selenium Locators (No POM class required)
	        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
	        usernameField.sendKeys(uname);
	        test.info("Username entered: " + uname);

	        WebElement passwordField = driver.findElement(By.name("password"));
	        passwordField.sendKeys(pword);
	        test.info("Password entered");

	        WebElement loginBtn = driver.findElement(By.xpath("//button[@type='submit']"));
	        loginBtn.click();
	        test.info("Login button clicked");

	        try {
	            WebElement dashboardHeader = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Dashboard']"))
	            );

	            if (dashboardHeader.isDisplayed()) {
	                String screenshotPath = captureScreenshot("Login_Success_" + System.currentTimeMillis());
	                test.pass("Login Successful").addScreenCaptureFromPath(screenshotPath);
	            }
	        } catch (Exception e) {
	            String screenshotPath = captureScreenshot("Login_Failure_" + System.currentTimeMillis());
	            test.fail("Login Failed: Dashboard not displayed").addScreenCaptureFromPath(screenshotPath);
	            Assert.fail("Login failed for user: " + uname);
	        }
	    }

	    @AfterMethod
	    public void tearDown() {
	        if (driver != null) {
	            driver.quit();
	        }
	    }

	    @AfterClass
	    public void flushReport() {
	        // Flush report once all data-driven iterations complete
	        if (extent != null) {
	            extent.flush();
	        }
	    }

	    public String captureScreenshot(String fileName) throws IOException {
	        TakesScreenshot ts = (TakesScreenshot) driver;
	        File source = ts.getScreenshotAs(OutputType.FILE);

	        // Ensure Screenshots directory exists
	        File screenshotDir = new File(projectpath + "/Screenshots/");
	        if (!screenshotDir.exists()) {
	            screenshotDir.mkdir();
	        }

	        String destinationPath = projectpath + "/Screenshots/" + fileName + ".png";
	        File destination = new File(destinationPath);
	        FileUtils.copyFile(source, destination);

	        return destinationPath;
	    }

	    @DataProvider(name = "dp")
	    public Object[][] dp() throws InvalidFormatException, IOException {
	        File f1 = new File(projectpath + "\\data.xlsx");
	        
	        // Return dummy data if data.xlsx doesn't exist
	        if (!f1.exists()) {
	            return new Object[][] {
	                {"Admin", "admin123"},
	                {"InvalidUser", "wrongpass"}
	            };
	        }

	        XSSFWorkbook workbook = new XSSFWorkbook(f1);
	        XSSFSheet sheet = workbook.getSheetAt(0);
	        int rowcount = sheet.getPhysicalNumberOfRows();

	        String[][] data = new String[rowcount][2];
	        for (int i = 0; i < rowcount; i++) {
	            data[i][0] = sheet.getRow(i).getCell(0).getStringCellValue();
	            data[i][1] = sheet.getRow(i).getCell(1).getStringCellValue();
	        }
	        workbook.close();
	        return data;
	    }
	}