package tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pages.MainPage;

import utils.BaseTest;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;


@RunWith(Parameterized.class)
public class FaqTest extends BaseTest {
    private final int index;
    private final String expectedText;

    public FaqTest(int index, String expectedText) {
        this.index = index;
        this.expectedText = expectedText;
    }


    @Parameterized.Parameters(name = "FAQ question #{0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {0, "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {1, "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {2, "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {3, "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {4, "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {5, "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {6, "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {7, "Да, обязательно. Всем самокатов! И Москве, и Московской области."}

        });
    }

    @Test
    public void testFaqAnswers() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickQuestion(index);
        String actualText = mainPage.getAnswer(index);
        assertFalse("Ответ не должен быть пустым", actualText.isEmpty());
        assertTrue("Ответ на вопрос #" + (index + 1) + " не соответствует ожидаемому.\n" +
                        "Ожидалось: " + expectedText + "\n" +
                        "Получено: " + actualText,
                actualText.equals(expectedText));
    }

}