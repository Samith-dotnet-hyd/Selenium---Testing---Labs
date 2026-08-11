import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Lab4Test {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new EdgeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Reporter.log("Lab4: Edge browser started and maximized.", true);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            Reporter.log("Lab4: Edge browser closed.", true);
        }
    }

    @Test
    public void searchAndFilterFlowTest() {
        Reporter.log("Step 1: Navigating to OpenCart site.", true);
        driver.get("https://tutorialsninja.com/demo/");

        Reporter.log("Step 2: Entering 'Mobile', clearing, and entering 'Monitors'.", true);
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='search']")));
        searchInput.sendKeys("Mobile");
        searchInput.clear();
        searchInput.sendKeys("Monitors");

        Reporter.log("Step 3: Clicking Search button.", true);
        driver.findElement(By.xpath("//button[@class='btn btn-default btn-lg']")).click();

        Reporter.log("Step 4: Verifying 'Search - Monitors' heading.", true);
        WebElement resultPageHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='content']//h1")));
        Assert.assertEquals(resultPageHeading.getText(), "Search - Monitors", "Search heading mismatch!");

        Reporter.log("Step 5: Checking 'Search in product descriptions' checkbox.", true);
        WebElement descCheckbox = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("description")));
        if (!descCheckbox.isSelected()) {
            descCheckbox.click();
        }

        Reporter.log("Step 6: Verifying checkbox selection state.", true);
        Assert.assertTrue(descCheckbox.isSelected(), "Description checkbox is not selected!");
    }
}