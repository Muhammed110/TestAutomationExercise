package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class AddToCartTest extends TestBase{
    @Test(description = "Verify that user can add one product to the cart")
    public void addOneItemToCartTestCase()
    {
        int index = random.nextInt(34);
        getDriver().get("https://www.automationexercise.com/");
        getHomePage().openProductsPage();

        WebElement product = getProductListPage().addProductToCartByIndex(index);
        String expectedName = product.findElement(By.cssSelector(".productinfo p")).getText();

        getHomePage().openCartPage();
        List<String> cartItems = getCartPage().getProductNamesInCart();

        Assert.assertTrue(cartItems.contains(expectedName),
                "❌ Expected product not found in cart: " + expectedName);


    }

    @Test(description = "Verify that user can add multiple products to the cart")
    public void addMultipleProductsToCartTestCase() {
        getDriver().get("https://www.automationexercise.com/");
        getHomePage().openProductsPage();
        List<WebElement> addedProducts = getProductListPage().addRandomProductsToCart(3);
        List<String> expectedNames = addedProducts.stream()
                .map(e -> e.findElement(By.cssSelector(".productinfo p")).getText())
                .toList();
        getHomePage().openCartPage();
        Assert.assertFalse(getCartPage().isCartEmpty(), "❌ Cart is empty!");

        List<String> actualNames = getCartPage().getProductNamesInCart();

        for (String expected : expectedNames) {
            Assert.assertTrue(actualNames.contains(expected),
                    "❌ Product not found in cart: " + expected);
        }
    }
}
