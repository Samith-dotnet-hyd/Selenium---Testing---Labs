import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DesktopTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new EdgeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://tutorialsninja.com/demo/");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testLab3_DesktopMacFlow() {
        // Step 1: Navigate Desktops -> Mac (1)
        wait.until(ExpectedConditions.elementToBeClickable(Locators.DESKTOPS_TAB)).click();
        wait.until(ExpectedConditions.elementToBeClickable(Locators.MAC_OPTION)).click();

        // Step 2: Verify Heading
        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.MAC_HEADING));
        Assert.assertEquals(heading.getText(), "Mac");

        // Step 3: Sort by Name (A - Z)
        WebElement sortDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(Locators.SORT_DROPDOWN));
        new Select(sortDropdown).selectByVisibleText("Name (A - Z)");

        // Step 4: Add to Cart
        wait.until(ExpectedConditions.elementToBeClickable(Locators.ADD_TO_CART_BTN)).click();

        // Step 5: Verify Success Banner
        WebElement successAlert = wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.SUCCESS_ALERT));
        Assert.assertTrue(successAlert.getText().contains("Success: You have added iMac to your shopping cart!"));
    }
}