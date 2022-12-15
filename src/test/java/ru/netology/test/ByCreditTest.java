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

public class ByCreditTest extends InitialTest {

    MainPage mainPage = new MainPage();
    BuyPage buyPage = new BuyPage();

    @BeforeEach
    void setUpForPayWithCard() {
        mainPage.buyByCredit();
    }

    // ТЕСТ ПРОЙДЕН. (2.1. Успешная покупка тура картой со статусом APPROVED используя кнопку "Купить в кредит")
    @Test
    public void shouldSuccessPayApprovedCredit() {

        var cardData = getApprovedNumber();
        buyPage.checkBuyForm(cardData);
        buyPage.successResultNotification();

        var expectedStatus = "APPROVED";
        var actualStatus = getCreditRequestInfo();
        assertEquals(expectedStatus, actualStatus);


        var bankIdExpected = getCreditRequestInfo();
        var paymentIdActual = getOrderInfo();
        assertNotNull(bankIdExpected);
        assertNotNull(paymentIdActual);
        assertEquals(bankIdExpected, paymentIdActual);

    }

    // ТЕСТ НЕ ПРОЙДЕН. (2.2. Отклонение операции в покупке тура при оплате картой со статусом DECLINED используя кнопку "Купить в кредит")
    @Test
    public void shouldPaymentRejectionDeclinedCredit() {
        var cardData = getDeclinedNumber();
        buyPage.checkBuyForm(cardData);
        buyPage.failureResultNotification();

        var expectedStatus = "Declined";
        var actualStatus = getCreditRequestInfo();
        assertEquals(expectedStatus, actualStatus);

        var bankIdExpected = getCreditRequestInfo();
        var paymentIdActual = getOrderInfo();
        assertNotNull(bankIdExpected);
        assertNotNull(paymentIdActual);
        assertEquals(bankIdExpected, paymentIdActual);
    }

    // ТЕСТ НЕ ПРОЙДЕН ( НЕГАТИВНЫЕ СЦЕНАРИИ. 1.1. Поле ввода "Владелец" заполнено символами на кирилице используя кнопку "Купить в кредит" со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenOwnerFilledCyrillicByCredit() {
        var cadrData = getRussianHolder();
        buyPage.checkBuyForm(cadrData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ НЕ ПРОЙДЕН (1.2. Поле ввода "Владелец" заполнено спец.символами используя кнопку "Купить в кредит" со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenOwnerFilledWitnSymbolsByCredit() {
        var cardData = getSpecialCharactersInHolder();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ НЕ ПРОЙДЕН (1.3. Поле ввода "Владелец" заполнено цифрами используя кнопку "Купить в кредит")
    @Test
    public void shouldPaymentRejectionWhenOwnerFilledWitnNumbersByCredit() {
        var cardData = getNumbersInHolder();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }


    // ТЕСТ НЕ ПРОЙДЕН (1.4. Поле ввода "Владелец" заполнено только фамилией на латинице используя кнопку "Купить в кредит" со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenOwnerOnlyLastNameByCredit() {
        var cardData = getOnlyLastName();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ НЕ ПРОЙДЕН (1.5. Поле ввода "Владелец" заполнено большим количеством символов на латинице используя кнопку "Купить в кредит")
    @Test
    public void shouldPaymentRejectionWhenManyLettersByCredit() {
        var cardData = getWithManyLetters();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ ПРОЙДЕН (2.1. Поле ввода "CVC/CVV" заполнено одной цифрой используя кнопку "Купить в кредит" со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWithCvcOneNumberByCredit() {
        var cardData = getCvcWithOneNumber();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ ПРОЙДЕН (3.1. Поле ввода "Номер карты" заполненно недостаточным количеством цифр используя кнопку "Купить в кредит" со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWithNotEnoughNumberByCredit() {
        var cardData = getNotEnoughNumbers();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ ПРОЙДЕН (3.2. Поле ввода "Номер карты" заполнено невалидным значением используя кнопку "Купить в кредит" со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWithNumberNotExistByCredit() {
        var cardData = getNumberNotExist();
        buyPage.checkBuyForm(cardData);
        buyPage.failureResultNotification();
    }

    // ТЕСТ ПРОЙДЕН (4.1. Поле ввода "Месяц" заполнено одной цифрой используя кнопку "Купить в кредит"  со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenMonthOneNumberByCredit() {
        var cardData = getMonthOneNumber();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ НЕ ПРОЙДЕН (4.2. Поле ввода "Месяц" заполнено нулевыми значениями используя кнопку "Купить в кредит"  со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenMonthZeroByCredit() {
        var cardData = getMonthWithZero();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ ПРОЙДЕН (4.3. Поле ввода "Месяц" заполнено значением больше 12 используя кнопку "Купить в кредит" со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenMonthMore12ByCredit() {
        var cardData = getMonthMore12();
        buyPage.checkBuyForm(cardData);
        buyPage.invalidCardExpirationDate();
    }

    // ТЕСТ ПРОЙДЕН (5.1. Поле ввода "Год" заполнено значением из 1 цифры используя кнопку "Купить в кредит" со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenYearOneNumberByCredit() {
        var cardData = getYearWithOneNumber();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ ПРОЙДЕН (5.2. Поле ввода "Год" заполнено истекшим сроком годности карты используя кнопку "Купить в кредит" со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenYearOfCreditCardExpired() {
        var cardData = getExpiredCard();
        buyPage.checkBuyForm(cardData);
        buyPage.invalidCardExpirationDate();
    }

    // ТЕСТ ПРОЙДЕН (5.3. Поле ввода "Год" заполнено завышенным сроком годности карты используя кнопку "Купить в кредит" со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenInvalidYearByCredit() {
        var cardData = getInvalidYear();
        buyPage.checkBuyForm(cardData);
        buyPage.invalidCardExpirationDate();
    }

    // ТЕСТ ПРОЙДЕН (5.4. Поле ввода "Год" заполнено нулевым значением используя кнопку "Купить в кредит" со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenYearZeroByCredit() {
        var cardData = getYearZero();
        buyPage.checkBuyForm(cardData);
        buyPage.expiredDate();
    }

    // ТЕСТ ПРОЙДЕН (6.1. Поля "Номер карты", "Месяц", "Год", "Владелец", "CVC/CVV" не заполнены используя кнопку "Купить в кредит"  со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenAllEmptyFieldsByCredit() {
        var cardData = getCardDataIfEmptyAllFields();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }
}

