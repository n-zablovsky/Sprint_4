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
    private final By successModal = By.className("Order_ModalHeader");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public WebElement findElementByPlaceholder(String placeholderContent) {
        String fullContent = String.format("//input[@placeholder='* %s']", placeholderContent);
        return driver.findElement(By.xpath(fullContent));
    }

    public void fillCustomerNameField(String name) {
        WebElement nameField = findElementByPlaceholder("Имя");
        nameField.sendKeys(name);
    }

    public void fillCustomerLastNameField(String lastName) {
        WebElement lastNameField = findElementByPlaceholder("Фамилия");
        lastNameField.sendKeys(lastName);
    }

    public void fillCustomerAddressField(String address) {
        WebElement addressField = findElementByPlaceholder("Адрес: куда привезти заказ");
        addressField.sendKeys(address);
    }

    public void fillCustomerMetroField(String metro) {
        WebElement metroField = findElementByPlaceholder("Станция метро");
        metroField.sendKeys(metro);
        WebElement suggestion = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[@class='select-search__row']")
        ));
        suggestion.click();
    }

    public void fillCustomerPhoneField(String phone) {
        WebElement phoneField = findElementByPlaceholder("Телефон: на него позвонит курьер");
        phoneField.sendKeys(phone);
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
        WebElement dateField = findElementByPlaceholder("Когда привезти самокат");
        dateField.sendKeys(date);
        dateField.sendKeys(Keys.ESCAPE);
    }

    public void fillRentRangeField(String rentPeriod) {
        WebElement rentRangeBlock = driver.findElement(
                By.xpath("//div[@class='Dropdown-placeholder' and text()='* Срок аренды']")
        );
        rentRangeBlock.click();
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
        WebElement fullContent = driver.findElement(By.xpath("//input[@placeholder='Комментарий для курьера']"));
        fullContent.sendKeys(comment);
    }

    public void fillRentInfo(String date, String rentPeriod, String colorId, String comment) {
        fillDateField(date);
        fillRentRangeField(rentPeriod);
        fillColorField(colorId);
        fillCommentField(comment);

        WebElement orderButtonElement = driver.findElement(orderButton);
        orderButtonElement.click();
    }

    public boolean isOrderConfirmed() {
        By yesButton = By.xpath("//button[text()='Да']");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(yesButton)).isDisplayed();
    }
}