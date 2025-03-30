package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тестовый класс для проверки процесса оформления заказа.
 */
public class CheckoutTest extends BaseTest {
    @Test
    void testCompleteCheckout() {
        logInfo("Начало теста авторизации");
        new LoginPage(page).login("standard_user", "secret_sauce");
        new InventoryPage(page).addProductToCart("Sauce Labs Backpack");
        CartPage cartPage = new CartPage(page);
        cartPage.navigateToCart();
        cartPage.removeItem("Sauce Labs Backpack");
        assertEquals(0, cartPage.getCartItems().size());
    }
}