package usecases;

import org.busygind.Configuration;
import org.busygind.MainPage;
import org.busygind.SystemAccess;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.busygind.Configuration.BASE_URL;
import static org.busygind.Configuration.getElementBySelector;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainPageTest {

    @BeforeAll
    public static void initDrivers() {
        Configuration.prepareDrivers();
    }

    @ParameterizedTest
    @CsvSource(
            value = {
                    "Hello:Привет:Привіт",
                    "How are you:Как вы:Як справи",
                    "Nice to meet you:Рад встрече:приємно познайомитись"
            },
            delimiter = ':'
    )
    public void translatorTest(String source, String expected, String ukrExpected) {
        List<WebDriver> drivers = Configuration.getDrivers();
        drivers.parallelStream().forEach(webDriver -> {
            webDriver.get(BASE_URL);

            MainPage mainPage = new MainPage(webDriver);
            try {
                assertEquals(expected, mainPage.getTranslation(source));
                mainPage.clearSource();
                mainPage.changeTargetLanguageOnUkrainian();
                assertEquals(ukrExpected, mainPage.getTranslation(source));
                mainPage.clearSource();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        drivers.forEach(WebDriver::quit);
    }

    @Test
    public void clipboardCopyTest() {
        List<WebDriver> drivers = Configuration.getDrivers();
        drivers.parallelStream().forEach(webDriver -> {
            webDriver.get(BASE_URL);

            MainPage mainPage = new MainPage(webDriver);
            try {
                String translate = mainPage.getTranslation("Hello");
                WebElement copyButton = getElementBySelector(
                        webDriver,
                        By.xpath("//div[6]/div[2]/span[2]/button/div[3]")
                );
                copyButton.click();
                String clipboard = SystemAccess.getClipboardContents();
                assertEquals(translate, clipboard);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void trySaveTranslationTestWithoutLogin() {
        List<WebDriver> drivers = Configuration.getDrivers();
        drivers.parallelStream().forEach(webDriver -> {
            webDriver.get(BASE_URL);

            MainPage mainPage = new MainPage(webDriver);
            try {
                mainPage.getTranslation("Hello");
                mainPage.saveTranslation();
                mainPage.closeLoginAdviceWhenSaveWindow();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void tryOpenHistoryTestWithoutLogin() {
        List<WebDriver> drivers = Configuration.getDrivers();
        drivers.parallelStream().forEach(webDriver -> {
            webDriver.get(BASE_URL);

            MainPage mainPage = new MainPage(webDriver);
            mainPage.openHistory();
            mainPage.closeLoginAdviceWhenHistoryWindow();
        });
    }
}
