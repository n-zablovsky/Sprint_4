package tests;

import org.junit.Test;
import pages.MainPage;

import utils.BaseTest;
import static org.junit.Assert.*;

public class FaqTest extends BaseTest {
    private static final String[] EXPECTED_ANSWERS = {
            "Сутки — 400 рублей. Оплата курьеру — наличными или картой.",
            "Пока что у нас так: один заказ — один самокат.",
            "Допустим, вы оформляете заказ на 8 мая.",
            "Только начиная с завтрашнего дня.",
            "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.",
            "Самокат приезжает к вам с полной зарядкой.",
            "Да, пока самокат не привезли.",
            "Да, обязательно. Всем самокатов!"
    };

    @Test
    public void testFaqSection() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();


        int questionCount = mainPage.getQuestionsCount();

        // Проверяем количество вопросов
        assertEquals("Количество вопросов в FAQ не соответствует ожидаемому",
                EXPECTED_ANSWERS.length, questionCount);

        // Проверяем каждый вопрос
        for (int i = 0; i < questionCount; i++) {
            try {
                System.out.println("Проверка вопроса #" + (i+1));

                // Кликаем на вопрос
                mainPage.clickQuestion(i);

                // Получаем текст ответа
                String actualAnswer = mainPage.getAnswer(i);
                System.out.println("Ответ: " + actualAnswer);

                // Проверяем соответствие ожидаемому ответу
                assertTrue("Ответ на вопрос #" + (i+1) + " не соответствует ожидаемому.\n" +
                                "Ожидалось: " + EXPECTED_ANSWERS[i] + "\n" +
                                "Получено: " + actualAnswer,
                        actualAnswer.contains(EXPECTED_ANSWERS[i]));

            } catch (Exception e) {
                System.err.println("Ошибка при проверке вопроса #" + (i+1) + ": " + e.getMessage());
                throw e;
            }
        }
    }
}