package org.busygind;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Configuration {

    private static final String PROPERTIES_PATH = "/Users/busygind02/IdeaProjects/itmo-road/Software-testing-technologies/lab3/src/main/resources/auto-testing.properties";
    public static final String CHROME_SYSTEM_PROPERTY_NAME = "webdriver.chrome.driver";
    public static final String CHROME_SYSTEM_PROPERTY_PATH = "/Users/busygind02/IdeaProjects/itmo-road/Software-testing-technologies/lab3/src/main/resources/drivers/chromedriver";
    public static final String FIREFOX_SYSTEM_PROPERTY_NAME = "webdriver.gecko.driver";
    public static final String FIREFOX_SYSTEM_PROPERTY_PATH = "/Users/busygind02/IdeaProjects/itmo-road/Software-testing-technologies/lab3/src/main/resources/drivers/geckodriver";
    public static final String BASE_URL = "https://translate.google.ru/";
    public static final String STACK_OVERFLOW_URL = "https://stackoverflow.com/";

    public static void prepareDrivers() {
        System.setProperty(CHROME_SYSTEM_PROPERTY_NAME, CHROME_SYSTEM_PROPERTY_PATH);
        System.setProperty(FIREFOX_SYSTEM_PROPERTY_NAME, FIREFOX_SYSTEM_PROPERTY_PATH);
    }

    public static WebElement getElementBySelector(WebDriver driver, By selector) {
        WebDriverWait driverWait = new WebDriverWait(driver, Duration.of(5, ChronoUnit.SECONDS));
        return driverWait.until(ExpectedConditions.visibilityOfElementLocated(selector));
    }

    public static void waitUntilPageLoads(WebDriver driver, Duration timeout) {
        WebDriverWait waitDriver = new WebDriverWait(driver, timeout);
        waitDriver.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    private static ChromeDriver getChromeDriver() {
        if (!System.getProperties().containsKey(CHROME_SYSTEM_PROPERTY_NAME)) {
            throw new RuntimeException("Chrome driver is not set properly");
        }
        return new ChromeDriver();
    }

    private static FirefoxDriver getFirefoxDriver() {
        if (!System.getProperties().containsKey(FIREFOX_SYSTEM_PROPERTY_NAME)) {
            throw new RuntimeException("Firefox driver is not set properly");
        }
        return new FirefoxDriver();
    }

    public static List<WebDriver> getDrivers() {
        List<WebDriver> drivers = new ArrayList<>();
        try {
            List<String> properties = Files.readAllLines(Paths.get(PROPERTIES_PATH));
            for (String property : properties) {
                if (property.startsWith("web-driver")) {
                    switch (property.toLowerCase().split("=")[1]) {
                        case "chrome" -> {
                            drivers.add(getChromeDriver());
                            return drivers;
                        }
                        case "firefox" -> {
                            drivers.add(getFirefoxDriver());
                            return drivers;
                        }
                        case "both" -> {
                            drivers.add(getChromeDriver());
                            drivers.add(getFirefoxDriver());
                            return drivers;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Web driver is not specified");
    }

    private static void switchOnNewWindow(WebDriver driver) {
        List<String> windowsIds = driver.getWindowHandles().stream().toList();
        driver.switchTo().window(windowsIds.get(windowsIds.size() - 1));
    }
}
