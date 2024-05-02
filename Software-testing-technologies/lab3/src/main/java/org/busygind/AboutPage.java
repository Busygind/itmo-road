package org.busygind;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.awt.*;

import static org.busygind.Configuration.getElementBySelector;

public class AboutPage extends Page {

    public AboutPage(WebDriver driver) {
        super(driver);
    }

    public String getHeader() {
        return getElementBySelector(driver, By.xpath("//div/div/section[1]/div[2]/h1")).getText();
    }

    public void changeLanguageOnPolish() throws AWTException {
        WebElement selector = getElementBySelector(
                driver,
                By.xpath("//select")
        );
        SystemAccess.scrollToBottom();
        Select select = new Select(selector);
        select.selectByVisibleText("Polski");
    }
}
