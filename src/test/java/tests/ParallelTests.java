package tests;

import base.BaseTest;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.InventoryPage;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Класс для тестирования параллельного выполнения операций с корзиной и оформлением заказа.
 * Содержит комплексные тесты, проверяющие:
 * - Полный процесс оформления заказа
 * - Параллельное управление содержимым корзины
 */
public class ParallelTests extends BaseTest {

    @Test
    @DisplayName("Полный цикл оформления заказа: авторизация → корзина → доставка → подтверждение")
    void testParallelCheckoutProcess() {
        logInfo("Начало теста авторизации");
        LoginPage loginPage = new LoginPage(page);
        loginPage.login("standard_user", "secret_sauce");

        assertTrue(page.url().contains("/inventory.html"),
                "Не удалось авторизоваться");

        InventoryPage inventoryPage = new InventoryPage(page);
        CartPage cartPage = new CartPage(page);
        CheckoutPage checkoutPage;

        inventoryPage.addProductToCart("Sauce Labs Backpack");
        page.waitForCondition(
                () -> inventoryPage.getCartItemCount() == 1,
                new Page.WaitForConditionOptions().setTimeout(10000)
        );

        cartPage.navigateToCart();
        page.waitForURL("**/cart.html");

        cartPage.proceedToCheckout();

        checkoutPage = new CheckoutPage(page);

        checkoutPage.fillShippingInfo("John", "Doe", "12345");

        page.waitForURL("**/checkout-step-two.html");

        checkoutPage.completePurchase();
        assertTrue(page.url().contains("/checkout-complete.html"));
    }


    @Test
    @DisplayName("Управление корзиной в изолированном контексте: добавление 2 товаров → удаление 1")
    void testParallelCartManagement() {
        logInfo("Начало теста авторизации");
        try (BrowserContext context = browser.newContext()) {
            Page page = context.newPage();
            LoginPage loginPage = new LoginPage(page);
            loginPage.login("standard_user", "secret_sauce");
            InventoryPage inventoryPage = new InventoryPage(page);
            CartPage cartPage = new CartPage(page);

            inventoryPage.addProductToCart("Sauce Labs Bike Light");
            inventoryPage.addProductToCart("Sauce Labs Bolt T-Shirt");

            int cartCount = inventoryPage.getCartItemCount();
            assertEquals(2, cartCount, "Счетчик корзины должен показывать 2 товара");

            cartPage.navigateToCart();
            cartPage.removeItem("Sauce Labs Bike Light");

            assertEquals(1, cartPage.getCartItems().size(),
                    "После удаления должен остаться 1 товар");
            assertTrue(cartPage.getCartItems().contains("Sauce Labs Bolt T-Shirt"),
                    "Оставшийся товар должен соответствовать ожиданиям");
        }
    }
}
