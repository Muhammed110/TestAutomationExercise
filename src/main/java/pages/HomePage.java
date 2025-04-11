package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.*;
import org.testng.annotations.Test;

public class HomePage extends PageBase {


    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy (xpath = "//a[@href='/products']")
    WebElement btnProducts;
    @FindBy (xpath = "//a[@href='/view_cart']")
    WebElement btnCart;
    @FindBy (xpath = "//a[@href='/login']")
    WebElement btnLogin;
    @FindBy (xpath = "//a[@href='/contact_us']")
    WebElement btnContactUs;
    @FindBy (xpath = "//a[@href='/logout']")
    public WebElement btnLogout;
    @FindBy (xpath = "//a[@href='/delete_account']")
    WebElement btnDeleteAccount;
    @FindBy (xpath = "//a[contains(text(), 'Logged in as')]")
    public WebElement loggedInLabel;

    public void openProductsPage()
    {
        click(btnProducts);
    }
    public void openLoginPage()
    {
        btnLogin.click();
    }
    public void openCartPage()
    {
        click(btnCart);
    }
    public void openContactUsPage()
    {
        click(btnContactUs);
    }
    public void clickLogoutButton()
    {click(btnLogout);}
    


}
