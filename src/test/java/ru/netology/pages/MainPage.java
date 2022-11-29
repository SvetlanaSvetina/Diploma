package ru.netology.pages;

import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;


// ГЛАВНАЯ СТРАНИЦА
public class MainPage {
    private static final SelenideElement payWithCardButton = $$("button").find(exactText("Купить"));
    private static final SelenideElement payWithCreditButton = $$("button").find(exactText("Купить в кредит"));

    public void buyByCard() {
        payWithCardButton.click();
    }

    public void buyByCredit() {
        payWithCreditButton.click();
    }
}



