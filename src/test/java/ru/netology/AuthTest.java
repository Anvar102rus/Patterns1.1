package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthTest {
    private final PersonGanerator.AuthUser activeUser = PersonGanerator.AuthUser.getValidActuveUser();
    private final PersonGanerator.AuthUser blockedUser = PersonGanerator.AuthUser.getValidBlockedUser();
    private final PersonGanerator.AuthUser invalidPassword = PersonGanerator.AuthUser.getInvalidPassword();
    private final PersonGanerator.AuthUser invalidLogin = PersonGanerator.AuthUser.getInvalidLogin();

    @BeforeEach
    void setupClass() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldSendValidActiveUser() {
        $("[data-test-id=login] .input__control").sendKeys(activeUser.getLogin());
        $("[data-test-id=password] .input__control").sendKeys(activeUser.getPassword());
        $("[data-test-id=action-login]").click();
        $(byText("Личный кабинет")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldSendValidBlockedUser(){
        $("[data-test-id=login] .input__control").sendKeys(blockedUser.login);
        $("[data-test-id=password] .input__control").sendKeys(blockedUser.password);
        $("[data-test-id=action-login]").click();
        $(".notification_status_error").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=error-notification]  .notification__title")
                .shouldHave(exactText("Ошибка"));
        $("[data-test-id=error-notification]  .notification__content")
                .shouldHave(exactText("Ошибка! Пользователь заблокирован"));
    }

    @Test
    void shouldSendInvalidPassword(){
        $("[data-test-id=login] .input__control").sendKeys(invalidPassword.login);
        $("[data-test-id=password] .input__control").sendKeys(invalidPassword.password);
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=error-notification]  .notification__title")
                .shouldHave(exactText("Ошибка"));
        $("[data-test-id=error-notification]  .notification__content")
                .shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldSendInvalidLogin(){
        $("[data-test-id=login] .input__control").sendKeys(invalidLogin.login);
        $("[data-test-id=password] .input__control").sendKeys(invalidLogin.password);
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=error-notification]  .notification__title")
                .shouldHave(exactText("Ошибка"));
        $("[data-test-id=error-notification]  .notification__content")
                .shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));
    }
}