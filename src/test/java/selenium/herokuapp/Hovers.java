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

public class Hovers {
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

    @Test(priority = 1)
    public void navigateToHoversPage(){
        driver.get("https://the-internet.herokuapp.com/");

        WebElement hoversLink = driver.findElement(By.xpath("//a[@href='/hovers']"));
        hoversLink.click();

        WebElement header = driver.findElement(By.xpath("//div[@class='example']//h3"));
        WebElement overview = driver.findElement(By.xpath("//div[@class='example']//p"));
        Assert.assertTrue(header.isDisplayed());
        Assert.assertTrue(overview.isDisplayed());
    }

    @Test(priority = 2)
    public void hoversValidation(){
        driver.get("https://the-internet.herokuapp.com/hovers");

        WebElement firstUserBox = driver.findElement(By.xpath("//div[@class='figure'][1]//img[1]"));
        WebElement secondUserBox = driver.findElement(By.xpath("//div[@class='figure'][2]//img[1]"));
        WebElement thirdUserBox = driver.findElement(By.xpath("//div[@class='figure'][3]//img[1]"));

        actions.moveToElement(firstUserBox).perform();
        WebElement firstUserLabelOnHover = driver.findElement(By.xpath("//div[@class='figure'][1]//h5"));
        Assert.assertTrue(firstUserLabelOnHover.isDisplayed());

        actions.moveToElement(secondUserBox).perform();
        WebElement secondUserLabelOnHover = driver.findElement(By.xpath("//div[@class='figure'][2]//h5"));
        Assert.assertTrue(secondUserLabelOnHover.isDisplayed());

        actions.moveToElement(thirdUserBox).perform();
        WebElement thirdUserLabelOnHover = driver.findElement(By.xpath("//div[@class='figure'][3]//h5"));
        Assert.assertTrue(thirdUserLabelOnHover.isDisplayed());
    }

    @AfterTest
    public void tearDown(){
        driver.close();
    }
}
