package tests;

import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.*;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.ArrayList;

public class TestBase {
    protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    protected Faker faker;
    protected Random random;

    // Page Objects - Lazy Initialization
    private HomePage homePage;
    private RegistrationPage registrationPage;
    private LoginPage loginPage;
    private ProductDetailsPage productDetailsPage;
    private ProductListPage productListPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private PaymentPage paymentPage;


    @Parameters({"browser"})
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser) {
        setUpDriver(browser);

        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        try {
            getDriver().manage().window().maximize();
        } catch (Exception e) {
            System.out.println("⚠️ Failed to maximize window: " + e.getMessage());
        }

        // Initialize Faker, Random, and reset Page Objects
        faker = new Faker();
        random = new Random();
        resetPageObjects();
    }

    private void setUpDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver.set(new FirefoxDriver());
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                driver.set(new EdgeDriver());
                break;

            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setExperimentalOption("prefs", Map.of(
                        "profile.default_content_setting_values.autofill_address", 2,
                        "credentials_enable_service", false,
                        "profile.password_manager_enabled", false,
                        "autofill.profile_enabled", false
                ));
                chromeOptions.addArguments("--disable-save-password-bubble");



                // Load AdBlock extension if exists
                File extension = getResourceFile("adblock.crx");
                if (extension.exists()) {
                    chromeOptions.addExtensions(extension);
                } else {
                    System.out.println("⚠️ AdBlock not found. Running without extension.");
                }

                driver.set(new ChromeDriver(chromeOptions));
                waitForNewTabAndClose(); // close extension tab if opened
                break;
        }
    }

    private File getResourceFile(String fileName) {
        URL resource = getClass().getClassLoader().getResource(fileName);

        if (resource == null) {
            throw new RuntimeException("AdBlock extension file is missing! Please add it to src/main/resources.");
        }

        try {
            return new File(resource.toURI());
        } catch (Exception e) {
            throw new RuntimeException("Failed to load extension file: " + fileName, e);
        }
    }

    private void waitForNewTabAndClose() {
        WebDriver driver = getDriver();
        int maxWaitTime = 10;

        Set<String> initialWindows = driver.getWindowHandles();
        int initialWindowCount = initialWindows.size();

        for (int i = 0; i < maxWaitTime; i++) {
            try {
                Thread.sleep(1000); // Wait for 1 second
                Set<String> currentWindows = driver.getWindowHandles();

                if (currentWindows.size() > initialWindowCount) {
                    System.out.println("🔄 Detected new tab. Closing it...");
                    ArrayList<String> tabs = new ArrayList<>(currentWindows);
                    driver.switchTo().window(tabs.get(1));
                    driver.close();
                    driver.switchTo().window(tabs.get(0));
                    System.out.println("✅ Extension tab closed, returning to main tab.");
                    break;
                }
            } catch (InterruptedException ignored) {}
        }
    }

    public WebDriver getDriver() {
        return driver.get();
    }

    // ✅ Lazy Initialization of Page Objects (Best Practice)
    public HomePage getHomePage() {
        if (homePage == null) homePage = new HomePage(getDriver());
        return homePage;
    }

    public RegistrationPage getRegistrationPage() {
        if (registrationPage == null) registrationPage = new RegistrationPage(getDriver());
        return registrationPage;
    }

    public LoginPage getLoginPage() {
        if (loginPage == null) loginPage = new LoginPage(getDriver());
        return loginPage;
    }

    public ProductDetailsPage getProductDetailsPage() {
        if (productDetailsPage == null) productDetailsPage = new ProductDetailsPage(getDriver());
        return productDetailsPage;
    }
    public ProductListPage getProductListPage()
    {
        if (productListPage == null)
            productListPage = new ProductListPage(getDriver());
        return productListPage;
    }
    public CartPage getCartPage()
    {
        if( cartPage == null)
            cartPage = new CartPage(getDriver());
        return cartPage;

    }

    public CheckoutPage getCheckoutPage() {
        if (checkoutPage == null)
            checkoutPage = new CheckoutPage(getDriver());
        return checkoutPage;
    }
    public PaymentPage getPaymentPage() {
        if (paymentPage == null)
            paymentPage = new PaymentPage(getDriver());
        return paymentPage;
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            System.out.println("❌ Test failed: " + result.getName() + " — capturing screenshot...");
            String className = result.getTestClass().getRealClass().getSimpleName();
            String fileName = result.getName() + "_" + System.currentTimeMillis() + ".png";
            new PageBase(getDriver()).captureScreenshot(className, fileName);
        }
        try {
            if (getDriver() != null) {
                getDriver().quit();
                driver.remove();
            }
        } catch (Exception e) {
            System.out.println("⚠️ Warning: Error while closing WebDriver: " + e.getMessage());
        }
    }

    public void loginWith(String email, String password) {
        getHomePage().openLoginPage();
        getLoginPage().setLoginMail(email);
        getLoginPage().setPassLogin(password);
        getLoginPage().click_login_button();
    }
    public void registerNewUserWithDetails(String email, String password) {
        Faker faker = new Faker();
        Random random = new Random();

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
        getRegistrationPage().setPhoneNumber(faker.phoneNumber().cellPhone());

        getRegistrationPage().clickCreateAccount();
        Assert.assertEquals(getRegistrationPage().accountCreatedLabel.getText(), "ACCOUNT CREATED!");
        getRegistrationPage().clickContinueAfterRegistration();
        Assert.assertTrue(getHomePage().btnLogout.isDisplayed());
    }

    private void resetPageObjects() {
        homePage = null;
        loginPage = null;
        productListPage = null;
        registrationPage = null;
        productDetailsPage = null;
        cartPage = null;
        checkoutPage = null;
        paymentPage = null;
    }
}
