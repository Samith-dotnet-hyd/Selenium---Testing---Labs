import org.openqa.selenium.By;

public class Locators {

    // Lab 3 Locators
    public static final By DESKTOPS_TAB = By.xpath("//a[text()='Desktops']");
    public static final By MAC_OPTION = By.xpath("//a[text()='Mac (1)']");
    public static final By MAC_HEADING = By.cssSelector("h2");
    public static final By SORT_DROPDOWN = By.xpath("//select[@id='input-sort']");
    public static final By ADD_TO_CART_BTN = By.xpath("//span[text()='Add to Cart']");
    public static final By SUCCESS_ALERT = By.cssSelector(".alert.alert-success");

    // Lab 4 Locators
    public static final By SEARCH_BOX = By.xpath("//input[@name='search']");
    public static final By SEARCH_BUTTON = By.xpath("//button[@class='btn btn-default btn-lg']");
    public static final By SEARCH_CRITERIA_BOX = By.id("input-search");
    public static final By SEARCH_PAGE_BUTTON = By.id("button-search");
    public static final By SEARCH_HEADING = By.xpath("//div[@id='content']//h1");
    public static final By DESCRIPTION_CHECKBOX = By.id("description");
}