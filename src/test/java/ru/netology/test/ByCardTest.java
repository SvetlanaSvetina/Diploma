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

    // ТЕСТ НЕ ПРОЙДЕН ( НЕГАТИВНЫЕ СЦЕНАРИИ. 1.1. Поле ввода "Владелец" заполнено символами на кирилице при покупке картой со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenOwnerFilledCyrillicByCard() {
        val cadrData = getRussianHolder();
        buyPage.checkBuyForm(cadrData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ НЕ ПРОЙДЕН (1.2. Поле ввода "Владелец" заполнено спец.символами при покупке картой со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenOwnerFilledWitnSymbolsByCard() {
        val cardData = getSpecialCharactersInHolder();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ НЕ ПРОЙДЕН (1.3. Поле ввода "Владелец" заполнено цифрами при покупке картой со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenOwnerFilledWitnNumbersByCard() {
        val cardData = getNumbersInHolder();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ НЕ ПРОЙДЕН (1.4. Поле ввода "Владелец" заполнено только фамилией на латинице при покупке картой со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenOwnerOnlyLastNameByCard() {
        val cardData = getOnlyLastName();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ НЕ ПРОЙДЕН  (1.5. Поле ввода "Владелец" заполнено большим количеством символов на латинице при покупке картой со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenManyLettersByCard() {
        val cardData = getWithManyLetters();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ ПРОЙДЕН (2.1. Поле ввода "CVC/CVV" заполнено одной цифрой при покупке картой со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWithCvcOneNumberByCard() {
        val cardData = getCvcWithOneNumber();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    //  ТЕСТ ПРОЙДЕН (3.1. Поле ввода "Номер карты" заполненно недостаточным количеством цифр при покупке картой со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWithNotEnoughNumberByCard() {
        val cardData = getNotEnoughNumbers();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ ПРОЙДЕН (3.2. Поле ввода "Номер карты" заполнено невалидным значением при покупке картой со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWithNumberNotExistByCard() {
        val cardData = getNumberNotExist();
        buyPage.checkBuyForm(cardData);
        buyPage.failureResultNotification();
    }

    // ТЕСТ ПРОЙДЕН (4.1. Поле ввода "Месяц" заполнено одной цифрой при покупке картой со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenMonthOneNumberByCard() {
        val cardData = getMonthOneNumber();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ НЕ ПРОЙДЕН (4.2. Поле ввода "Месяц" заполнено нулевыми значениями при покупке картой  со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenMonthZeroByCard() {
        val cardData = getMonthWithZero();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ ПРОЙДЕН  (4.3. Поле ввода "Месяц" заполнено значением больше 12 при покупке картой со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenMonthMore12ByCard() {
        val cardData = getMonthMore12();
        buyPage.checkBuyForm(cardData);
        buyPage.invalidCardExpirationDate();
    }

    // ТЕСТ ПРОЙДЕН (5.1. Поле ввода "Год" заполнено значением из 1 цифры при покупке картой со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenYearOneNumberByCard() {
        val cardData = getYearWithOneNumber();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ ПРОЙДЕН (5.2. Поле ввода "Год" заполнено истекшим сроком годности карты при покупке картой со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenYearOfCardExpired() {
        val cardData = getExpiredCard();
        buyPage.checkBuyForm(cardData);
        buyPage.invalidCardExpirationDate();
    }

    // ТЕСТ ПРОЙДЕН (5.3. Поле ввода "Год" заполнено завышенным сроком годности карты при покупке картой со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenInvalidYearByCard() {
        val cardData = getInvalidYear();
        buyPage.checkBuyForm(cardData);
        buyPage.invalidCardExpirationDate();
    }

    // ТЕСТ ПРОЙДЕН (5.4. Поле ввода "Год" заполнено нулевым значением при покупке картой  со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenYearZeroByCard() {
        val cardData = getYearZero();
        buyPage.checkBuyForm(cardData);
        buyPage.expiredDate();
    }

    // ТЕСТ ПРОЙДЕН (6.1. Поля "Номер карты", "Месяц", "Год", "Владелец", "CVC/CVV" не заполнены при покупке картой  со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenAllEmptyFieldsByCard() {
        val cardData = getCardDataIfEmptyAllFields();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }
}



