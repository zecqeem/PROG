package com.example.bruteforceauth;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SecretPage {
    private Duration timeout = Duration.ofSeconds(3);
    private WebDriverWait wait;
    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"root\"]/div/div[3]/div[4]/div/div/div[2]")
    private WebElement verifyEvent;
    @FindBy(css = "input[type='password'][placeholder='Enter decryption key...']")
    private WebElement passwordForEncryption;
    @FindBy(xpath = "//*[@id=\"root\"]/div/div[3]/div[3]/button[1]")
    private WebElement parseButton;
    @FindBy(xpath = "//*[@id=\"root\"]/div/div[3]/div[3]/button[2]")
    private WebElement downloadButton;
    @FindBy(xpath = "//*[@id=\"root\"]/div/div[3]/div[3]/button[3]")
    private WebElement downloadEncryptedButton;
    public SecretPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
        wait = new WebDriverWait(driver,timeout);
    }
    public SecretPage parse(){
        wait.until(ExpectedConditions.elementToBeClickable(this.parseButton)).click();
        return this;
    }
    public int resultOfParse(){
        wait.until(ExpectedConditions.visibilityOf(this.verifyEvent));
        return Integer.parseInt(verifyEvent.getText().replaceAll("[^0-9]", ""));
    }
    public SecretPage downloadExcelFile(){
        wait.until(ExpectedConditions.elementToBeClickable(this.downloadButton))
                .click();
        return this;
    }
    public SecretPage downloadEncryptedFile(String password){
        WebElement encButton = wait.until(ExpectedConditions
                .elementToBeClickable(this.downloadEncryptedButton));
        this.passwordForEncryption.click();
        this.passwordForEncryption.sendKeys(password);
        encButton.click();
        return this;
    }
    public void downloadEncryptedFile() {
        WebElement encButton = wait.until(ExpectedConditions
                .elementToBeClickable(this.downloadEncryptedButton));
        encButton.click();
    }

    public String resultOfDownload(){
        wait.until(ExpectedConditions.visibilityOf(this.verifyEvent));
        return verifyEvent.getText();
    }

}
