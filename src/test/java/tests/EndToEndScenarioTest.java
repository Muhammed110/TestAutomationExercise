package tests;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class EndToEndScenarioTest extends TestBase{
    @Test(description = "End-to-end: Add to cart, register, checkout, pay, and verify order")
    public void completeOrderFlowTest() {
        getDriver().get("https://www.automationexercise.com/");

        // Add products
        getHomePage().openProductsPage();
        List<WebElement> products = getProductListPage().addRandomProductsToCart(3);

        // Go to cart and proceed
        getHomePage().openCartPage();
        Assert.assertFalse(getCartPage().isCartEmpty(), "❌ Cart is empty!");
        getCartPage().clickProceedToCheckOut();
        getCartPage().clickRegisterLoginButton();

        // Register new user
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        registerNewUserWithDetails(email, name);

        // Go to cart and proceed again
        getHomePage().openCartPage();
        getCartPage().clickProceedToCheckOut();

        // Click Place Order
        getCheckoutPage().clickPlaceOrder();

        // Fill payment info
        String cardName = faker.name().fullName();
        String cardNum = faker.finance().creditCard();
        String cvc = faker.number().digits(3);
        String month = String.format("%02d", faker.number().numberBetween(1, 12));
        String year = String.valueOf(faker.number().numberBetween(2025, 2030));

        getPaymentPage().fillPaymentForm(cardName, cardNum, cvc, month, year);
        getPaymentPage().clickPayAndConfirm();

        // Verify order confirmation
        Assert.assertTrue(getPaymentPage().isOrderConfirmedDisplayed(), "❌ Order not confirmed!");
        getPaymentPage().clickContinueButton();
        Assert.assertEquals(getDriver().getCurrentUrl(), "https://www.automationexercise.com/");
    }
}
