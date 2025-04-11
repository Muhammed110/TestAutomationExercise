package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ExcelUtil;

import java.util.Map;

public class LoginWithWrongCredentials extends TestBase{

    @Test (description = "Verify that user can't login with wrong credentials and error message appears")
    public void loginWithWrongCredentialsTestCase()
    {
        getDriver().get("https://www.automationexercise.com/");
        getHomePage().openLoginPage();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        loginWith(email,password);
        Assert.assertTrue(getLoginPage().incorrectCredentialsMessage.isDisplayed());
        Assert.assertEquals(getLoginPage().incorrectCredentialsMessage.getText(), "Your email or password is incorrect!");
    }

    @Test (description = "Verify that user can Logout successfully")
    public void logoutTestCase()
    {
        getDriver().get("https://www.automationexercise.com/");
        getHomePage().openLoginPage();

        // ✅ Read a random row from Excel file "data.xlsx"
        Map<String, String> credentials = ExcelUtil.getRandomRow("Sheet1", 2);

        if (credentials != null) {
            String email = credentials.get("Email");
            String password = credentials.get("Password");
            loginWith(email,password);
            getHomePage().clickLogoutButton();
        } else {
            System.out.println("❌ No data found in the Excel sheet!");
        }
        Assert.assertEquals(getDriver().getCurrentUrl(),"https://www.automationexercise.com/login");
    }
}
