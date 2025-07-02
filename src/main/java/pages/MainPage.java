package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы элементов
    private final By cookieBanner = By.className("App_CookieConsent__1yUIN");
    private final By cookieAcceptButton = By.xpath("//button[text()='да все привыкли']");
    private final By topOrderButton = By.xpath("//button[text()='Заказать' and contains(@class, 'Button_Button__ra12g')]");
    private final By bottomOrderButton = By.xpath("//div[contains(@class, 'Home_FinishButton')]/button[text()='Заказать']");
    private final By scooterLogo = By.xpath("//a[@href='/']/img[@alt='Scooter']");
    private final By yandexLogo = By.xpath("//a[@href='//yandex.ru']/img[@alt='Yandex']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Основные методы
    public void open() {
        driver.get("https://qa-scooter.praktikum-services.ru/");
        acceptCookies();
    }

    public void acceptCookies() {
        try {
            WebElement cookieBannerElement = wait.until(ExpectedConditions.visibilityOfElementLocated(cookieBanner));
            cookieBannerElement.findElement(cookieAcceptButton).click();
            wait.until(ExpectedConditions.invisibilityOf(cookieBannerElement));
        } catch (Exception e) {
            System.out.println("Cookie banner not found or already accepted");
        }
    }

    // Методы для кнопок заказа
    public void clickOrderButtonTop() {
        driver.findElement(topOrderButton).click();
    }

    public void clickOrderButtonBottom() {
        WebElement element = driver.findElement(bottomOrderButton);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
        element.click();
    }

    // Методы для логотипов
    public void clickScooterLogo() {
        driver.findElement(scooterLogo).click();
    }

    public void clickYandexLogo() {
        driver.findElement(yandexLogo).click();
    }
}