package usecases;

import org.busygind.AboutPage;
import org.busygind.Configuration;
import org.busygind.MainPage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.awt.*;
import java.util.List;

import static org.busygind.Configuration.BASE_URL;
import static org.junit.jupiter.api.Assertions.*;

public class AboutPageTest {

    @BeforeAll
    public static void initDrivers() {
        Configuration.prepareDrivers();
    }

    @Test
    void headerTextTest() {
        List<WebDriver> drivers = Configuration.getDrivers();
        drivers.parallelStream().forEach(webDriver -> {
            webDriver.get(BASE_URL);

            MainPage mainPage = new MainPage(webDriver);
            mainPage.goToAboutPage();
            AboutPage aboutPage = new AboutPage(webDriver);
            assertEquals("Вы сможете понимать других и выражать свои мысли на разных языках", aboutPage.getHeader());
        });
        drivers.forEach(WebDriver::quit);
    }

    @Test
    void changePageLanguageTest() {
        List<WebDriver> drivers = Configuration.getDrivers();
        drivers.parallelStream().forEach(webDriver -> {
            webDriver.get(BASE_URL);

            MainPage mainPage = new MainPage(webDriver);
            mainPage.goToAboutPage();
            AboutPage aboutPage = new AboutPage(webDriver);
            try {
                aboutPage.changeLanguageOnPolish();
            } catch (AWTException e) {
                throw new RuntimeException(e);
            }
            assertEquals("Poznaj świat wokół Ciebie i rozmawiaj w różnych językach", aboutPage.getHeader());
        });
        drivers.forEach(WebDriver::quit);
    }
}
