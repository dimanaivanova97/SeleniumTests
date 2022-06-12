package selenium.herokuapp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.NoSuchElementException;

public class ChallengingDOM {
    ChromeDriver driver;
    WebDriverWait wait;

    @BeforeTest
    public void setUp() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void navigateToChallengingDOM(){
        driver.get("https://the-internet.herokuapp.com/");

        WebElement challengingDOMlink = driver.findElement(By.xpath("//a[@href='/challenging_dom']"));
        challengingDOMlink.click();

        WebElement heading = driver.findElement(By.xpath("//div[@id='content']//h3"));
        WebElement overview = driver.findElement(By.xpath("//div[@id='content']//p"));
        WebElement firstButton = driver.findElement(By.xpath("//div[@class='large-2 columns']//a[@class='button']"));
        WebElement secondButton = driver.findElement(By.xpath("//div[@class='large-2 columns']//a[@class='button alert']"));
        WebElement thirdButton = driver.findElement(By.xpath("//div[@class='large-2 columns']//a[@class='button success']"));
        Assert.assertEquals(heading.getText(), "Challenging DOM");
        Assert.assertTrue(overview.isDisplayed());
        Assert.assertTrue(firstButton.isDisplayed());
        Assert.assertTrue(secondButton.isDisplayed());
        Assert.assertTrue(thirdButton.isDisplayed());
    }

    @Test
    public void verifyButtons(){
        driver.get("https://the-internet.herokuapp.com/challenging_dom");

        WebElement firstButton = driver.findElement(By.xpath("//div[@class='large-2 columns']//a[@class='button']"));
        WebElement secondButton = driver.findElement(By.xpath("//div[@class='large-2 columns']//a[@class='button alert']"));
        WebElement thirdButton = driver.findElement(By.xpath("//div[@class='large-2 columns']//a[@class='button success']"));
        Assert.assertTrue(firstButton.isDisplayed());
        Assert.assertTrue(secondButton.isDisplayed());
        Assert.assertTrue(thirdButton.isDisplayed());
    }

    @AfterTest
    public void tearDown(){
        driver.close();
    }
}
