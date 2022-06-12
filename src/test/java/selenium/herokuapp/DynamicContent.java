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

public class DynamicContent {
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
    public void navigateToDynamicContentPage(){
        driver.get("https://the-internet.herokuapp.com/");

        WebElement dynamicContentLink = driver.findElement(By.xpath("//a[@href='/dynamic_content']"));
        dynamicContentLink.click();

        WebElement header = driver.findElement(By.xpath("//div[@class='example']//h3"));
        WebElement overview = driver.findElement(By.xpath("//div[@class='example']//p"));
        Assert.assertTrue(header.isDisplayed());
        Assert.assertTrue(overview.isDisplayed());
    }

    @Test(priority = 2)
    public void staticContent(){
        driver.get("https://the-internet.herokuapp.com/dynamic_content?with_content=static");

        WebElement firstFieldPicture = driver.findElement(By.xpath("//div[@id='content']//div[@class='row'][1]//div[@class='large-2 columns']//img"));
        WebElement firstFieldText = driver.findElement(By.xpath("//div[@id='content']//div[@class='row'][1]//div[@class='large-10 columns']"));
        WebElement secondFieldPicture = driver.findElement(By.xpath("//div[@id='content']//div[@class='row'][2]//div[@class='large-2 columns']//img"));
        WebElement secondFieldText = driver.findElement(By.xpath("//div[@id='content']//div[@class='row'][2]//div[@class='large-10 columns']"));

        Assert.assertTrue(firstFieldPicture.isDisplayed());
        Assert.assertTrue(firstFieldText.isDisplayed());
        Assert.assertEquals(firstFieldPicture.getAttribute("src"), "https://the-internet.herokuapp.com/img/avatars/Original-Facebook-Geek-Profile-Avatar-5.jpg");
        Assert.assertEquals(firstFieldText.getText(), "Accusantium eius ut architecto neque vel voluptatem vel nam eos minus ullam dolores voluptates enim sed voluptatem rerum qui sapiente nesciunt aspernatur et accusamus laboriosam culpa tenetur hic aut placeat error autem qui sunt.");
        Assert.assertTrue(secondFieldText.isDisplayed());
        Assert.assertTrue(secondFieldPicture.isDisplayed());
        Assert.assertEquals(secondFieldPicture.getAttribute("src"), "https://the-internet.herokuapp.com/img/avatars/Original-Facebook-Geek-Profile-Avatar-1.jpg");
        Assert.assertEquals(secondFieldText.getText(), "Omnis fugiat porro vero quas tempora quis eveniet ab officia cupiditate culpa repellat debitis itaque possimus odit dolorum et iste quibusdam quis dicta autem sint vel quo vel consequuntur dolorem nihil neque sunt aperiam blanditiis.");

    }

    @Test(priority = 2)
    public void dynamicContent(){
        driver.get("https://the-internet.herokuapp.com/dynamic_content?with_content=static");

        WebElement firstFieldPicture = driver.findElement(By.xpath("//div[@id='content']//div[@class='row'][3]//div[@class='large-2 columns']//img"));
        WebElement firstFieldText = driver.findElement(By.xpath("//div[@id='content']//div[@class='row'][3]//div[@class='large-10 columns']"));

        Assert.assertTrue(firstFieldPicture.isDisplayed());
        Assert.assertTrue(firstFieldText.isDisplayed());
        Assert.assertFalse(firstFieldText.getText().isEmpty());
        Assert.assertFalse(firstFieldPicture.getAttribute("src").isEmpty());
    }

    @AfterTest
    public void tearDown(){
        driver.close();
    }
}
