package com.testing.test;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class App {

    public static void main(String[] args) {
        WebDriver driver = new EdgeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.manage().window().maximize();
            driver.get("https://tutorialsninja.com/demo/");

            // 1. Verify Page Title
            String actualTitle = driver.getTitle();
            String expectedTitle = "Your Store";
            System.out.println("The title of OpenCart page is: " + actualTitle);

            if (actualTitle.equals(expectedTitle)) {
                System.out.println("Title Verified Successfully");
            } else {
                System.out.println("Title Verification Failed");
            }

            // 2. Navigate to Registration Page
            driver.findElement(By.xpath("//span[text()='My Account']")).click();
            driver.findElement(By.linkText("Register")).click();

            WebElement headingElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
            if (headingElement.getText().equals("Register Account")) {
                System.out.println("Heading Verified");
            } else {
                System.out.println("Heading Verification Failed");
            }

            // 3. Verify First Name Validation Error (>32 characters)
            driver.findElement(By.id("input-firstname")).sendKeys("abcdefghijklmnopqrstuvwxyzabcdefg");
            driver.findElement(By.xpath("//input[@name='newsletter' and @value='1']")).click();
            driver.findElement(By.name("agree")).click();
            driver.findElement(By.cssSelector("input.btn.btn-primary")).click();

            String expectedFirstNameWarning = "First Name must be between 1 and 32 characters!";
            WebElement actualFirstNameWarningElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='" + expectedFirstNameWarning + "']"))
            );

            if (expectedFirstNameWarning.equals(actualFirstNameWarningElement.getText())) {
                System.out.println("FirstName warning verification successful!");
            } else {
                System.out.println("FirstName warning didn't match the requirement.");
            }

            // Re-locate element after DOM refresh to prevent StaleElementReferenceException
            WebElement firstNameField = driver.findElement(By.id("input-firstname"));
            firstNameField.clear();
            firstNameField.sendKeys("Samith");

            // 4. Verify Last Name Validation Error (>32 characters)
            WebElement lastNameField = driver.findElement(By.id("input-lastname"));
            lastNameField.sendKeys("abcdefghijklmnopqrstuvwxyzabcdefg");
            driver.findElement(By.cssSelector("input.btn.btn-primary")).click();

            String expectedLastNameWarning = "Last Name must be between 1 and 32 characters!";
            WebElement actualLastNameWarningElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='" + expectedLastNameWarning + "']"))
            );

            if (expectedLastNameWarning.equals(actualLastNameWarningElement.getText())) {
                System.out.println("LastName warning verification successful!");
            } else {
                System.out.println("LastName warning didn't match the requirement.");
            }

            // Re-locate element after second DOM refresh
            lastNameField = driver.findElement(By.id("input-lastname"));
            lastNameField.clear();
            lastNameField.sendKeys("User");

            // 5. Fill remaining details with Samith's credentials
            String uniqueEmail = "samith" + System.currentTimeMillis() + "@gmail.com";
            
            driver.findElement(By.id("input-email")).sendKeys(uniqueEmail);
            driver.findElement(By.id("input-telephone")).sendKeys("9876543210");
            driver.findElement(By.id("input-password")).sendKeys("Password@123");
            driver.findElement(By.id("input-confirm")).sendKeys("Password@123");

            // Re-check Privacy Policy check box if unchecked by reload
            WebElement agreeCheckBox = driver.findElement(By.name("agree"));
            if (!agreeCheckBox.isSelected()) {
                agreeCheckBox.click();
            }

            driver.findElement(By.xpath("//input[@name='newsletter' and @value='0']")).click();
            driver.findElement(By.cssSelector("input.btn.btn-primary")).click();

            // 6. Verify Account Creation Success
            WebElement successHeading = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='content']//h1"))
            );

            if (successHeading.getText().equals("Your Account Has Been Created!")) {
                System.out.println("Account created successfully for Samith!");
            } else {
                System.out.println("Account creation Failed!");
            }

            // 7. Navigate to Order History
            driver.findElement(By.cssSelector("a.btn.btn-primary")).click();
            driver.findElement(By.xpath("//a[text()='View your order history']")).click();
            System.out.println("Successfully navigated to Order History page.");

        } catch (Exception e) {
            System.err.println("Test Execution failed due to: " + e.getMessage());
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}