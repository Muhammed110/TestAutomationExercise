package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class SearchTest extends TestBase {
    @Test (description = "Verify that users can search for product successfully")
    public void searchTestCase()
    {
        getDriver().get("https://www.automationexercise.com/");
        getHomePage().openProductsPage();
        String productName = getProductListPage().getRandomProductName();
        getProductListPage().setSearchProduct(productName);
        getProductListPage().clickSubmitSearchButton();
        List<WebElement> searchResults = getDriver().findElements(By.cssSelector(".product-image-wrapper"));

        Assert.assertFalse(searchResults.isEmpty(), "❌ No search results found!");

        for (WebElement result : searchResults) {
            String name = result.findElement(By.cssSelector(".productinfo p")).getText();
            Assert.assertTrue(name.toLowerCase().contains(productName.toLowerCase()),
                    "❌ Result doesn't match searched product: " + name);
        }

    }
}
