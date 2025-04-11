package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import components.CartTableComponent;



import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CartPage extends PageBase {

    @FindBy(css = "table#cart_info_table tbody tr")
    private List<WebElement> cartItems;
    @FindBy(id = "empty_cart")
    private WebElement emptyCartMessage;

    @FindBy (css = "a.btn.check_out")
    WebElement btnGoToCheckout;
    @FindBy(css = "#checkoutModal a[href='/login']")
    private WebElement registerLoginLink;

    @FindBy(id = "cart_info_table")
    private WebElement cartTable;

    public CartTableComponent getCartTableComponent() {
        return new CartTableComponent(cartTable);
    }

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    public List<String> getProductNamesInCart() {
        List<String> names = new ArrayList<>();
        for (WebElement row : cartItems) {
            String name = row.findElement(By.cssSelector(".cart_description h4 a")).getText();
            names.add(name);
        }
        return names;
    }
    public void clickProceedToCheckOut()
    {
     click(btnGoToCheckout);
    }
    public void clickRegisterLoginButton()
    {  new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.visibilityOf(registerLoginLink));
        click(registerLoginLink);}
    public boolean isCartEmpty() {
        return emptyCartMessage.isDisplayed();
    }
    public int getCartItemCount() {
        return cartItems.size();
    }
}
