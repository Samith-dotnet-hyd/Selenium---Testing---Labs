import org.dom4j.Document;
import org.dom4j.io.SAXReader;
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
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;

public class SearchTestXML {

    private WebDriver driver;
    private WebDriverWait wait;
    private Document xmlDoc;

    @BeforeMethod
    public void setUp() throws Exception {
        // Load XML Object Repository using dom4j
        File xmlFile = new File("ObjectRepository.xml");
        SAXReader reader = new SAXReader();
        xmlDoc = reader.read(xmlFile);
        System.setProperty("webdriver.edge.driver", "./msedgedriver.exe");
        // Setup WebDriver
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");
//        io.github.bonigarcia.wdm.WebDriverManager.edgedriver().setup();
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
    public void testLab4_SearchFlowUsingXMLRepository() {
        // Fetch XPath & ID values from XML file
        String searchBoxXpath = xmlDoc.getRootElement().elementText("search_box");
        String searchBtnXpath = xmlDoc.getRootElement().elementText("search_button");
        String searchHeadingXpath = xmlDoc.getRootElement().elementText("search_heading");
        String criteriaBoxId = xmlDoc.getRootElement().elementText("search_criteria_box");
        String descCheckboxId = xmlDoc.getRootElement().elementText("description_checkbox");
        String pageBtnId = xmlDoc.getRootElement().elementText("search_page_button");

        // Step 1: Type 'Mobile', clear, type 'Monitors'
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchBoxXpath)));
        searchInput.sendKeys("Mobile");
        searchInput.clear();
        searchInput.sendKeys("Monitors");

        // Step 2: Click Search Button
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(searchBtnXpath))).click();

        // Step 3: Verify Search Heading
        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchHeadingXpath)));
        Assert.assertEquals(heading.getText(), "Search - Monitors");

        // Step 4: Clear Search Criteria input box on search page
        WebElement criteriaBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(criteriaBoxId)));
        criteriaBox.clear();

        // Step 5: Select 'Search in product descriptions' Checkbox
        WebElement descCheckbox = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(descCheckboxId)));
        if (!descCheckbox.isSelected()) {
            descCheckbox.click();
        }

        // Step 6: Verify Checkbox Selection State & click re-search
        Assert.assertTrue(descCheckbox.isSelected());
        wait.until(ExpectedConditions.elementToBeClickable(By.id(pageBtnId))).click();
    }
}