import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class CardDeliveryTest {

    @BeforeEach
    void Setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldMeetingDay() {
        String meetingDate = DataGenerator.forwardDays(3);
        String meetingDateNew = DataGenerator.forwardDays(7);
        $("[placeholder='Город']").setValue(DataGenerator.choiseCity());
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(meetingDate);
        $("[name=name]").setValue(DataGenerator.createName());
        $("[name=phone]").setValue(DataGenerator.createPhone());
        $(".checkbox__box").click();
        $(".button__text").click();
        $(withText("Успешно")).shouldBe(visible);
        $(".notification__content").shouldBe(visible)
                .shouldHave(exactText("Встреча успешно запланирована на " + meetingDate));
        $("input[placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(meetingDateNew);
        $(".button__text").click();
        $(withText("У вас уже запланирована встреча на другую дату. Перепланировать?")).shouldBe(visible);
        $("[data-test-id=replan-notification] button.button").click();
        $(withText("Успешно")).shouldBe(visible);
        $(".notification__content").shouldBe(visible)
                .shouldHave(exactText("Встреча успешно запланирована на " + meetingDateNew));
    }

    @Test
    void shouldMeetingDayWithInvalidName() {
        String meetingDate = DataGenerator.forwardDays(3);
        $("[placeholder='Город']").setValue(DataGenerator.choiseCity());
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(meetingDate);
        $("[name=name]").setValue("Andrey Starkov");
        $("[name=phone]").setValue(DataGenerator.createPhone());
        $(".checkbox__box").click();
        $(".button__text").click();
        $(withText("Имя и Фамилия указаные неверно"))
                .shouldBe(visible).shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldMeetingDayWithInvalidPhone() { // баг - происходит заказ карты с некорректным номером телефона
        String meetingDate = DataGenerator.forwardDays(4);
        $("[placeholder='Город']").setValue(DataGenerator.choiseCity());
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(meetingDate);
        $("[name=name]").setValue(DataGenerator.createName());
        $("[name=phone]").setValue("+7981");
        $(".checkbox__box").click();
        $(".button__text").click();
        $(withText("Успешно")).shouldBe(visible);
        $(".notification__content").shouldBe(visible)
                .shouldHave(exactText("Встреча успешно запланирована на " + meetingDate));
    }

    @Test
    void shouldMeetingDayWithNameФёдор() { // баг - если в имени Ё форма считает имя некорректным
        String meetingDate = DataGenerator.forwardDays(5);
        $("[placeholder='Город']").setValue(DataGenerator.choiseCity());
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(meetingDate);
        $("[name=name]").setValue("Фёдор Орлов");
        $("[name=phone]").setValue(DataGenerator.createPhone());
        $(".checkbox__box").click();
        $(".button__text").click();
        $(withText("Имя и Фамилия указаные неверно"))
                .shouldBe(visible).shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldMeetingDayWithInvalidCity() {
        String meetingDate = DataGenerator.forwardDays(6);
        $("[placeholder='Город']").setValue("Sankt-Peterburg");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(meetingDate);
        $("[name=name]").setValue(DataGenerator.createName());
        $("[name=phone]").setValue(DataGenerator.createPhone());
        $(".checkbox__box").click();
        $(".button__text").click();
        $(byText("Доставка в выбранный город недоступна")).shouldBe(visible).shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldMeetingDayEmptyFormCity() {
        String meetingDate = DataGenerator.forwardDays(7);
        $("[placeholder='Город']").setValue("");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(meetingDate);
        $("[name=name]").setValue(DataGenerator.createName());
        $("[name=phone]").setValue(DataGenerator.createPhone());
        $(".checkbox__box").click();
        $(".button__text").click();
        $(byText("Поле обязательно для заполнения")).shouldBe(visible).shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldMeetingDayEmptyFormName() {
        String meetingDate = DataGenerator.forwardDays(8);
        $("[placeholder='Город']").setValue(DataGenerator.choiseCity());
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(meetingDate);
        $("[name=name]").setValue("");
        $("[name=phone]").setValue(DataGenerator.createPhone());
        $(".checkbox__box").click();
        $(".button__text").click();
        $(byText("Поле обязательно для заполнения")).shouldBe(visible).shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldMeetingDayEmptyFormPhone() {
        String meetingDate = DataGenerator.forwardDays(9);
        $("[placeholder='Город']").setValue(DataGenerator.choiseCity());
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(meetingDate);
        $("[name=name]").setValue(DataGenerator.createName());
        $("[name=phone]").setValue("");
        $(".checkbox__box").click();
        $(".button__text").click();
        $(byText("Поле обязательно для заполнения")).shouldBe(visible).shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldMeetingDayEmptyCheckBox() {
        String meetingDate = DataGenerator.forwardDays(10);
        $("[placeholder='Город']").setValue(DataGenerator.choiseCity());
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(meetingDate);
        $("[name=name]").setValue(DataGenerator.createName());
        $("[name=phone]").setValue(DataGenerator.createPhone());
        $(".button__text").click();
        $(".input_invalid[data-test-id='agreement']").shouldBe(visible);
    }


}


