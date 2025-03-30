package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тестовый класс для проверки функционала авторизации.
 */
public class LoginTest extends BaseTest {

    @Test
    void testSuccessfulLogin() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.login("standard_user", "secret_sauce");
        if(page.url().contains("/inventory.html")) {
        } else {
        }
        assertTrue(page.url().contains("/inventory.html"));
    }
}