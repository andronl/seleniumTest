package localbrowsers;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by andrei on 22/12/19.
 */
@Test()
public class Firefox {
    private WebDriver driver;

    @BeforeTest
    public void firefoxSetup() {
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("marionette", true);
        System.setProperty("webdriver.gecko.driver", "/Users/andreilyuzin/Downloads/geckodriver");
         driver = new FirefoxDriver();
    }

    @Test
    public void test() {
        WebDriverWait wait = new WebDriverWait(driver,40);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        driver.get("https://xn--d1apb.xn--d1achjhdicc8bh4h.xn--p1ai");
        driver.findElement(By.id("layout_104")).click();
        wait.until(ExpectedConditions.titleIs("Мониторинг показателей работы МФЦ - ИС МДМ"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("mdm-select")));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("select2-selection__rendered")));
        driver.findElement(By.xpath("/html/body/div/div/div/div/div/div/div/section/div/div/div/div[4]/div/div[2]/div[1]/div[3]/div/span/span[1]/span")).click();
        executor.executeScript("document.getElementsByClassName('select-aggregation-territory select2-hidden-accessible')[0].setAttribute('aria-hidden', 'false')");
        List<WebElement> elements = driver.findElements(By.className("select2-results__option"));

        for (WebElement e : elements) {
            if (e.getText().equals("Субъекты РФ")) {
             e.click();
             break;
            }
        }

        Assert.assertEquals(driver.findElement(By.cssSelector(".select2-container--below > span:nth-child(1) > span:nth-child(1)")).getText(), "Субъекты РФ");

        driver.findElement(By.cssSelector(".apply-filters")).click();
        wait.until(ExpectedConditions.visibilityOf( driver.findElement(By.cssSelector(".DTFC_LeftBodyLiner > table:nth-child(1) > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(3)"))));
        Assert.assertEquals(driver.findElement(By.cssSelector(".DTFC_LeftBodyLiner > table:nth-child(1) > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(3)")).getText(),
        "Все МФЦ/ТОСП");
    }

    @AfterTest
    public void testTeardown() {
        driver.quit();
    }
}
