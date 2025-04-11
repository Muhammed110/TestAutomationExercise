package pages;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends PageBase {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//input[@data-qa ='login-email']")
    WebElement emailLogin;

    @FindBy(xpath = "//input[@data-qa='login-password']")
    WebElement passLogin;
    @FindBy(xpath = "//button[@data-qa='login-button']")
    WebElement btnLogin;

    @FindBy(xpath = "//input[@data-qa='signup-name']")
    WebElement nameRegister;

    @FindBy(xpath = "//input[@data-qa='signup-email']")
    WebElement emailRegister;
    @FindBy(xpath = "//button[@data-qa='signup-button']")
    WebElement btnSignUp;
    @FindBy(xpath = "//p[text()='Your email or password is incorrect!']")
    public WebElement incorrectCredentialsMessage;


    public void setLoginMail(String email) {
        sendKeys(emailLogin, email);
    }

    public void setPassLogin(String pass) {
        sendKeys(passLogin, pass);
    }

    public void setNameRegister(String name) {
        sendKeys(nameRegister, name);
    }

    public void setEmailRegister(String email) {
        sendKeys(emailRegister, email);
    }

    public void click_login_button() {
        click(btnLogin);
    }

    public void click_signUp_button() {
        click(btnSignUp);
    }

}

