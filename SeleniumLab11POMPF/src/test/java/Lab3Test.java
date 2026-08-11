
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class Lab3Test {

    private WebDriver driver;
    private PageFactoryPOM page;

    @BeforeMethod
    public void setUp() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new EdgeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://tutorialsninja.com/demo/");

        page = new PageFactoryPOM(driver);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testLab3_DesktopMacFlow() {
        page.clickDesktopsAndMac();
        Assert.assertEquals(page.getMacHeadingText(), "Mac");

        page.sortByNameAZ();
        page.clickAddToCart();

        Assert.assertTrue(page.getSuccessAlertText().contains("Success: You have added iMac to your shopping cart!"));
    }
}