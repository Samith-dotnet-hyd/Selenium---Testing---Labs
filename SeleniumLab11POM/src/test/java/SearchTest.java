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
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchTest {

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
    public void testLab4_SearchAndFilterFlow() {
        // Step 1: Search 'Mobile', clear, search 'Monitors'
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.SEARCH_BOX));
        searchInput.sendKeys("Mobile");
        searchInput.clear();
        searchInput.sendKeys("Monitors");

        // Step 2: Click Search Button
        wait.until(ExpectedConditions.elementToBeClickable(Locators.SEARCH_BUTTON)).click();

        // Step 3: Verify Search Heading
        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.SEARCH_HEADING));
        Assert.assertEquals(heading.getText(), "Search - Monitors");

        // Step 4: Clear Search Criteria input box on search page
        WebElement criteriaBox = wait.until(ExpectedConditions.presenceOfElementLocated(Locators.SEARCH_CRITERIA_BOX));
        criteriaBox.clear();

        // Step 5: Select 'Search in product descriptions' Checkbox
        WebElement descCheckbox = wait.until(ExpectedConditions.presenceOfElementLocated(Locators.DESCRIPTION_CHECKBOX));
        if (!descCheckbox.isSelected()) {
            descCheckbox.click();
        }

        // Step 6: Verify Checkbox Selection State & re-search
        Assert.assertTrue(descCheckbox.isSelected());
        wait.until(ExpectedConditions.elementToBeClickable(Locators.SEARCH_PAGE_BUTTON)).click();
    }
}