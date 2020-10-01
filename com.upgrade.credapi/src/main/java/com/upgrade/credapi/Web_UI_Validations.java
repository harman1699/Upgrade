package com.upgrade.credapi;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class Web_UI_Validations {

	static WebDriver driver;


	static String defaultLoanAmount = "2,000";
	static String deafultMonthlyPayment = "$71.78";
	static String deafultTermAmount = "36";
	static String deafultInterestRate = "17.48%";
	static String deafultAPR = "21.99%";

	@BeforeSuite
	public static void beforeSuite() {

		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\Tom\\Music\\REST-API Automation\\com.upgrade.credapi\\driverfolder\\chromedriver.exe ");

		driver = new ChromeDriver();
		driver.get("https://www.credify.tech/phone/nonDMFunnel");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@Test(enabled = true)
	public static void test1() {
		driver.findElement(By.name("desiredAmount")).sendKeys("2000");
		driver.findElement(
				By.xpath("//*[@id=\"root\"]/div/main/div/div/div/div/div[2]/div[2]/form/div/div/div[2]/div/select"))
				.sendKeys("Business");
		driver.findElement(By.className("sc-jJMGHv")).click();

	}

	@Test(dependsOnMethods = { "test1" })
	public static void test2() throws InterruptedException {
		Actions a = new Actions(driver);
		driver.findElement(By.name("borrowerFirstName")).sendKeys("Rocky");
		driver.findElement(By.name("borrowerLastName")).sendKeys("singh");

		WebElement street = driver.findElement(By.name("borrowerStreet"));
	
		street.sendKeys("1600 Amphitheatre Parkway");
		a.moveToElement(street).sendKeys(Keys.ENTER).perform();

		driver.findElement(By.name("borrowerCity")).sendKeys("Mountain View");
		driver.findElement(By.name("borrowerState")).sendKeys("California");
		driver.findElement(By.name("borrowerZipCode")).sendKeys("94043");
		driver.findElement(By.name("borrowerDateOfBirth")).sendKeys("01/01/2000");
		driver.findElement(By.className("sc-jJMGHv")).click();
		driver.findElement(By.name("borrowerIncome")).sendKeys("$120,000");
		WebElement clickButton = driver.findElement(By.name("borrowerAdditionalIncome"));
		clickButton.sendKeys("$5,000");
		clickButton.sendKeys(Keys.TAB);
		driver.findElement(By.className("sc-jJMGHv")).click();

		driver.findElement(By.name("username")).sendKeys("rocky99@upgrade-challenge.com");
		driver.findElement(By.name("password")).sendKeys("Rocky123");
		driver.findElement(By.className("sc-eHEFXm")).click();
		driver.findElement(By.className("sc-jJMGHv")).click();

		WebElement loanAmount = driver.findElement(By.cssSelector(".sc-bkbjAj"));
		String s0 = loanAmount.getText();
		System.out.println("Deafult loanAmount is: $" + s0);

		WebElement monthlyPayment = driver.findElement(By.cssSelector(".sc-cbeQSR>span"));
		String s1 = monthlyPayment.getText();
		System.out.println("Deafult monthlyPayment is: " + s1);

		WebElement term = driver.findElement(By.cssSelector(".section--xs:nth-of-type(1)"));
		String[] s2 = term.getText().split("[a-zA-Z\\s+]+");
		System.out.println("Deafult term Amount is: $" + s2[0]);

		WebElement interestRate = driver.findElement(By.cssSelector(".section--xs:nth-of-type(2)"));
		String[] s3 = interestRate.getText().split("[a-zA-Z\\s+]+");
		System.out.println("Deafult interestRate is:" + s3[0]);

		WebElement APR = driver.findElement(By.cssSelector(".section--xs:nth-of-type(3)>div"));

		String[] s4 = APR.getText().split("[a-zA-Z\\s+()]+");
		System.out.println("Deafult APR is: " + s4[0]);

		Thread.sleep(5000);

		WebElement menuClick = driver.findElement(By.xpath("(.//label[@class='header-nav__toggle'])[1][position()=1]"));

		menuClick.click();

		WebElement signOut = driver.findElement(By.linkText("Sign Out"));
		signOut.click();
		driver.get(" https://www.credify.tech/portal/login");
		WebElement email = driver.findElement(By.name("username"));
		email.sendKeys("rocky99@upgrade-challenge.com");

		WebElement pass = driver.findElement(By.name("password"));
		pass.sendKeys("Rocky123");
		pass.sendKeys(Keys.TAB);

		WebElement forgetPass = driver.findElement(By.className("sc-hKFyIo"));
		forgetPass.sendKeys(Keys.TAB);

		Thread.sleep(2000);
		WebElement signIn = driver.findElement(By.xpath("//button[@type='submit']"));

		signIn.click();
		Thread.sleep(3000);
		String pageUrl = driver.getCurrentUrl();
		System.out.println("Offer Page URL:" + pageUrl);
		Thread.sleep(5000);

		try {
			Assert.assertEquals(defaultLoanAmount, s0, "Loan Amount are same");

		} catch (AssertionError e) {

			System.out.println("Loan Amount is different");
		}
		try {

			Assert.assertEquals(deafultMonthlyPayment, s1, "Loan Amount are same");

		} catch (AssertionError e) {

			System.out.println("Monthly payment is different");
		}

		try {

			Assert.assertEquals(deafultTermAmount, s2[0], "Loan Amount are same");

		} catch (AssertionError e) {

			System.out.println("Term amount is different");
		}

		try {

			Assert.assertEquals(deafultInterestRate, s3[0], "Loan Amount are same");

		} catch (AssertionError e) {

			System.out.println("Interest rate is different");
		}
		try {

			Assert.assertEquals(deafultAPR, s4[0], "Loan Amount are same");
		} catch (AssertionError e) {

			System.out.println("APR is different");
		}

	}

	@AfterSuite
	public static void afterSuite() throws InterruptedException {
		Thread.sleep(5000);
		driver.close();
	}
}
