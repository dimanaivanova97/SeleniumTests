package selenium.skillo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class FirstTest {

    ChromeDriver driver;
    WebDriverWait wait;

    @BeforeTest
    public void setUp() {

        // 1st approach - Chrome Driver manual set up.
//        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        // Headless implementation
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200", "--ignore-certificate-errors");
//        driver = new ChromeDriver(options);

        // Different wait types
        // Explicit Wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        // Implicit Wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        // Page Load
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        // Driver settings
        driver.manage().window().maximize();
    }

    @Test(priority = 0)
    public void loginTest() throws InterruptedException {

        driver.get("http://training.skillo-bg.com/posts/all");

        WebElement loginButton = driver.findElement(By.id("nav-link-login"));
        loginButton.click();
        WebElement userNameField = driver.findElement(By.id("defaultLoginFormUsername"));
        userNameField.click();
        userNameField.sendKeys("Dimana.ivanova");
        WebElement passwordField = driver.findElement(By.id("defaultLoginFormPassword"));
        passwordField.click();
        passwordField.sendKeys("Dimana.97");
        WebElement rememberMeCheckbox = driver.findElement(By.xpath("//input[@type='checkbox']"));
        rememberMeCheckbox.click();
        WebElement signInButton = driver.findElement(By.id("sign-in-button"));
        signInButton.click();

        WebElement newPostButton = driver.findElement(By.id("nav-link-new-post"));
        Assert.assertTrue(newPostButton.isDisplayed());
    }

    @Test
    public void dropDownTest(){
        driver.get("https://www.mobile.bg/pcgi/mobile.cgi");
        WebElement cookieConsentButton = driver.findElement(By.xpath("//div[@class='fc-dialog-container']//button[@class='fc-button fc-cta-consent fc-primary-button']"));
        cookieConsentButton.click();

        Select dropdownMarka = new Select(driver.findElement(By.xpath("//select[@name='marka']")));

        dropdownMarka.selectByVisibleText("Honda");

        Select dropdownModel = new Select(driver.findElement(By.xpath("//select[@name='model']")));
        dropdownModel.selectByVisibleText("Civic");

        WebElement searchButton = driver.findElement(By.xpath("//input[@type='button'][@name='button2']"));
        wait.until(ExpectedConditions.visibilityOf(searchButton));
        searchButton.click();

        WebElement firstElement = driver.findElement(By.xpath("//table[@class='tablereset'][2]"));

        Assert.assertTrue(firstElement.isDisplayed());


    }

    @AfterMethod
    public void tearDown(){
        driver.close();
    }
}
