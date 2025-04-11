package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.util.ArrayList;


import java.time.Duration;
import java.util.List;
import java.util.Random;

public class ProductListPage extends PageBase{
    @FindBy(xpath = "//h2[@class = 'title text-center']")
    public WebElement allProductsLabel;

    @FindBy(xpath = "//div[@class='product-image-wrapper']")
    List<WebElement> allProducts;

    @FindBy (css = "div.modal-content")
    public WebElement addedToCartMessage;
    @FindBy (css ="button.close-modal")
    WebElement btnContinueShopping;

    @FindBy(id = "search_product")
    WebElement searchProduct;

    @FindBy (id = "submit_search")
    WebElement btnSubmitSearch;


    public WebElement addProductToCartByIndex(int index) {
        WebElement product = allProducts.get(index);

        // Extract name early if needed
        String name = product.findElement(By.cssSelector(".productinfo p")).getText();

        new Actions(driver).moveToElement(product).perform();
        WebElement overlayButton = product.findElement(By.cssSelector(".product-overlay a.add-to-cart"));

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(overlayButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", overlayButton);

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(addedToCartMessage));

        Assert.assertTrue(addedToCartMessage.isDisplayed());
        click(btnContinueShopping);

        return driver.findElements(By.cssSelector("div.product-image-wrapper")).get(index);
    }

    public List<WebElement> addRandomProductsToCart(int maxCount) {
        List<WebElement> selected = new ArrayList<>();
        Random random = new Random();
        List<Integer> usedIndexes = new ArrayList<>();

        while (selected.size() < maxCount) {
            int index = random.nextInt(allProducts.size());

            if (usedIndexes.contains(index)) continue;
            usedIndexes.add(index);

            WebElement freshProduct = addProductToCartByIndex(index);
            selected.add(freshProduct);
        }
        return selected;
    }


    public void setSearchProduct(String product)
    {
        sendKeys(searchProduct,product);
    }
    public void clickSubmitSearchButton()
    {
        click(btnSubmitSearch);
    }
    public String getRandomProductName() {
        Random random = new Random();
        int index = random.nextInt(allProducts.size());
        WebElement productCard = allProducts.get(index);

        WebElement productName = productCard.findElement(By.cssSelector(".productinfo p"));

        return productName.getText();
    }

    public ProductListPage(WebDriver driver) {
        super(driver);
    }
}
