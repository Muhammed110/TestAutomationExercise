package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

public class RegistrationPage extends PageBase {
    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "id_gender1")
    WebElement rdBtnMale;
    @FindBy(id = "id_gender2")
    WebElement rdBtnFemale;
    @FindBy(id = "password")
    WebElement passwordRegister;
    @FindBy(id = "days")
    WebElement dayDOB;
    @FindBy(id = "months")
    WebElement monthDOB;
    @FindBy(id = "years")
    WebElement yearDOB;
    @FindBy(id = "newsletter")
    WebElement newsletterCheckBox;
    @FindBy(id = "optin")
    WebElement receiveOffersCheckBox;
    @FindBy(id = "first_name")
    WebElement firstName;
    @FindBy(id = "last_name")
    WebElement lastName;
    @FindBy(id = "company")
    WebElement company;
    @FindBy(id = "address1")
    WebElement address1;
    @FindBy(id = "address2")
    WebElement address2;
    @FindBy(id = "country")
    WebElement country;
    @FindBy(id = "state")
    WebElement state;
    @FindBy(id = "city")
    WebElement city;
    @FindBy(id = "zipcode")
    WebElement zipCode;
    @FindBy(id = "mobile_number")
    WebElement phoneNumber;
    @FindBy(xpath = "//button[@data-qa = 'create-account']")
    WebElement btnCreateAccount;
    @FindBy (xpath = "//h2[@data-qa = 'account-created']")
    public WebElement accountCreatedLabel;
    @FindBy (xpath = "//a[@data-qa = 'continue-button']")
    WebElement btnContinue;

    public void chooseGender(int gender)
    {
        if (gender == 1)
            click(rdBtnMale);
        else if (gender == 2)
            click(rdBtnFemale);
    }
    public void setPasswordRegister(String password)
    {
        sendKeys(passwordRegister,password);
    }
    public void setDateOfBirth(Date dateOfBirth)
    {
        // Convert Date to LocalDate
        LocalDate localDob = dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        int day = localDob.getDayOfMonth(); // Get correct day (1-31)
        int year = localDob.getYear(); // Get correct year (e.g., 1998)

        // Get month name (e.g., "January", "February")
        String monthName = localDob.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        // Select day
        Select select = new Select(dayDOB);
        select.selectByValue(String.valueOf(day));

        // Select month by visible text (e.g., "January", "February")
        select = new Select(monthDOB);
        select.selectByVisibleText(monthName);

        // Select year
        select = new Select(yearDOB);
        select.selectByVisibleText(String.valueOf(year));
    }
    public void checkNewsletter() {
        click(newsletterCheckBox);
    }
    public void checkOffers()
    {
        click(receiveOffersCheckBox);
    }
    public void setFirstName(String first_Name)
    {
        sendKeys(firstName,first_Name);
    }
    public void setLastName (String last_Name)
    {
        sendKeys(lastName,last_Name);
    }
    public void setCompany(String companyName)
    {
        sendKeys(company,companyName);
    }
    public void setAddress1(String firstAddress)
    {
        sendKeys(address1,firstAddress);
    }
    public void setAddress2(String secondAddress)
    {
        sendKeys(address2,secondAddress);
    }
    public void setCountry()
    {

    }

    public void setState(String stateName)
    {
        sendKeys(state,stateName);
    }
    public void setCity(String cityName)
    {
        sendKeys(city,cityName);
    }
    public void setZipCode(String zipCodeName)
    {
        sendKeys(zipCode,zipCodeName);
    }
    public void setPhoneNumber(String mobilePhone)
    {
        sendKeys(phoneNumber,mobilePhone);
    }
    public void clickCreateAccount()
    {
        click(btnCreateAccount);
    }

    public void clickContinueAfterRegistration()
    {
        click(btnContinue);
    }

}
