package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

/**
 * Page Object для страницы авторизации
 */
public class LoginPage {
    private final Page page;
    private final String URL = "https://www.saucedemo.com/";

    /**
     * Инициализирует страницу авторизации
     * @param page Экземпляр страницы Playwright
     */
    public LoginPage(Page page) {
        this.page = page;
        page.navigate(URL);
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
    }

    /**
     * Выполняет авторизацию
     * @param username Логин пользователя
     * @param password Пароль пользователя
     */
    public void login(String username, String password) {
        page.locator("input[data-test='username']").waitFor();
        page.locator("input[data-test='password']").waitFor();

        page.fill("input[data-test='username']", username);
        page.fill("input[data-test='password']", password);

        page.click("input[data-test='login-button']");
        page.waitForURL(
                url -> url.contains("/inventory.html"),
                new Page.WaitForURLOptions().setTimeout(10000)
        );
    }
}