import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

public class RegisterCSVTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.edge.driver", "./msedgedriver.exe");

        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new EdgeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Step 1: Open URL
        driver.get("https://tutorialsninja.com/demo/");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @DataProvider(name = "csvRegistrationData")
    public Object[][] getCSVData() {
        return CSVUtils.getCSVData("UserDetails.csv");
    }

    @Test(dataProvider = "csvRegistrationData")
    public void testUserRegistrationFromCSV(String firstName, String lastName, String email, 
                                            String telephone, String password, String confirmPassword) {

        // Step 2: Verify application title
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, "Your Store", "Title mismatch!");

        // Step 3: Click "My Account" menu option
        WebElement myAccountMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='My Account']")));
        myAccountMenu.click();

        // Step 4: Select "Register" option
        WebElement registerOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Register']")));
        registerOption.click();

        // Step 5: Verify page heading text "Register Account"
        WebElement pageHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='content']//h1")));
        Assert.assertEquals(pageHeading.getText(), "Register Account", "Page heading mismatch!");

        // Step 6: Enter details from CSV (Appending timestamp to keep email unique across runs)
        String uniqueEmail = System.currentTimeMillis() + "_" + email.trim();

        driver.findElement(By.id("input-firstname")).sendKeys(firstName.trim());
        driver.findElement(By.id("input-lastname")).sendKeys(lastName.trim());
        driver.findElement(By.id("input-email")).sendKeys(uniqueEmail);
        driver.findElement(By.id("input-telephone")).sendKeys(telephone.trim());
        driver.findElement(By.id("input-password")).sendKeys(password.trim());
        driver.findElement(By.id("input-confirm")).sendKeys(confirmPassword.trim());

        // Step 7: Select "I have read and agree to the Privacy Policy" checkbox
        WebElement privacyCheckbox = driver.findElement(By.name("agree"));
        if (!privacyCheckbox.isSelected()) {
            privacyCheckbox.click();
        }

        // Step 8: Click "Continue" button
        driver.findElement(By.xpath("//input[@value='Continue']")).click();

        // Step 9: Verify acknowledgement message "Your Account Has Been Created!"
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='content']//h1")));
        Assert.assertEquals(successMessage.getText(), "Your Account Has Been Created!", "Account creation verification failed!");
    }
}