package com.example.bruteforceauth;

import com.example.bruteforceauth.repository.ImdbTop250Repository;
import org.apache.juli.logging.Log;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.TreeMap;
@SpringBootTest
public class UiTests {
	String password = "FEr@D]mcSRUc;@~Y";
	String login = "user1";
	WebDriver driver;

	@Autowired
	private ImdbTop250Repository imdbRepository;

	@BeforeEach
	void setUp(){
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}
	@AfterEach
	void shutDown(){
		if (driver != null){
			driver.quit();
		}
	}

	@Test
	void loginPageUsabilityIncorrectPassword() {
		String password = "wrongPassword";
		String login = "user1";
		LoginPage loginPage = new LoginPage(driver)
				.openPage()
				.testToLog(login,password);
		Assertions.assertFalse(loginPage.isLogged());
		Assertions.assertTrue(loginPage.getMessageOfInvalidCredentials().contains("Invalid password"));
	}
	@Test
	void loginPageUsabilityIncorrectLogin() {
		String password = "rightPassword";
		String login = "notExisting";
		LoginPage loginPage = new LoginPage(driver)
				.openPage()
				.testToLog(login,password);
		Assertions.assertFalse(loginPage.isLogged());
		Assertions.assertTrue(loginPage.getMessageOfInvalidCredentials().contains("User not found"));
	}
	@Test
	void loginPageUsability() {

		Assertions.assertTrue(new LoginPage(driver)
				.openPage()
				.testToLog(login,password)
				.isLogged());
	}
	@Test
	void parseFilms(){
		int numOfParsedFilms = new LoginPage(driver)
				.toSecret(login,password)
				.parse()
				.resultOfParse();
		if (imdbRepository.count()>0){
			Assertions.assertEquals(0, numOfParsedFilms);
		}else
			//25 will be only in case if db were empty before the execution
			Assertions.assertEquals(25, numOfParsedFilms);
	}

	@Test
	void downloadExcel(){
		String result = new LoginPage(driver)
				.toSecret(login,password)
				.downloadExcelFile()
				.resultOfDownload();
		Assertions.assertTrue(result.contains("успешно"));
	}
	@Test
	void downloadEncrypted(){
		String result = new LoginPage(driver)
				.toSecret(login,password)
				.downloadEncryptedFile("1234")
				.resultOfDownload();
		Assertions.assertTrue(result.contains("успешно"));
	}
	@Test
	void downloadEncryptedNoPassword(){
		SecretPage secretPage = new LoginPage(driver)
				.openPage()
				.toSecret(login, password);
		secretPage.downloadEncryptedFile();
		try {
			String promptText = ExpectedConditions.alertIsPresent().apply(driver).getText();

			Assertions.assertTrue(promptText.contains("пароль для шифрования"));

			driver.switchTo().alert().dismiss();

		} catch (NoAlertPresentException e) {
			Assertions.fail("no fail message");
		}
	}

}
