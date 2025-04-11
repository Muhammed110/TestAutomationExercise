package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.ExcelUtil;

public class RegistrationTest extends TestBase {



    @Test(description = "Verify that user can register successfully")
    public void registrationTestCase() {
        getDriver().get("https://www.automationexercise.com/");
        getHomePage().openLoginPage();

        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        getLoginPage().setNameRegister(faker.name().fullName());
        getLoginPage().setEmailRegister(email);
        getLoginPage().click_signUp_button();

        getRegistrationPage().chooseGender(random.nextInt(2) + 1);
        getRegistrationPage().setPasswordRegister(password);
        getRegistrationPage().setDateOfBirth(faker.date().birthday());

        getRegistrationPage().setFirstName(faker.name().firstName());
        getRegistrationPage().setLastName(faker.name().lastName());
        getRegistrationPage().setCompany(faker.company().name());
        getRegistrationPage().setAddress1(faker.address().fullAddress());
        getRegistrationPage().setAddress2(faker.address().fullAddress());
        getRegistrationPage().setState(faker.address().state());
        getRegistrationPage().setCity(faker.address().cityName());
        getRegistrationPage().setZipCode(faker.address().zipCode());
        getRegistrationPage().setPhoneNumber(faker.phoneNumber().phoneNumber());
        getRegistrationPage().clickCreateAccount();

        Assert.assertEquals(getRegistrationPage().accountCreatedLabel.getText(),"ACCOUNT CREATED!");
        ExcelUtil.saveCredentials(email,password);
        getRegistrationPage().clickContinueAfterRegistration();

        Assert.assertEquals(getDriver().getCurrentUrl(),"https://www.automationexercise.com/");
        Assert.assertTrue(getHomePage().btnLogout.isDisplayed());

    }
}