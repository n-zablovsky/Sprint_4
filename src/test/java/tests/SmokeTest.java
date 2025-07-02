package tests;

import org.junit.Test;
import pages.MainPage;
import utils.BaseTest;
import static org.junit.Assert.*;

public class SmokeTest extends BaseTest {
    @Test
    public void testScooterLogoRedirect() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickScooterLogo();
        assertEquals("https://qa-scooter.praktikum-services.ru/", driver.getCurrentUrl());
    }

    @Test
    public void testYandexLogoRedirect() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();

        String originalWindow = driver.getWindowHandle();
        mainPage.clickYandexLogo();

        // Ждем открытия нового окна
        Thread.sleep(2000); // Временное решение для демонстрации

        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.equals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        assertTrue("Не открылась страница Яндекса",
                driver.getCurrentUrl().startsWith("https://yandex.ru/"));
    }
}