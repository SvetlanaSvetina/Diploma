package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


// СТРАНИЦА ВВОДА ДАННЫХ
public class BuyPage {

    private final SelenideElement number = $("input[placeholder='0000 0000 0000 0000']");
    private final SelenideElement month = $("input[placeholder='08']");
    private final SelenideElement year = $("input[placeholder='22']");
    private final SelenideElement cvc = $("input[placeholder='999']");
    private final ElementsCollection set = $$(".input__control");
    private final SelenideElement holderField = set.get(3);


    private final SelenideElement continueButton = $$("button").find(exactText("Продолжить"));
    private final SelenideElement successResult = $(byText("Операция одобрена Банком."));

    private final SelenideElement emptyField = $(byText("Поле обязательно для заполнения"));
    private final SelenideElement incorrectFormat = $(byText("Неверный формат"));
    private final SelenideElement invalidCardExpirationDate = $(byText("Неверно указан срок действия карты"));
    private final SelenideElement expiredDatePass = $(byText("Истёк срок действия карты"));

    private final SelenideElement failureResult = $(byText("Ошибка! Банк отказал в проведении операции."));


    public void checkBuyForm(DataHelper.CardData cardData) {
        number.setValue(cardData.getNumber());
        month.setValue(cardData.getMonth());
        year.setValue(cardData.getYear());
        holderField.setValue(cardData.getHolder());
        cvc.setValue(cardData.getCvc());
        continueButton.click();
    }

    public void emptyField() {
        emptyField.shouldBe(Condition.visible);
    }

    public void incorrectFormat() {
        incorrectFormat.shouldBe(Condition.visible);
    }

    public void invalidCardExpirationDate() {
        invalidCardExpirationDate.shouldBe(Condition.visible);
    }

    public void expiredDate() {
        expiredDatePass.shouldBe(Condition.visible);
    }

    public void successResultNotification() {
        successResult.shouldHave(Condition.visible, Duration.ofSeconds(30));
    }

    public void failureResultNotification() {
        failureResult.shouldHave(Condition.visible, Duration.ofSeconds(30));
    }
}

