package base;

import com.microsoft.playwright.Page;
import lombok.Getter;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Делает скриншоты при падении тестов.
 * Автоматически сохраняет в target/screenshots/ с именем теста.
 */
public class ScreenshotWatcher implements TestWatcher {
    /**
     * -- GETTER --
     * Возвращает путь к последнему скриншоту
     */
    @Getter
    private static String lastScreenshotPath;
    private static final ThreadLocal<Page> pageHolder = new ThreadLocal<>();


    /** Вызывается при падении теста */
    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        Page page = pageHolder.get();
        if (page != null) {
            String testName = context.getDisplayName().replaceAll("[^a-zA-Z0-9]", "_");
            Path path = Paths.get("target/screenshots", testName + "_FAILED.png");
            try {
                page.screenshot(new Page.ScreenshotOptions().setPath(path));
                lastScreenshotPath = path.toString();
            } catch (Exception e) {
                System.err.println("Ошибка при создании скриншота: " + e.getMessage());
            }
        }
    }
}