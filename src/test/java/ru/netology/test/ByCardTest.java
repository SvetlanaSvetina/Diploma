package ru.netology.test;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.pages.BuyPage;
import ru.netology.pages.MainPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.netology.data.DataHelper.*;
import static ru.netology.data.SQLHelper.*;

// extends — используем для наследования свойств класса
// ByCardTest наследник InitialTest
public class ByCardTest extends InitialTest {

    MainPage mainPage = new MainPage();
    BuyPage buyPage = new BuyPage();

    @BeforeEach
    void setUpForPayWithCard() {
        mainPage.buyByCard();
    }

    // ТЕСТ ПРОЙДЕН (1.1. Успешная покупка тура картой со статусом APPROVED используя кнопку "Купить")
    @Test
    public void
    shouldSuccessPayApprovedCard() {

        var cardData = getApprovedNumber();
        buyPage.checkBuyForm(cardData);
        buyPage.successResultNotification();

        var expectedStatus = "APPROVED";
        var actualStatus = getPaymentInfo();
        assertEquals(expectedStatus, actualStatus); // возвращаю getStatus

        var expectedAmount = "4500000";
        var actualAmount = getPaymentInfo();
        assertEquals(expectedAmount, actualAmount);

        var transactionIdExpected = getPaymentInfo();
        var paymentIdActual = getOrderInfo();
        assertNotNull(transactionIdExpected);
        assertNotNull(paymentIdActual);
        assertEquals(transactionIdExpected, paymentIdActual);
    }


    // ТЕСТ НЕ ПРОЙДЕН (1.2. Отклонение операции в покупке тура при оплате картой со статусом DECLINED используя кнопку "Купить")
    @Test
    public void shouldPaymentRejectionDeclinedCard() {
        var cardData = getDeclinedNumber();
        buyPage.checkBuyForm(cardData);
        buyPage.failureResultNotification();

        var expectedStatus = "DECLINED";
        var actualStatus = getPaymentInfo();
        assertEquals(expectedStatus, actualStatus);

    }

    // ТЕСТ НЕ ПРОЙДЕН ( НЕГАТИВНЫЕ СЦЕНАРИИ. 1.1. Поле ввода "Владелец" заполнено символами на кирилице при покупке картой со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenOwnerFilledCyrillicByCard() {
        var cadrData = getRussianHolder();
        buyPage.checkBuyForm(cadrData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ НЕ ПРОЙДЕН (1.2. Поле ввода "Владелец" заполнено спец.символами при покупке картой со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenOwnerFilledWitnSymbolsByCard() {
        var cardData = getSpecialCharactersInHolder();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ НЕ ПРОЙДЕН (1.3. Поле ввода "Владелец" заполнено цифрами при покупке картой со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenOwnerFilledWitnNumbersByCard() {
        var cardData = getNumbersInHolder();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ НЕ ПРОЙДЕН (1.4. Поле ввода "Владелец" заполнено только фамилией на латинице при покупке картой со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenOwnerOnlyLastNameByCard() {
        var cardData = getOnlyLastName();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ НЕ ПРОЙДЕН  (1.5. Поле ввода "Владелец" заполнено большим количеством символов на латинице при покупке картой со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenManyLettersByCard() {
        var cardData = getWithManyLetters();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ ПРОЙДЕН (2.1. Поле ввода "CVC/CVV" заполнено одной цифрой при покупке картой со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWithCvcOneNumberByCard() {
        var cardData = getCvcWithOneNumber();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    //  ТЕСТ ПРОЙДЕН (3.1. Поле ввода "Номер карты" заполненно недостаточным количеством цифр при покупке картой со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWithNotEnoughNumberByCard() {
        var cardData = getNotEnoughNumbers();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ ПРОЙДЕН (3.2. Поле ввода "Номер карты" заполнено невалидным значением при покупке картой со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWithNumberNotExistByCard() {
        var cardData = getNumberNotExist();
        buyPage.checkBuyForm(cardData);
        buyPage.failureResultNotification();
    }

    // ТЕСТ ПРОЙДЕН (4.1. Поле ввода "Месяц" заполнено одной цифрой при покупке картой со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenMonthOneNumberByCard() {
        var cardData = getMonthOneNumber();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ НЕ ПРОЙДЕН (4.2. Поле ввода "Месяц" заполнено нулевыми значениями при покупке картой  со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenMonthZeroByCard() {
        var cardData = getMonthWithZero();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ ПРОЙДЕН  (4.3. Поле ввода "Месяц" заполнено значением больше 12 при покупке картой со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenMonthMore12ByCard() {
        var cardData = getMonthMore12();
        buyPage.checkBuyForm(cardData);
        buyPage.invalidCardExpirationDate();
    }

    // ТЕСТ ПРОЙДЕН (5.1. Поле ввода "Год" заполнено значением из 1 цифры при покупке картой со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenYearOneNumberByCard() {
        var cardData = getYearWithOneNumber();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ ПРОЙДЕН (5.2. Поле ввода "Год" заполнено истекшим сроком годности карты при покупке картой со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenYearOfCardExpired() {
        var cardData = getExpiredCard();
        buyPage.checkBuyForm(cardData);
        buyPage.invalidCardExpirationDate();
    }

    // ТЕСТ ПРОЙДЕН (5.3. Поле ввода "Год" заполнено завышенным сроком годности карты при покупке картой со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenInvalidYearByCard() {
        var cardData = getInvalidYear();
        buyPage.checkBuyForm(cardData);
        buyPage.invalidCardExpirationDate();
    }

    // ТЕСТ ПРОЙДЕН (5.4. Поле ввода "Год" заполнено нулевым значением при покупке картой  со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenYearZeroByCard() {
        var cardData = getYearZero();
        buyPage.checkBuyForm(cardData);
        buyPage.expiredDate();
    }

    // ТЕСТ ПРОЙДЕН (6.1. Поля "Номер карты", "Месяц", "Год", "Владелец", "CVC/CVV" не заполнены при покупке картой  со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenAllEmptyFieldsByCard() {
        var cardData = getCardDataIfEmptyAllFields();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }
}



