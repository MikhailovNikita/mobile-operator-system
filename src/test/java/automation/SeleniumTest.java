package automation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@Ignore("Take too long, run manually and I broke everything")
public class SeleniumTest {

    private final static String HOST_URL = "http://localhost:8081";
    private final static String LOGIN_PAGE = HOST_URL + "/login";
    private static WebDriver driver;


    @Before
    public void setup(){
        //please don't punch me in the face for this
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Nikita Mikhailov\\IdeaProjects\\chromedriver.exe");
        driver = new ChromeDriver();
    }


    @Test
    public void homepageAccessibility(){
        driver.navigate().to(HOST_URL);
        driver.close();
        driver.quit();
    }


    @Test
    public void loginAsUser(){
        driver.navigate().to(LOGIN_PAGE);
        driver.findElement(By.id("login_field")).sendKeys("a@b.c");
        driver.findElement(By.id("password_field")).sendKeys("qwerty");
        driver.findElement(By.id("login_submit")).click();
        Assert.assertEquals(driver.getCurrentUrl(),HOST_URL + "/client");

        driver.close();
        driver.quit();
    }

    @Test
    public void loginFail(){
        driver.navigate().to(LOGIN_PAGE);
        driver.findElement(By.id("login_field")).sendKeys("wrong@user.name");
        driver.findElement(By.id("password_field")).sendKeys("wrongpass");
        driver.findElement(By.id("login_submit")).click();
        Assert.assertEquals(driver.getCurrentUrl(),LOGIN_PAGE + "?error=true");
        driver.close();
        driver.quit();
    }

    @Test
    public void noLoginAccessToAdmin(){
        driver.navigate().to(HOST_URL + "/admin/new_client");
        Assert.assertEquals(driver.getCurrentUrl(),LOGIN_PAGE);
        driver.close();
        driver.quit();
    }

    @Test
    public void noLoginAccessToClient(){
        driver.navigate().to(HOST_URL + "/client");
        Assert.assertEquals(driver.getCurrentUrl(), LOGIN_PAGE);
        driver.close();
        driver.quit();
    }

    @Test
    public void wrongPage(){
        driver.navigate().to(HOST_URL + "/wrong_page");
        Assert.assertTrue(driver.getPageSource().contains("The page is not found"));

    }
}