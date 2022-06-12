package selenium.herokuapp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class RedirectLink {
    ChromeDriver driver;
    WebDriverWait wait;
    Actions actions;

    @BeforeTest
    public void setUp() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        actions = new Actions(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void navigateToRedirectPage(){
        driver.get("https://the-internet.herokuapp.com/");

        WebElement redirectLink = driver.findElement(By.xpath("//a[@href='/redirector']"));
        redirectLink.click();

        WebElement header = driver.findElement(By.xpath("//div[@class='example']//h3"));
        WebElement overview = driver.findElement(By.xpath("//div[@class='example']//p"));
        Assert.assertTrue(header.isDisplayed());
        Assert.assertEquals(header.getText(), "Redirection");
        Assert.assertTrue(overview.isDisplayed());
    }

    @Test
    public void redirectStatusCodesGroups(){
        driver.get("https://the-internet.herokuapp.com/redirector");
        WebElement redirectButton = driver.findElement(By.id("redirect"));
        redirectButton.click();

        WebElement twoHundredStatusCodes = driver.findElement(By.xpath("//a[@href='status_codes/200']"));
        twoHundredStatusCodes.click();
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='example']/p")).getText(), "This page returned a 200 status code.\n\nFor a definition and common list of HTTP status codes, go here");
        driver.navigate().back();

        WebElement threeHundredStatusCodes = driver.findElement(By.xpath("//a[@href='status_codes/301']"));
        threeHundredStatusCodes.click();
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='example']/p")).getText(), "This page returned a 301 status code.\n\nFor a definition and common list of HTTP status codes, go here");
        driver.navigate().back();

        WebElement fourHundredStatusCodes = driver.findElement(By.xpath("//a[@href='status_codes/404']"));
        fourHundredStatusCodes.click();
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='example']/p")).getText(), "This page returned a 404 status code.\n\nFor a definition and common list of HTTP status codes, go here");
        driver.navigate().back();

        WebElement fiveHundredStatusCodes = driver.findElement(By.xpath("//a[@href='status_codes/500']"));
        fiveHundredStatusCodes.click();
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='example']/p")).getText(), "This page returned a 500 status code.\n\nFor a definition and common list of HTTP status codes, go here");
        driver.navigate().back();

        driver.navigate().back();
        WebElement header = driver.findElement(By.xpath("//div[@class='example']//h3"));
        Assert.assertEquals(header.getText(), "Redirection");
    }

    @Test
    public void redirectCompleteStatusCodeList(){
        driver.get("https://the-internet.herokuapp.com/redirector");
        WebElement redirectButton = driver.findElement(By.id("redirect"));
        redirectButton.click();

        WebElement redirectToListStatusCodesButton = driver.findElement(By.xpath("//div[@class='example']//a"));
        redirectToListStatusCodesButton.click();
        Assert.assertEquals(driver.findElement(By.xpath("//article//h1")).getText(), "Hypertext Transfer Protocol (HTTP) Status Code Registry");

        WebElement referenceButton = driver.findElement(By.xpath("//a[@href='https://www.iana.org/go/rfc9110']"));
        referenceButton.click();
        Assert.assertEquals(driver.findElement(By.id("rfcnum")).getText(), "RFC 9110");

        driver.navigate().back();
        Assert.assertEquals(driver.findElement(By.xpath("//article//h1")).getText(), "Hypertext Transfer Protocol (HTTP) Status Code Registry");

        driver.navigate().back();
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='example']//h3")).getText(), "Status Codes");

        driver.navigate().back();
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='example']//h3")).getText(), "Redirection");
    }

    @AfterTest
    public void tearDown(){
        driver.close();
    }
}
