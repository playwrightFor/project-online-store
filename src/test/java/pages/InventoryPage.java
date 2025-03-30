package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

/**
 * Page Object для страницы каталога товаров
 */
public class InventoryPage {
    private final Page page;

    /**
     * Конструктор с ожиданием загрузки списка товаров
     * @param page Экземпляр страницы Playwright
     */
    public InventoryPage(Page page) {
        this.page = page;
        page.waitForSelector(".inventory_list",
                new Page.WaitForSelectorOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(10000));
    }

    /**
     * Добавляет товар в корзину
     * @param productName Название товара (должно точно совпадать)
     */
    public void addProductToCart(String productName) {
        page.locator(String.format(
                        "div.inventory_item:has-text('%s') button:has-text('Add to cart')",
                        productName))
                .click();
    }

    /**
     * Получает количество товаров в корзине
     * @return Число товаров (0 если корзина пуста)
     * @throws NumberFormatException если элемент счетчика не найден
     */
    public int getCartItemCount() {
        try {
            return Integer.parseInt(page.textContent(".shopping_cart_badge"));
        } catch (Exception e) {
            return 0;
        }
    }
}