package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы для формы заказа
    private final By nextButton = By.xpath("//button[text()='Далее']");
    private final By orderButton = By.xpath("(//button[text()='Заказать'])[2]");
    private final By yesButton = By.xpath ("//button[text()=\"Да\"]");
    private final By InfoModal = By.className("Order_ModalHeader__3FDaJ");

    private final By nameField = By.xpath(".//input[@placeholder='* Имя']");
    private final By lastNameField = By.xpath(".//input[@placeholder='* Фамилия']");
    private final By addressField = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metroField = By.xpath(".//input[@placeholder='* Станция метро']");
    private final By metroSuggestionField = By.xpath("//li[@class='select-search__row']");
    private final By phoneField = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By dateField = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    private final By rentRangeField = By.xpath("//div[@class='Dropdown-placeholder' and text()='* Срок аренды']");
    private final By CommentField = By.xpath("//input[@placeholder='Комментарий для курьера']");


    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void fillCustomerNameField(String name) {
        WebElement nameElement = driver.findElement(nameField);
        nameElement.sendKeys(name);
    }

    public void fillCustomerLastNameField(String lastName) {
        WebElement lastNameElement = driver.findElement(lastNameField);
        lastNameElement.sendKeys(lastName);
    }

    public void fillCustomerAddressField(String address) {
        WebElement addressElement = driver.findElement(addressField);
        addressElement.sendKeys(address);
    }

    public void fillCustomerMetroField(String metro) {
        WebElement metroElement = driver.findElement(metroField);
        metroElement.sendKeys(metro);
        WebElement suggestion = wait.until(ExpectedConditions.visibilityOfElementLocated(
                metroSuggestionField
        ));
        suggestion.click();
    }

    public void fillCustomerPhoneField(String phone) {
        WebElement phoneElement = driver.findElement(phoneField);
        phoneElement.sendKeys(phone);
    }

    public void fillCustomerInfo(String name, String lastName, String address, String metro, String phone) {
        fillCustomerNameField(name);
        fillCustomerLastNameField(lastName);
        fillCustomerAddressField(address);
        fillCustomerMetroField(metro);
        fillCustomerPhoneField(phone);

        driver.findElement(nextButton).click();
    }

    public void fillDateField(String date) {
        WebElement dateElement = driver.findElement(dateField);
        dateElement.sendKeys(date);
        dateElement.sendKeys(Keys.ESCAPE);
    }

    public void fillRentRangeField(String rentPeriod) {
        WebElement rentRangeElement = driver.findElement(rentRangeField);
        rentRangeElement.click();
        String fullContent = String.format("//div[@class='Dropdown-option' and text()='%s']", rentPeriod);
        WebElement rentRangeField = driver.findElement(
                By.xpath(fullContent)
        );
        rentRangeField.click();
    }

    public void fillColorField(String colorId) {
        WebElement checkbox = driver.findElement(By.id(colorId));
        checkbox.click();
    }

    public void fillCommentField(String comment) {
        WebElement CommentElement = driver.findElement(CommentField);
        CommentElement.sendKeys(comment);
    }

    public void fillRentInfo(String date, String rentPeriod, String colorId, String comment) {
        fillDateField(date);
        fillRentRangeField(rentPeriod);
        fillColorField(colorId);
        fillCommentField(comment);

        WebElement orderButtonElement = driver.findElement(orderButton);
        orderButtonElement.click();

        WebElement yesButtonElement = driver.findElement(yesButton);
        yesButtonElement.click();
    }

    public String getOrderStatus() {
        WebElement CommentElement = driver.findElement(InfoModal);
        return CommentElement.getText();
    }
}