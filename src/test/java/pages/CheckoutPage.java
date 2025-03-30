package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

/**
 * Page Object для страницы оформления заказа
 */
public class CheckoutPage {
    private final Page page;

    /**
     * Конструктор с ожиданием загрузки формы
     * @param page Экземпляр страницы Playwright
     */
    public CheckoutPage(Page page) {
        this.page = page;
        page.waitForSelector("input[data-test='firstName']",
                new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
    }

    /**
     * Заполняет данные доставки
     * @param firstName Имя
     * @param lastName Фамилия
     * @param postalCode Почтовый индекс
     * @throws AssertionError если не удалось заполнить поле
     */
    public void fillShippingInfo(String firstName, String lastName, String postalCode) {
        page.fill("input[data-test='firstName']", firstName);
        page.fill("input[data-test='lastName']", lastName);
        page.fill("input[data-test='postalCode']", postalCode);

        if (lastName.isEmpty() || page.inputValue("input[data-test='lastName']").isEmpty()) {
            throw new AssertionError("Фамилия обязательна для заполнения");
        }

        page.click("input[data-test='continue']");
    }

    /**
     * Завершает оформление заказа
     */
    public void completePurchase() {
        page.click("button[data-test='finish']");
        page.waitForURL(
                url -> url.contains("checkout-complete.html"),
                new Page.WaitForURLOptions().setTimeout(10000)
        );
    }
}