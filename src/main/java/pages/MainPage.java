package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

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
    private final By faqSection = By.className("Home_FAQ__3uVm4");
    private final By questionButtons = By.cssSelector(".accordion__button");
    private final By answerPanels = By.cssSelector(".accordion__panel");

    public static final String URL = "https://qa-scooter.praktikum-services.ru/";

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Основные методы
    public void open() {
        driver.get(URL);
        acceptCookies();
    }

    public void clickQuestion(int index) {
        // Ждем загрузки всего раздела FAQ
        wait.until(ExpectedConditions.visibilityOfElementLocated(faqSection));

        List<WebElement> questions = driver.findElements(questionButtons);

        if (index < 0 || index >= questions.size()) {
            throw new IndexOutOfBoundsException("Question index " + index + " out of bounds");
        }

        WebElement question = questions.get(index);
        // Скролл и клик через JavaScript для надежности
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", question);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", question);

        // Ждем раскрытия ответа
        wait.until(d -> {
            WebElement answer = driver.findElements(answerPanels).get(index);
            return answer.isDisplayed();
        });
    }

    public String getAnswer(int index) {
        List<WebElement> answers = driver.findElements(answerPanels);

        if (index < 0 || index >= answers.size()) {
            throw new IndexOutOfBoundsException("Answer index " + index + " out of bounds");
        }

        return answers.get(index).getText();
    }

    public int getQuestionsCount() {
        return driver.findElements(questionButtons).size();
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
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
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