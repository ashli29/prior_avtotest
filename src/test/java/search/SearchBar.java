package search;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class SearchBar {



    @BeforeEach
    void openSiteAndSearchBar() {
        open("https://www.priorbank.by/");
        $(".search__icon-i").click();
    }

    @AfterEach
     void screenshot(){
        Screenshot screenshot = new Screenshot();
        screenshot.takeScreenshot();
    }


    @ParameterizedTest(name = "{index} Проверка поиска для {0}")
    @ValueSource (strings = {"bank", "credit", "cards", "payments"})
    @DisplayName("Проверка ввода англоязычных слов")
    public void searchEnglishWords(String word) {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        step("В строке поиска написать англоязычное слово", () ->{
            $(".search__input-modal").setValue(word);
            $(".search-portlet__tooltips-container").shouldBe(visible);
        });
        step("Нажать на Enter", () ->{
            $(".search__input-modal").pressEnter();
            $(".search-portlet__results-container").shouldHave(text(word));
            $(".search-portlet__highlighted-text").shouldHave
                    (cssValue("background-color", "rgba(255, 237, 0, 0.25)"));
        });

    }
    @Test
    @DisplayName("Проверка написания пробела в строке поиска")
    public void searchRussianWords() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        step("В строку поиска ввести пробел", () ->{
            $(".search__input-modal").setValue(" ");
            $(".search__input-modal").$("placeholder").shouldNotBe(visible);
        });
        step("Кликнуть на кнопку Х", () ->{
            $(".search__input-modal-clear").click();
            $(".search__input-modal").$("placeholder").shouldBe(visible);
        });

    }

}
