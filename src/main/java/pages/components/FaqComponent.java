package pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class FaqComponent {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы для FAQ
    private final By faqSection = By.className("Home_FAQ__3uVm4");
    private final By questionButtons = By.cssSelector(".accordion__button");
    private final By answerPanels = By.cssSelector(".accordion__panel");

    public FaqComponent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
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
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", question);
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", question);

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
}