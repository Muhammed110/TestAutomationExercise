package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CheckoutTest extends TestBase {

    @Test(description = "Verify that user can complete the order successfully without signing in before!")
    public void completeCheckoutWithoutLoginTestcase() {
        getDriver().get("https://www.automationexercise.com/");


        // 🛒 Add products
        getHomePage().openProductsPage();
        List<WebElement> products = getProductListPage().addRandomProductsToCart(3);

        // 📦 Extract expected values
        List<String> expectedNames = new ArrayList<>();
        List<String> expectedUnitPrices = new ArrayList<>();
        List<String> expectedQuantities = new ArrayList<>();
        List<String> expectedTotals = new ArrayList<>();

        for (WebElement product : products) {
            String name = product.findElement(By.cssSelector(".productinfo p")).getText();
            String priceText = product.findElement(By.cssSelector(".productinfo h2")).getText(); // e.g., Rs. 500
            String numericPrice = priceText.replaceAll("[^\\d]", "");
            int price = Integer.parseInt(numericPrice);
            int quantity = 1;

            expectedNames.add(name);
            expectedUnitPrices.add(priceText);
            expectedQuantities.add(String.valueOf(quantity));
            expectedTotals.add("Rs. " + (price * quantity));
        }

        // 🧭 Navigate to checkout
        getHomePage().openCartPage();
        Assert.assertFalse(getCartPage().isCartEmpty(), "❌ Cart is empty!");
        getCartPage().clickProceedToCheckOut();
        getCartPage().clickRegisterLoginButton();

        // Register new user
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        registerNewUserWithDetails(email,name);


        // 🛒 Go to checkout again after login redirect
        getHomePage().openCartPage();
        getCartPage().clickProceedToCheckOut();

        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("cart_info")));

        var table = getCheckoutPage().getCheckoutTableComponent();

        int expectedTotal = expectedUnitPrices.stream()
                .map(price -> price.replaceAll("[^0-9]", "")) // Remove "Rs. " prefix
                .mapToInt(Integer::parseInt)
                .sum();
        String expectedGrandTotal = "Rs. " + expectedTotal;


        String actualGrandTotal = getCheckoutPage().getGrandTotalPrice();

        Assert.assertEquals(actualGrandTotal, expectedGrandTotal, "❌ Grand total mismatch!");

        Assert.assertEquals(table.getProductNames(), expectedNames, "❌ Product names mismatch!");
        Assert.assertEquals(table.getProductPrices(), expectedUnitPrices, "❌ Unit prices mismatch!");
        Assert.assertEquals(table.getQuantities(), expectedQuantities, "❌ Quantities mismatch!");
        Assert.assertEquals(table.getTotalPrices(), expectedTotals, "❌ Total prices mismatch!");
        getCheckoutPage().clickPlaceOrder();

        String cardHolder = faker.name().fullName();
        String cardNumber = faker.finance().creditCard();
        String cvc = faker.number().digits(3);
        String month = String.format("%02d", faker.number().numberBetween(1, 12));
        String year = String.valueOf(faker.number().numberBetween(2025, 2030));

        getPaymentPage().fillPaymentForm(cardHolder,cardNumber,cvc,month,year);
        getPaymentPage().clickPayAndConfirm();

        Assert.assertTrue(getPaymentPage().isOrderConfirmedDisplayed(), "❌ Order confirmation message not displayed!");
        getPaymentPage().clickContinueButton();

        Assert.assertEquals(getDriver().getCurrentUrl(),"https://www.automationexercise.com/");

    }

    @Test(description = "Verify that user can complete the order successfully without signing in before!")
    public void completeCheckoutLoggedInBeforeTestcase() {
        getDriver().get("https://www.automationexercise.com/");
        getHomePage().openLoginPage();
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();

        registerNewUserWithDetails(email,name);

        getHomePage().openProductsPage();
        getProductListPage().addRandomProductsToCart(5);
        List<WebElement> products = getProductListPage().addRandomProductsToCart(3);

        // 📦 Extract expected values
        List<String> expectedNames = new ArrayList<>();
        List<String> expectedUnitPrices = new ArrayList<>();
        List<String> expectedQuantities = new ArrayList<>();
        List<String> expectedTotals = new ArrayList<>();

        for (WebElement product : products) {
            String nameText = product.findElement(By.cssSelector(".productinfo p")).getText();
            String priceText = product.findElement(By.cssSelector(".productinfo h2")).getText();
            String numericPrice = priceText.replaceAll("[^\\d]", "");
            int price = Integer.parseInt(numericPrice);
            int quantity = 1;

            expectedNames.add(nameText);
            expectedUnitPrices.add(priceText);
            expectedQuantities.add(String.valueOf(quantity));
            expectedTotals.add("Rs. " + (price * quantity));
        }

        // 🧭 Navigate to checkout
        getHomePage().openCartPage();
        Assert.assertFalse(getCartPage().isCartEmpty(), "❌ Cart is empty!");
        getCartPage().clickProceedToCheckOut();

        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("cart_info")));

        var table = getCheckoutPage().getCheckoutTableComponent();

        int expectedTotal = expectedUnitPrices.stream()
                .map(price -> price.replaceAll("[^0-9]", ""))
                .mapToInt(Integer::parseInt)
                .sum();
        String expectedGrandTotal = "Rs. " + expectedTotal;

        String actualGrandTotal = getCheckoutPage().getGrandTotalPrice();

        Assert.assertEquals(actualGrandTotal, expectedGrandTotal, "❌ Grand total mismatch!");
        Assert.assertEquals(table.getProductNames(), expectedNames, "❌ Product names mismatch!");
        Assert.assertEquals(table.getProductPrices(), expectedUnitPrices, "❌ Unit prices mismatch!");
        Assert.assertEquals(table.getQuantities(), expectedQuantities, "❌ Quantities mismatch!");
        Assert.assertEquals(table.getTotalPrices(), expectedTotals, "❌ Total prices mismatch!");

        getCheckoutPage().clickPlaceOrder();

        // 💳 Payment
        String cardHolder = faker.name().fullName();
        String cardNumber = faker.finance().creditCard();
        String cvc = faker.number().digits(3);
        String month = String.format("%02d", faker.number().numberBetween(1, 12));
        String year = String.valueOf(faker.number().numberBetween(2025, 2030));

        getPaymentPage().fillPaymentForm(cardHolder, cardNumber, cvc, month, year);
        getPaymentPage().clickPayAndConfirm();

        Assert.assertTrue(getPaymentPage().isOrderConfirmedDisplayed(), "❌ Order confirmation message not displayed!");
        getPaymentPage().clickContinueButton();

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://www.automationexercise.com/");
    }

}
