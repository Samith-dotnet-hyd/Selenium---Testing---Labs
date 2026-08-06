package com.samith.Lab6;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class App {

    public static void main(String[] args) {
        WebDriver driver = new EdgeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.manage().window().maximize();
            driver.get("https://tutorialsninja.com/demo/");

            // 1. Login with Samith's Credentials from Registration
            driver.findElement(By.xpath("//span[text()='My Account']")).click();
            driver.findElement(By.linkText("Login")).click();

            driver.findElement(By.xpath("//input[@name='email']")).sendKeys("samithreddy@gmail.com");
            driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Password@123");
            driver.findElement(By.xpath("//input[@type='submit']")).click();

            // 2. Navigate to Components -> Monitors
            driver.findElement(By.xpath("//a[text()='Components']")).click();
            driver.findElement(By.xpath("//a[text()='Monitors (2)']")).click();

            // 3. Select limit 25 and click on the first product
            WebElement showDropdown = driver.findElement(By.cssSelector("select[id='input-limit']"));
            Select select = new Select(showDropdown);
            select.selectByVisibleText("25");

            List<WebElement> addToCarts = driver.findElements(By.xpath("//span[text()='Add to Cart']"));
            addToCarts.get(0).click();

            // 4. Verify product specification tab & Add to Wish List
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Specification"))).click();
            driver.findElement(By.xpath("//button[@data-original-title='Add to Wish List']")).click();

            // 5. Verify Wish List success message
            WebElement successMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert.alert-success"))
            );
            String actualMessage = successMessage.getText();
            String expectedMessage = "Success: You have added Apple Cinema 30\" to your wish list!";

            if (actualMessage.contains(expectedMessage)) {
                System.out.println("Wishlist Message Verified");
            } else {
                System.out.println("Wishlist Message Verification Failed:\n" + actualMessage);
            }

            // 6. Search for Mobile and select HTC Touch HD
            driver.findElement(By.xpath("//input[@name='search']")).sendKeys("Mobile");
            driver.findElement(By.xpath("//button[@class='btn btn-default btn-lg']")).click();
            driver.findElement(By.id("description")).click();
            driver.findElement(By.id("button-search")).click();
            driver.findElement(By.linkText("HTC Touch HD")).click();

            // 7. Update quantity to 3 and Add to Cart
            WebElement qtyField = driver.findElement(By.id("input-quantity"));
            qtyField.clear();
            qtyField.sendKeys("3");
            driver.findElement(By.id("button-cart")).click();

            // 8. Verify Cart success message
            WebElement successMessage1 = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert.alert-success"))
            );
            String actualMessage1 = successMessage1.getText();
            String expectedMessage1 = "Success: You have added HTC Touch HD to your shopping cart!";

            if (actualMessage1.contains(expectedMessage1)) {
                System.out.println("Cart Success Message Verified");
            } else {
                System.out.println("Cart Success Message Verification Failed:\n" + actualMessage1);
            }

            // 9. Open mini-cart and verify item name
            driver.findElement(By.xpath("//div[@id='cart']//button")).click();
            String mobileName = driver.findElement(By.xpath("//td[@class='text-left']//a")).getText();
            if (mobileName.equals("HTC Touch HD")) {
                System.out.println("Mobile name matched!");
            } else {
                System.out.println("Mobile name mismatched!");
            }

            // 10. Checkout and Logout
            driver.findElement(By.linkText("Checkout")).click();

            // Click My Account and wait for Logout link in dropdown
            driver.findElement(By.xpath("//a[@title='My Account']")).click();
            WebElement logoutLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Logout']"))
            );
            logoutLink.click();

            String logoutMessage = driver.findElement(By.xpath("//div[@id='content']//h1")).getText();
            if (logoutMessage.equals("Account Logout")) {
                System.out.println("Account Logout Verified!");
            } else {
                System.out.println("Account Logout Verification Failed!");
            }

            driver.findElement(By.linkText("Continue")).click();

        } catch (Exception e) {
            System.err.println("Test Execution failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}