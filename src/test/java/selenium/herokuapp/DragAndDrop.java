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

public class DragAndDrop {
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
    public void navigateToDragAndDrop(){
        driver.get("https://the-internet.herokuapp.com/");

        WebElement dragAndDropLink = driver.findElement(By.xpath("//a[@href='/drag_and_drop']"));
        dragAndDropLink.click();

        WebElement header = driver.findElement(By.xpath("//div[@class='example']//h3"));
        Assert.assertTrue(header.isDisplayed());
        Assert.assertEquals(header.getText(), "Drag and Drop");
    }

    @Test(priority = 2)
    public void dragAndDrop(){
        actions = new Actions(driver);

        driver.get("https://the-internet.herokuapp.com/drag_and_drop");

        WebElement firstBox = driver.findElement(By.id("column-a"));
        WebElement secondBox = driver.findElement(By.id("column-b"));
        WebElement headerFirstBox = driver.findElement(By.xpath("//div[@id='column-a']//header"));
        WebElement headerSecondBox = driver.findElement(By.xpath("//div[@id='column-b']//header"));
        actions.dragAndDrop(secondBox, firstBox).perform();

        WebElement headerFirstBoxAfterDragAndDrop = driver.findElement(By.xpath("//div[@id='column-a']//header"));
        WebElement headerSecondBoxAfterDragAndDrop = driver.findElement(By.xpath("//div[@id='column-b']//header"));

        if (headerFirstBox.getText().equals("A") && headerSecondBox.getText().equals("B")){
            Assert.assertEquals(headerFirstBoxAfterDragAndDrop.getText(), "B");
            Assert.assertEquals(headerSecondBoxAfterDragAndDrop.getText(), "A");
        } else {
            Assert.assertEquals(headerFirstBoxAfterDragAndDrop.getText(), "A");
            Assert.assertEquals(headerSecondBoxAfterDragAndDrop.getText(), "B");
        }
    }

    @AfterTest
    public void tearDown(){
        driver.close();
    }
}
