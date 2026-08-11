import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OpenCartTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");
        
        driver = new EdgeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Lab 3 Flow: Desktops -> Mac -> Sorting -> Add to Cart
     */
    @Test
    public void testLab3_DesktopMacFlow() {
        driver.get("https://tutorialsninja.com/demo/");

        // Navigate: Desktops -> Mac (1)
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Desktops']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Mac (1)']"))).click();

        // Verification 1: Page Heading is "Mac"
        WebElement macHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2")));
        assertEquals("Mac", macHeading.getText());

        // Sort by 'Name (A - Z)'
        WebElement sortDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@id='input-sort']")));
        Select select = new Select(sortDropdown);
        select.selectByVisibleText("Name (A - Z)");

        // Add to Cart
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Add to Cart']"))).click();

        // Verification 2: Check success alert text
        WebElement successAlert = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert.alert-success"))
        );
        assertTrue(successAlert.getText().contains("Success: You have added iMac to your shopping cart!"));
    }

    /**
     * Lab 4 Flow: Search -> Refine Search -> Description Filter
     */
    @Test
    public void testLab4_SearchAndFilterFlow() {
        driver.get("https://tutorialsninja.com/demo/");

        // Search Flow: Enter 'Mobile', clear, enter 'Monitors'
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='search']")));
        searchInput.sendKeys("Mobile");
        searchInput.clear();
        searchInput.sendKeys("Monitors");

        // Click Search Button
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='btn btn-default btn-lg']"))).click();

        // Verification 1: Check Heading on Search Results Page
        WebElement resultHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='content']//h1")));
        assertEquals("Search - Monitors", resultHeading.getText());

        // Select 'Search in product descriptions' Checkbox
        WebElement descCheckbox = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("description")));
        if (!descCheckbox.isSelected()) {
            descCheckbox.click();
        }

        // Verification 2: Checkbox state verification
        assertTrue("Description checkbox should be selected", descCheckbox.isSelected());
    }
}