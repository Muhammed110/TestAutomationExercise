package pages;

import components.CartTableComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutPage extends PageBase {

    @FindBy(id = "cart_info")
    private WebElement checkoutTable;

    @FindBy(xpath = "(//p[@class='cart_total_price'])[last()]")
    private WebElement grandTotalElement;
    @FindBy(css = "a[href='/payment']")
    WebElement placeOrderButton;

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public CartTableComponent getCheckoutTableComponent() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(checkoutTable));
        return new CartTableComponent(checkoutTable);
    }

    public String getGrandTotalPrice() {
        return grandTotalElement.getText();
    }
    public void clickPlaceOrder() {
        click(placeOrderButton);
    }
}
