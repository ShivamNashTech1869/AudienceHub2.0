package pages;

import org.jboss.aerogear.security.otp.Totp;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import resources.Base;
import resources.DataDriven;
import util.Common;
import util.Log;

import java.io.IOException;
import java.time.Duration;
import java.util.Set;

public class Auth {
    WebDriver driver;
    WebDriverWait wait;
    Base base;
    Common common;
    String twoFactorCode;


    public Auth(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
        wait=new WebDriverWait(driver, Duration.ofSeconds(60));
        base = new Base();
        common = new Common(driver);
    }

    @FindBy(css = "input[name='username']") WebElement emailInputBox;
    @FindBy(xpath = "//button[text()='Next']") WebElement nextButton;

    //Google Authenticator
    @FindBy(css = "input#identifierId") WebElement emailInputField;
    @FindBy(xpath = "//span[text()='Next']") WebElement nextSighInStep;

    @FindBy(css = "input[name='Passwd']")
    WebElement passwordInputField;


    @FindBy(xpath = "//button[span[text()='Try another way']]") WebElement tryAnotherWayButton;
    @FindBy(xpath = "//li[contains(div, 'Google Authenticator')]") WebElement googleAuthenticatorOption;
    @FindBy(id = "totpPin") WebElement otpPinInputBox;
    @FindBy(css = "img[alt='Google SignIn']")
    WebElement signInWithGoogleButton;


    public void signIn(String credentialData) throws IOException, InterruptedException {
        wait.until(ExpectedConditions.visibilityOf(emailInputBox));
        emailInputBox.sendKeys(DataDriven.getTestData("Accounts", credentialData).get(1));
        wait.until(ExpectedConditions.elementToBeClickable(nextButton));
        nextButton.click();

        handleGoogleSignIn();

        // Handle the new window
        String parentWindowHandle = driver.getWindowHandle();
        Set<String> allWindowHandles = driver.getWindowHandles();

        // Switch to the new window (Google sign-in page)
        for (String windowHandle : allWindowHandles) {
            if (!windowHandle.equals(parentWindowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

//      By using googleAuthenticatorOption
        wait.until(ExpectedConditions.visibilityOf(emailInputField));
        emailInputField.sendKeys(Keys.CONTROL, "a");
        emailInputField.sendKeys(DataDriven.getTestData("Accounts", credentialData).get(1));
        wait.until(ExpectedConditions.elementToBeClickable(nextSighInStep));
        nextSighInStep.click();
        wait.until(ExpectedConditions.visibilityOf(passwordInputField));
        passwordInputField.sendKeys(DataDriven.getTestData("Accounts", credentialData).get(2));
        wait.until(ExpectedConditions.elementToBeClickable(nextSighInStep));
        nextSighInStep.click();

        try {
            Thread.sleep(20000);
            wait.until(ExpectedConditions.elementToBeClickable(tryAnotherWayButton));
            tryAnotherWayButton.click();
        } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
            Thread.sleep(20000);
            tryAnotherWayButton = driver.findElement(By.xpath("//button[span[text()='Try another way']]"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tryAnotherWayButton);
        }


        common.setImplicitWait(30);
        wait.until(ExpectedConditions.elementToBeClickable(googleAuthenticatorOption));
        googleAuthenticatorOption.click();
        wait.until(ExpectedConditions.visibilityOf(otpPinInputBox));

//      Fetch the 2FA code
        twoFactorCode = twoFAKey();
        otpPinInputBox.sendKeys(twoFactorCode);

        Thread.sleep(1000);
        wait.until(ExpectedConditions.elementToBeClickable(nextSighInStep));
        nextSighInStep.click();
        common.setImplicitWait(60);
    }

    public String twoFAKey() throws IOException {
        String otpKeyStr = base.loadProperties("2FA_Key");  // Load the 2FA secret key
        Totp totp = new Totp(otpKeyStr);
        String twoFactorCode = totp.now();
        Log.info("twoFactorCode : " + twoFactorCode);
        return twoFactorCode;
    }

    public void handleGoogleSignIn() {
        try {
            wait.until(ExpectedConditions.visibilityOf(signInWithGoogleButton));
            wait.until(ExpectedConditions.elementToBeClickable(signInWithGoogleButton));
            signInWithGoogleButton.click();
        } catch (Exception e) {
            System.out.println("Google sign-in button not available; assuming default Google sign-in.");
        }
    }

}
