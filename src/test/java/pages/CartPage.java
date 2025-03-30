package pages;

import com.microsoft.playwright.Page;
import java.util.List;

import com.microsoft.playwright.Page;
import java.util.List;

/**
 * Page Object для работы с корзиной покупок.
 */
public class CartPage {
    private final Page page;

    public CartPage(Page page) {
        this.page = page;
    }

    /**
     * Переход в корзину
     */
    public void navigateToCart() {
        page.click(".shopping_cart_link");
    }

    /**
     * Удаление товара из корзины
     * @param productName Название товара для удаления
     */
    public void removeItem(String productName) {
        page.locator(".cart_item:has-text('" + productName + "') button").click();
        page.waitForCondition(
                () -> !page.isVisible(".cart_item:has-text('" + productName + "')"),
                new Page.WaitForConditionOptions().setTimeout(5000)
        );
    }

    /**
     * Получение списка товаров в корзине
     * @return Список названий товаров
     */
    public List<String> getCartItems() {
        return page.locator(".inventory_item_name").allTextContents();
    }

    /**
     * Переход к оформлению заказа
     */
    public void proceedToCheckout() {
        page.click("button[data-test='checkout']");
    }
}