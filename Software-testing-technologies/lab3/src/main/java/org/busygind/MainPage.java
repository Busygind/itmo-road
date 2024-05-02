package org.busygind;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.busygind.Configuration.getElementBySelector;

public class MainPage extends Page {
    public MainPage(WebDriver driver) {
        super(driver);
    }

    public void goToAboutPage() {
        WebElement burger = getElementBySelector(
                driver,
                By.xpath("//div[2]/header/div[2]/div[1]/div[1]")
        );
        burger.click();
        WebElement aboutButton = getElementBySelector(
                driver,
                By.xpath("//div[2]/header/div[1]/div/div[2]/div/c-wiz/div/div[1]/a")
        );
        aboutButton.click();
    }

    public String getTranslation(String source) throws InterruptedException {
        WebElement textArea = getElementBySelector(
                driver,
                By.xpath("//body[@id='yDmH0d']/c-wiz/div/div[2]/c-wiz/div[2]/c-wiz/div/div[2]/div[2]/c-wiz/span/span/div/textarea")
        );
        textArea.sendKeys(source);
        Thread.sleep(500);
        WebElement answer = getElementBySelector(
                driver,
                By.xpath("//div[6]/div/div/span/span/span")
        );
        return answer.getText();
    }

    public void clearSource() {
        WebElement cross = getElementBySelector(
                driver,
                By.xpath("//c-wiz/div/div/div/span/button/div[3]")
        );
        cross.click();
    }

    public void changeTargetLanguageOnUkrainian() {
        WebElement langButton = getElementBySelector(
                driver,
                By.xpath("//div[5]/div/div[2]/div/div/span/button[3]/span[3]")
        );
        langButton.click();
    }

    public void openHistory() {
        WebElement historyButton = getElementBySelector(
                driver,
                By.xpath("//c-wiz/div/div[2]/c-wiz/nav/a[1]/div[1]")
        );
        historyButton.click();
    }

    public void openSavedTranslations() {
        WebElement savedButton = getElementBySelector(
                driver,
                By.xpath("//a[2]/div")
        );
        savedButton.click();
    }

    public void saveTranslation() {
        WebElement saveTranslationButton = getElementBySelector(
                driver,
                By.xpath("//c-wiz/div/div[2]/c-wiz/div[2]/c-wiz/div[1]/div[2]/div[2]/c-wiz[2]/div/div[6]/div/div[3]/div/button")
        );
        saveTranslationButton.click();
    }

    public String getSavedTranslationSource() {
        WebElement savedTranslation = getElementBySelector(
                driver,
                By.xpath("//div[4]/span")
        );
        return savedTranslation.getText();
    }

    public void closeLoginAdviceWhenSaveWindow() {
        WebElement notNowButton = getElementBySelector(
                driver,
                By.xpath("//div[8]/div[2]/div/div[2]/button[1]/span")
        );
        notNowButton.click();
    }

    public void closeLoginAdviceWhenHistoryWindow() {
        WebElement notNowButton = getElementBySelector(
                driver,
                By.xpath("//div[7]/div[2]/div/div[2]/button[1]/span")
        );
        notNowButton.click();
    }
}
