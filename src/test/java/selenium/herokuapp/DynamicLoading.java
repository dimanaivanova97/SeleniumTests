package selenium.herokuapp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class DynamicLoading {
    ChromeDriver driver;
    WebDriverWait wait;
    Actions actions;

    @BeforeTest
    public void setUp() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        actions = new Actions(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void navigateToDynamicLoadingPage(){
        driver.get("https://the-internet.herokuapp.com/");

        WebElement dynamicLoadingLink = driver.findElement(By.xpath("//a[@href='/dynamic_loading']"));
        dynamicLoadingLink.click();

        WebElement header = driver.findElement(By.xpath("//div[@class='example']//h3"));
        WebElement overview = driver.findElement(By.xpath("//div[@class='example']//p"));
        Assert.assertTrue(header.isDisplayed());
        Assert.assertEquals(header.getText(), "Dynamically Loaded Page Elements");
        Assert.assertTrue(overview.isDisplayed());
    }

    @Test
    public void hiddenElement(){
        driver.get("https://the-internet.herokuapp.com/dynamic_loading");
        WebElement hiddenElementLink = driver.findElement(By.xpath("//a[@href='/dynamic_loading/1']"));
        hiddenElementLink.click();

        WebElement header = driver.findElement(By.xpath("//div[@class='example']//h4"));

        Assert.assertEquals(header.getText(), "Example 1: Element on page that is hidden");

        WebElement startButton = driver.findElement(By.xpath("//div[@id='start']//button"));
        startButton.click();

        WebElement hiddenElement = driver.findElement(By.id("finish"));
        wait.until(ExpectedConditions.visibilityOf(hiddenElement));

        Assert.assertEquals(hiddenElement.getText(), "Hello World!");

    }

    @Test
    public void renderedElement(){
        driver.get("https://the-internet.herokuapp.com/dynamic_loading");
        WebElement renderedElementLink = driver.findElement(By.xpath("//a[@href='/dynamic_loading/2']"));
        renderedElementLink.click();

        WebElement header = driver.findElement(By.xpath("//div[@class='example']//h4"));

        Assert.assertEquals(header.getText(), "Example 2: Element rendered after the fact");

        WebElement startButton = driver.findElement(By.xpath("//div[@id='start']//button"));
        startButton.click();

        WebElement hiddenElement = driver.findElement(By.id("finish"));
        wait.until(ExpectedConditions.visibilityOf(hiddenElement));

        Assert.assertEquals(hiddenElement.getText(), "Hello World!");
    }

    @AfterTest
    public void tearDown(){
        driver.close();
    }
}
