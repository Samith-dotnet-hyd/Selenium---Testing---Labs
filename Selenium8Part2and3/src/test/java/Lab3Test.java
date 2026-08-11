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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Lab3Test {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new EdgeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Reporter.log("Lab3: Edge browser started and maximized.", true);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            Reporter.log("Lab3: Edge browser closed.", true);
        }
    }

    @Test
    public void desktopMacFlowTest() {
        Reporter.log("Step 1: Navigating to OpenCart site.", true);
        driver.get("https://tutorialsninja.com/demo/");

        Reporter.log("Step 2: Clicking Desktops -> Mac (1).", true);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Desktops']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Mac (1)']"))).click();

        Reporter.log("Step 3: Verifying 'Mac' heading.", true);
        WebElement macHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2")));
        Assert.assertEquals(macHeading.getText(), "Mac", "Page heading mismatch!");

        Reporter.log("Step 4: Sorting products by Name (A - Z).", true);
        WebElement sortDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@id='input-sort']")));
        Select select = new Select(sortDropdown);
        select.selectByVisibleText("Name (A - Z)");

        Reporter.log("Step 5: Clicking Add to Cart.", true);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Add to Cart']"))).click();

        Reporter.log("Step 6: Verifying success message.", true);
        WebElement successAlert = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert.alert-success"))
        );
        Assert.assertTrue(successAlert.getText().contains("Success: You have added iMac to your shopping cart!"),
                "Success banner mismatch!");
    }
}