package tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pages.MainPage;
import pages.OrderPage;
import utils.BaseTest;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderTest extends BaseTest {
    private final String name;
    private final String lastName;
    private final String address;
    private final String metro;
    private final String phone;
    private final boolean useTopButton;
    private final String date;
    private final String rentPeriod;
    private final String colorId;
    private final String comment;

    public OrderTest(String name, String lastName, String address,
                     String metro, String phone, boolean useTopButton, String date, String rentPeriod, String colorId, String comment) {
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.useTopButton = useTopButton;
        this.date = date;
        this.rentPeriod = rentPeriod;
        this.colorId = colorId;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Иван", "Иванов", "ул. Ленина, 1", "Черкизовская", "+79998887766", true, "20.06.2025", "сутки", "black", "123Abcd*&,"},
                {"Петр", "Петров", "ул. Пушкина, 10", "Сокольники", "+79997776655", false, "20.12.2025", "пятеро суток", "grey", ""}
        });
    }

    @Test
    public void testOrderCreation() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();

        if (useTopButton) {
            mainPage.clickOrderButtonTop();
        } else {
            mainPage.clickOrderButtonBottom();
        }

        OrderPage orderPage = new OrderPage(driver);
        orderPage.fillCustomerInfo(name, lastName, address, metro, phone);
        orderPage.fillRentInfo(date, rentPeriod, colorId, comment);

        assertTrue ( "Заказ не подтвержден", orderPage.getOrderStatus().contains("Заказ оформлен"));
    }
}