package com.example.bruteforceauth;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[2]/div/div")
    private WebElement invalidCredentialsMessage;
    @FindBy(xpath = "//*[@id=\"username\"]")
    private WebElement loginText;
    @FindBy(xpath = "//*[@id=\"password\"]")
    private WebElement passwordText;
    @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[2]/form/button")
    private WebElement loginButton;
    @FindBy(xpath =  "//*[@id=\"root\"]/div/div[3]/div[1]/h2" )
    private WebElement secretHeading;

    private Duration timeout = Duration.ofSeconds(3);
    private WebDriverWait wait;
    public static final String URL = "http://localhost:3000";
    private WebDriver driver;
    public LoginPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
        wait = new WebDriverWait(driver,timeout);
    }
    public LoginPage openPage(){
        driver.get(URL);
        return this;
    }
    public SecretPage toSecret(String login, String password){
        openPage();
        testToLog(login,password);
        return new SecretPage(driver);
    }
    public LoginPage testToLog(String login, String password){
        WebElement loginInput = wait.until(
                ExpectedConditions.elementToBeClickable(this.loginText));
        loginInput.click();
        loginInput.sendKeys(login);
        WebElement passwordInput = wait.until(
                ExpectedConditions.elementToBeClickable(this.passwordText));
        passwordInput.click();
        passwordInput.sendKeys(password);

        WebElement verifyButton = wait.until(
                ExpectedConditions.elementToBeClickable(this.loginButton));
        verifyButton.click();

        return this;
    }
    public boolean isLogged(){
        try {
            return wait.until(ExpectedConditions.visibilityOf(secretHeading)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public String getMessageOfInvalidCredentials(){
        return this.invalidCredentialsMessage.getText();
    }
}
