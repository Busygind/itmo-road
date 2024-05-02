package org.busygind;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.busygind.Configuration.STACK_OVERFLOW_URL;
import static org.busygind.Configuration.getElementBySelector;

// КЛАСС СОХРАНЕН В ПАМЯТЬ О ПОТРАЧЕННОМ ВРЕМЕНИ В ПОПЫТКАХ АВТОРИЗОВАТЬСЯ via Google ИСПОЛЬЗУЯ ПРОКЛЯТЫЙ СЕЛЕНИУМ
public class StackOverflowPage {
    public static void loginViaGoogle(WebDriver driver) throws InterruptedException {
        driver.get(STACK_OVERFLOW_URL);
        WebElement stupidWindowSO = getElementBySelector(
                driver,
                By.xpath("//div[5]/iframe")
        );
        driver.switchTo().frame(stupidWindowSO);
        WebElement closeStupidWindowSOButton = getElementBySelector(
                driver,
                By.xpath("//*[@id=\"close\"]")
        );
        closeStupidWindowSOButton.click();
        Thread.sleep(1000);
        driver.switchTo().parentFrame();

        WebElement loginSOButton = getElementBySelector(
                driver,
                By.xpath("//header/div/nav/ol/li[3]/a")
        );
        loginSOButton.click();
        WebElement viaGoogleSOButton = getElementBySelector(
                driver,
                By.xpath("//div[2]/button")
        );
        viaGoogleSOButton.click();

        Thread.sleep(1500);
    }
}
