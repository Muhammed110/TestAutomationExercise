package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import pages.HomePage;
import utils.ExcelUtil;
import java.util.Map;
public class LoginTest extends TestBase{

    @Test (description = "Verify that user can Log in successfully with registered mail from the Excel sheet")
    public void loginTestCase()
    {
        getDriver().get("https://www.automationexercise.com/");
        getHomePage().openLoginPage();

        // ✅ Read a random row from Excel file "data.xlsx"
        Map<String, String> credentials = ExcelUtil.getRandomRow("Sheet1", 2);

        if (credentials != null) {
            String email = credentials.get("Email");
            String password = credentials.get("Password");
            loginWith(email,password);
        } else {
            System.out.println("❌ No data found in the Excel sheet!");
        }
        Assert.assertTrue(getHomePage().loggedInLabel.isDisplayed());
        Assert.assertTrue(getHomePage().btnLogout.isDisplayed());
    }
}
