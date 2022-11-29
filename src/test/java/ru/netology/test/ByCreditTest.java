package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.pages.BuyPage;
import ru.netology.pages.MainPage;

import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.netology.data.DataHelper.*;
import static ru.netology.data.SQLHelper.*;

// extends — используем для наследования свойств класса
// ByCardTest наследник UITest

public class ByCreditTest extends UITest {

    MainPage mainPage = new MainPage();
    BuyPage buyPage = new BuyPage();

    @BeforeEach
    void setUpForPayWithCard() {
        mainPage.buyByCredit();
    }

    // ТЕСТ ПРОЙДЕН. (2.1. Успешная покупка тура картой со статусом APPROVED используя кнопку "Купить в кредит")
    @Test
    public void shouldSuccessPayApprovedCredit() {
        // val - переменная только для чтения (не изменяемая)

        val cardData = getApprovedNumber();
        buyPage.checkBuyForm(cardData);
        buyPage.successResultNotification();

        val expectedStatus = "APPROVED";
        val actualStatus = getCardStatusForPayWithCredit();
        assertEquals(expectedStatus, actualStatus);


        val bankIdExpected = getBankId();
        val paymentIdActual = getPaymentIdForPayWithCredit();
        assertNotNull(bankIdExpected);
        assertNotNull(paymentIdActual);
        assertEquals(bankIdExpected, paymentIdActual);

    }

    // ТЕСТ НЕ ПРОЙДЕН. (2.2. Отклонение операции в покупке тура при оплате картой со статусом DECLINED используя кнопку "Купить в кредит")
    @Test
    public void shouldPaymentRejectionDeclinedCredit() {
        val cardData = getDeclinedNumber();
        buyPage.checkBuyForm(cardData);
        buyPage.failureResultNotification();

        val expectedStatus = "Declined";
        val actualStatus = getCardStatusForPayWithCredit();
        assertEquals(expectedStatus, actualStatus);

        val bankIdExpected = getBankId();
        val paymentIdActual = getPaymentIdForPayWithCredit();
        assertNotNull(bankIdExpected);
        assertNotNull(paymentIdActual);
        assertEquals(bankIdExpected, paymentIdActual);

    }
}
