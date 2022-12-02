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

    // ТЕСТ НЕ ПРОЙДЕН ( НЕГАТИВНЫЕ СЦЕНАРИИ. 1.1. Поле ввода "Владелец" заполнено символами на кирилице используя кнопку "Купить в кредит" со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenOwnerFilledCyrillicByCredit() {
        val cadrData = getRussianHolder();
        buyPage.checkBuyForm(cadrData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ НЕ ПРОЙДЕН (1.2. Поле ввода "Владелец" заполнено спец.символами используя кнопку "Купить в кредит" со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenOwnerFilledWitnSymbolsByCredit() {
        val cardData = getSpecialCharactersInHolder();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ НЕ ПРОЙДЕН (1.3. Поле ввода "Владелец" заполнено цифрами используя кнопку "Купить в кредит")
    @Test
    public void shouldPaymentRejectionWhenOwnerFilledWitnNumbersByCredit() {
        val cardData = getNumbersInHolder();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }


    // ТЕСТ НЕ ПРОЙДЕН (1.4. Поле ввода "Владелец" заполнено только фамилией на латинице используя кнопку "Купить в кредит" со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenOwnerOnlyLastNameByCredit() {
        val cardData = getOnlyLastName();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ НЕ ПРОЙДЕН (1.5. Поле ввода "Владелец" заполнено большим количеством символов на латинице используя кнопку "Купить в кредит")
    @Test
    public void shouldPaymentRejectionWhenManyLettersByCredit() {
        val cardData = getWithManyLetters();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ ПРОЙДЕН (2.1. Поле ввода "CVC/CVV" заполнено одной цифрой используя кнопку "Купить в кредит" со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWithCvcOneNumberByCredit() {
        val cardData = getCvcWithOneNumber();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ ПРОЙДЕН (3.1. Поле ввода "Номер карты" заполненно недостаточным количеством цифр используя кнопку "Купить в кредит" со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWithNotEnoughNumberByCredit() {
        val cardData = getNotEnoughNumbers();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ ПРОЙДЕН (3.2. Поле ввода "Номер карты" заполнено невалидным значением используя кнопку "Купить в кредит" со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWithNumberNotExistByCredit() {
        val cardData = getNumberNotExist();
        buyPage.checkBuyForm(cardData);
        buyPage.failureResultNotification();
    }

    // ТЕСТ ПРОЙДЕН (4.1. Поле ввода "Месяц" заполнено одной цифрой используя кнопку "Купить в кредит"  со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenMonthOneNumberByCredit() {
        val cardData = getMonthOneNumber();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ НЕ ПРОЙДЕН (4.2. Поле ввода "Месяц" заполнено нулевыми значениями используя кнопку "Купить в кредит"  со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenMonthZeroByCredit() {
        val cardData = getMonthWithZero();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ ПРОЙДЕН (4.3. Поле ввода "Месяц" заполнено значением больше 12 используя кнопку "Купить в кредит" со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenMonthMore12ByCredit() {
        val cardData = getMonthMore12();
        buyPage.checkBuyForm(cardData);
        buyPage.invalidCardExpirationDate();
    }

    // ТЕСТ ПРОЙДЕН (5.1. Поле ввода "Год" заполнено значением из 1 цифры используя кнопку "Купить в кредит" со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenYearOneNumberByCredit() {
        val cardData = getYearWithOneNumber();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }

    // ТЕСТ ПРОЙДЕН (5.2. Поле ввода "Год" заполнено истекшим сроком годности карты используя кнопку "Купить в кредит" со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenYearOfCreditCardExpired() {
        val cardData = getExpiredCard();
        buyPage.checkBuyForm(cardData);
        buyPage.invalidCardExpirationDate();
    }

    // ТЕСТ ПРОЙДЕН (5.3. Поле ввода "Год" заполнено завышенным сроком годности карты используя кнопку "Купить в кредит" со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenInvalidYearByCredit() {
        val cardData = getInvalidYear();
        buyPage.checkBuyForm(cardData);
        buyPage.invalidCardExpirationDate();
    }

    // ТЕСТ ПРОЙДЕН (5.4. Поле ввода "Год" заполнено нулевым значением используя кнопку "Купить в кредит" со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenYearZeroByCredit() {
        val cardData = getYearZero();
        buyPage.checkBuyForm(cardData);
        buyPage.expiredDate();
    }

    // ТЕСТ ПРОЙДЕН (6.1. Поля "Номер карты", "Месяц", "Год", "Владелец", "CVC/CVV" не заполнены используя кнопку "Купить в кредит"  со статусом APPROVED)
    @Test
    public void shouldPaymentRejectionWhenAllEmptyFieldsByCredit() {
        val cardData = getCardDataIfEmptyAllFields();
        buyPage.checkBuyForm(cardData);
        buyPage.incorrectFormat();
    }
}

