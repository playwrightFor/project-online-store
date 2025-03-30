package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Тестовый класс для проверки функционала корзины покупок.
 * Содержит тест добавления товара в корзину.
 */
public class CartTest extends BaseTest {
    @Test
    void testAddToCart() {
        logInfo("Начало теста авторизации");
        LoginPage loginPage = new LoginPage(page);

        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(page);
        inventoryPage.addProductToCart("Sauce Labs Backpack");

        CartPage cartPage = new CartPage(page);
        cartPage.navigateToCart();
        assertTrue(cartPage.getCartItems().contains("Sauce Labs Backpack"));
    }
}