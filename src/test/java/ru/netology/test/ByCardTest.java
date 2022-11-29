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
public class ByCardTest extends UITest {

    MainPage mainPage = new MainPage();
    BuyPage buyPage = new BuyPage();

    @BeforeEach
    void setUpForPayWithCard() {
        mainPage.buyByCard();
    }

    // ТЕСТ ПРОЙДЕН (1.1. Успешная покупка тура картой со статусом APPROVED используя кнопку "Купить")
    @Test
    public void shouldSuccessPayApprovedCard() {
        // val - переменная только для чтения (не изменяемая)
        val cardData = getApprovedNumber();
        buyPage.checkBuyForm(cardData);
        buyPage.successResultNotification();

        val expectedStatus = "APPROVED";
        val actualStatus = getCardStatusForPayWithCard();
        assertEquals(expectedStatus, actualStatus);

        val expectedAmount = "4500000";
        val actualAmount = getAmountPurchase();
        assertEquals(expectedAmount, actualAmount);

        val transactionIdExpected = getTransactionId();
        val paymentIdActual = getPaymentIdForPayWithCard();
        assertNotNull(transactionIdExpected);
        assertNotNull(paymentIdActual);
        assertEquals(transactionIdExpected, paymentIdActual);
    }


    // ТЕСТ НЕ ПРОЙДЕН (1.2. Отклонение операции в покупке тура при оплате картой со статусом DECLINED используя кнопку "Купить")
    @Test
    public void shouldPaymentRejectionDeclinedCard() {
        val cardData = getDeclinedNumber();
        buyPage.checkBuyForm(cardData);
        buyPage.failureResultNotification();

        val expectedStatus = "Decline";
        val actualStatus = getCardStatusForPayWithCard();
        assertEquals(expectedStatus, actualStatus);

    }

}



