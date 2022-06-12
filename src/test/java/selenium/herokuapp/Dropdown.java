package selenium.herokuapp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Alert;
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
import java.util.List;
import java.util.NoSuchElementException;

public class Dropdown {
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
    public void navigateToDropdownListPage(){
        driver.get("https://the-internet.herokuapp.com/");

        WebElement dropdownLink = driver.findElement(By.xpath("//a[@href='/dropdown']"));
        dropdownLink.click();

        WebElement header = driver.findElement(By.xpath("//div[@class='example']//h3"));
        WebElement overview = driver.findElement(By.xpath("//div[@class='example']//p"));
        Assert.assertTrue(header.isDisplayed());
        Assert.assertTrue(overview.isDisplayed());
    }

    @Test(priority = 2)
    public void dropdownOptions(){
        driver.get("https://the-internet.herokuapp.com/dropdown");
        WebElement dropdownButton = driver.findElement(By.id("dropdown"));
        dropdownButton.click();

        List<WebElement> dropdownClass = driver.findElements(By.xpath("//select[@id='dropdown']//option"));

        Assert.assertEquals(dropdownClass.size(), 3);

        for (int i = dropdownClass.size(); i > 0; i--) {
            WebElement options = driver.findElement(By.xpath("//select[@id='dropdown']//option[" + i + "]"));

            options.click();
            if (i==1){
                Assert.assertFalse(options.isSelected());
            } else {
                Assert.assertTrue(options.isSelected());
            }
        }
    }

    @AfterTest
    public void tearDown(){
        driver.close();
    }
}
