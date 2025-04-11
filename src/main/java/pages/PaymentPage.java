package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PaymentPage extends PageBase {
    public PaymentPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(name = "name_on_card")
    WebElement nameOnCard;

    @FindBy(name = "card_number")
    WebElement cardNumber;

    @FindBy(name = "cvc")
    WebElement cvc;

    @FindBy(name = "expiry_month")
    WebElement expiryMonth;

    @FindBy(name = "expiry_year")
    WebElement expiryYear;

    @FindBy(id = "submit")
    WebElement payAndConfirmButton;
    @FindBy(xpath = "//p[contains(text(), 'Congratulations! Your order has been confirmed!')]")
    WebElement orderConfirmedMessage;
    @FindBy(css = "[data-qa='continue-button']")
    WebElement continueButton;

    public void clickContinueButton() {
        click(continueButton);
    }

    public void fillPaymentForm(String name, String card, String cvcCode, String month, String year) {
        sendKeys(nameOnCard, name);
        sendKeys(cardNumber, card);
        sendKeys(cvc, cvcCode);
        sendKeys(expiryMonth, month);
        sendKeys(expiryYear, year);
    }

    public void clickPayAndConfirm() {
        click(payAndConfirmButton);
    }

    public boolean isOrderConfirmedDisplayed() {
        return orderConfirmedMessage.isDisplayed();
    }
}
