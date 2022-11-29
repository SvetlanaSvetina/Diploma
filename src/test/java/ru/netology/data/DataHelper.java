package ru.netology.data;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Locale;

@NoArgsConstructor // генерирует конструктор без параметров
public class DataHelper {

    public static String approvedNumber = "4444 4444 4444 4441";
    public static String declinedNumber = "4444 4444 4444 4442";
    public static String notExistNumber = "1234 1234 1234 1234";
    public static String fakerNumber = new Faker(new Locale("en-US")).finance().creditCard();
    public static Faker fakerEN = new Faker(new Locale("en-US"));
    public static Faker fakerRU = new Faker(new Locale("ru-RU"));

    @Data
    @AllArgsConstructor
    public static class CardData {
        String number, month, year, holder, cvc;
    }

    // МЕТОД ГЕНЕРАЦИИ ДАННЫХ КАРТЫ
    public static CardData getCardDataEn(String number) {
        String month = String.format("%2d", fakerEN.number().numberBetween(1, 12)).replace(" ", "0");
        int numberYear = Calendar.getInstance().get(Calendar.YEAR);
        String year = Integer.toString(fakerEN.number().numberBetween(numberYear + 1, numberYear + 2)).substring(2);
        String holder = fakerEN.name().firstName() + " " + fakerEN.name().lastName();
        String cvc = fakerEN.numerify("###");
        return new CardData(number, month, year, holder, cvc);
    }

    // ДАННЫЕ ПО ВАЛИДНОЙ КАРТЕ
    public static CardData getApprovedNumber() {
        return getCardDataEn(approvedNumber);
    }

    // ДАННЫЕ ПО НЕВАЛИДНОЙ КАРТЕ
    public static CardData getDeclinedNumber() {
        return getCardDataEn(declinedNumber);
    }

    // МЕТОД ГЕНЕРАЦИИ РАЗНЫХ ВИДОВ КАРТ
    public static CardData getNumberFaker() {
        return getCardDataEn(fakerNumber);
    }


    // ПОЛЕ ВВОДА "ВЛАДЕЛЕЦ" ЗАПОЛНЕНО СИМВОЛАМИ НА КИРИЛЛИЦЕ
    public static CardData getRussianHolder() {
        CardData card = getCardDataEn(approvedNumber);
        card.setHolder(fakerRU.name().firstName() + " " + fakerRU.name().lastName());
        return card;
    }

    // ПОЛЕ ВВОДА "ВЛАДЕЛЕЦ" ЗАПОЛНЕНО СПЕЦСИМВОЛАМИ
    public static CardData getSpecialCharactersInHolder() {
        CardData card = getCardDataEn(approvedNumber);
        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("en-US"), new RandomService());
        card.setHolder(fakeValuesService.regexify("[\\-\\=\\+\\<\\>\\!\\@\\#\\$\\%\\^\\{\\}]{1,10}"));
        return card;
    }

    // ПОЛЕ ВВОДА "ВЛАДЕЛЕЦ" ЗАПОЛНЕНО ЦИФРАМИ
    public static CardData getDigitsInHolder() {
        CardData card = getCardDataEn(approvedNumber);
        card.setHolder(fakerEN.numerify("####################"));
        return card;
    }

    // ПОЛЕ ВВОДА "ВЛАДЕЛЕЦ" ЗАПОЛНЕНО ТОЛЬКО ФАМИЛИЕЙ
    public static CardData getHolderWithoutName() {
        CardData card = getCardDataEn(approvedNumber);
        card.setHolder(fakerEN.name().lastName());
        return card;
    }

    // НПОЛЕ ВВОДА "ВЛАДЕЛЕЦ" ЗАПОЛНЕНО БОЛЬШИМ КОЛИЧЕСТВОМ СИМВОЛОВ НА ЛАТИНИЦЕ
    public static CardData getHolderWithManyLetters() {
        CardData card = getCardDataEn(approvedNumber);
        card.setHolder("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
        return card;
    }

    // ПОЛЕ ВВОДА "CVC/CVV" ЗАПОЛНЕНО 1 ЦИФРОЙ
    public static CardData getCvcCodeWithTwoDigits() {
        CardData card = getCardDataEn(approvedNumber);
        card.setCvc(fakerEN.number().digits(1) + "w");
        return card;
    }

    // ПОЛЕ ВВОДА "НОМЕР КАРТЫ" ЗАПОЛНЕНО НЕДОСТАТОЧНЫМ КОЛИЧЕСТВОМ ЦИФР
    public static CardData getNumberIfFewDigits() {
        return getCardDataEn("card" + approvedNumber.substring(3));
    }

    // ПОЛЕ ВВОДА "НОМЕР КАРТЫ" ЗАПОЛНЕНО НЕВАЛИДНЫМ ЗНАЧЕНИЕМ
    public static CardData getNumberIfNotExistInBase() {
        return getCardDataEn(notExistNumber);
    }


    // ПОЛЕ ВВОДА "МЕСЯЦ" ЗАПОЛНЕНО 1 ЦИФРОЙ
    public static CardData getMonthWithOneDigit() {
        CardData card = getCardDataEn(approvedNumber);
        card.setMonth(Integer.toString(fakerEN.number().numberBetween(1, 9)));
        return card;
    }

    // ПОЛЕ ВВОДА "МЕСЯЦ" ЗАПОЛНЕНО НУЛЕВЫМИ ЗНАЧЕНИЯМИ
    public static CardData getMonthWithZero() {
        CardData card = getCardDataEn(approvedNumber);
        card.setMonth(("00"));
        return card;
    }

    // ПОЛЕ ВВОДА "МЕСЯЦ" ЗАПОЛНЕНО ЗНАЧЕНИЕМ БОЛЬШЕ 12
    public static CardData getMonthMore12() {
        CardData card = getCardDataEn(approvedNumber);
        card.setMonth(Integer.toString(fakerEN.number().numberBetween(13, 99)));
        return card;
    }

    // ПОЛЕ ВВОДА "ГОД" ЗАПОЛНЕНО ЗНАЧЕНИЕМ ИЗ 1 ЦИФРЫ
    public static CardData getYearWithOneDigit() {
        CardData card = getCardDataEn(approvedNumber);
        card.setYear(Integer.toString(fakerEN.number().numberBetween(1, 9)));
        return card;
    }

    // ПОЛЕ ВВОДА "ГОД" ЗАПОЛНЕНО ИСТЕКШИМ СРОКОМ ГОДНОСТИ КАРТЫ
    public static CardData getExpiredCard() {
        CardData card = getCardDataEn(approvedNumber);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -0);
        int numberYear = calendar.get(Calendar.YEAR) % 1000;
        card.setYear(Integer.toString(numberYear));
        int numberMonth = calendar.get(Calendar.MONTH);
        card.setMonth(String.format("%2d", numberMonth).replace(" ", "0"));
        return card;
    }

    // ПОЛЕ ВВОДА "ГОД" ЗАПОЛНЕНО ЗАВЫШЕННЫМ СРОКОМ ГОДНОСТИ КАРТЫ
    public static CardData getInvalidYearIfInTheFarFuture() {
        CardData card = getCardDataEn(approvedNumber);
        int numberYear = Calendar.getInstance().get(Calendar.YEAR) % 1000;
        card.setYear(Integer.toString(fakerEN.number().numberBetween(numberYear + 6, 99)));
        return card;
    }

    // ПОЛЕ ВВОДА "ГОД" ЗАПОЛНЕНО НУЛЕВЫМ ЗНАЧЕНИЕМ
    public static CardData getYearWithZero() {
        CardData card = getCardDataEn(approvedNumber);
        card.setYear("00");
        return card;
    }

    // ВСЕ ПОЛЯ ПУСТЫЕ
    public static CardData getCardDataIfEmptyAllFields(){
        return new CardData("", "", "", "", "");
    }

}