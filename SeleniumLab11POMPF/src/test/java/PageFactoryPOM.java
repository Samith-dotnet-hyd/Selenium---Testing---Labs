import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class PageFactoryPOM {

    private WebDriver driver;

    // --- Lab 3 Web Elements ---
    @FindBy(xpath = "//a[text()='Desktops']")
    private WebElement desktopsTab;

    @FindBy(xpath = "//a[text()='Mac (1)']")
    private WebElement macOption;

    @FindBy(css = "h2")
    private WebElement macHeading;

    @FindBy(xpath = "//select[@id='input-sort']")
    private WebElement sortDropdown;

    @FindBy(xpath = "//span[text()='Add to Cart']")
    private WebElement addToCartBtn;

    @FindBy(css = ".alert.alert-success")
    private WebElement successAlert;

    // --- Lab 4 Web Elements ---
    @FindBy(xpath = "//input[@name='search']")
    private WebElement searchInput;

    @FindBy(xpath = "//button[@class='btn btn-default btn-lg']")
    private WebElement searchButton;

    @FindBy(xpath = "//div[@id='content']//h1")
    private WebElement searchHeading;

    @FindBy(id = "input-search")
    private WebElement searchCriteriaBox;

    @FindBy(id = "description")
    private WebElement descriptionCheckbox;

    @FindBy(id = "button-search")
    private WebElement searchPageButton;

    // Constructor initializing PageFactory
    public PageFactoryPOM(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // --- Lab 3 Actions ---
    public void clickDesktopsAndMac() {
        desktopsTab.click();
        macOption.click();
    }

    public String getMacHeadingText() {
        return macHeading.getText();
    }

    public void sortByNameAZ() {
        Select select = new Select(sortDropdown);
        select.selectByVisibleText("Name (A - Z)");
    }

    public void clickAddToCart() {
        addToCartBtn.click();
    }

    public String getSuccessAlertText() {
        return successAlert.getText();
    }

    // --- Lab 4 Actions ---
    public void searchProduct(String keyword) {
        searchInput.clear();
        searchInput.sendKeys(keyword);
        searchButton.click();
    }

    public String getSearchHeadingText() {
        return searchHeading.getText();
    }

    public void clearSearchCriteria() {
        searchCriteriaBox.clear();
    }

    public void checkDescriptionAndSearch() {
        if (!descriptionCheckbox.isSelected()) {
            descriptionCheckbox.click();
        }
        searchPageButton.click();
    }

    public boolean isDescriptionSelected() {
        return descriptionCheckbox.isSelected();
    }
}