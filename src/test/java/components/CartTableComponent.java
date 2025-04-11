package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CartTableComponent {
    private final WebElement table;

    public CartTableComponent(WebElement table) {
        this.table = table;
    }

    private List<WebElement> getProductRows() {
        return table.findElements(By.cssSelector("tbody tr[id^='product-']"));
    }

    public List<String> getProductNames() {
        List<String> names = new ArrayList<>();
        for (WebElement row : getProductRows()) {
            String name = row.findElement(By.cssSelector(".cart_description h4 a")).getText();
            names.add(name);
        }
        return names;
    }

    public List<String> getProductPrices() {
        List<String> prices = new ArrayList<>();
        for (WebElement row : getProductRows()) {
            String price = row.findElement(By.cssSelector(".cart_price p")).getText();
            prices.add(price);
        }
        return prices;
    }

    public List<String> getQuantities() {
        List<String> quantities = new ArrayList<>();
        for (WebElement row : getProductRows()) {
            String quantity = row.findElement(By.cssSelector(".cart_quantity button")).getText();
            quantities.add(quantity);
        }
        return quantities;
    }

    public List<String> getTotalPrices() {
        List<String> totals = new ArrayList<>();
        for (WebElement row : getProductRows()) {
            String total = row.findElement(By.cssSelector(".cart_total .cart_total_price")).getText();
            totals.add(total);
        }
        return totals;
    }
}
