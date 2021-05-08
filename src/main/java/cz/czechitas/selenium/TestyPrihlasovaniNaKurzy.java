package cz.czechitas.selenium;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Year;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestyPrihlasovaniNaKurzy {

    WebDriver prohlizec;

    @BeforeEach
    public void setUp() {
//      System.setProperty("webdriver.gecko.driver", System.getProperty("user.home") + "/Java-Training/Selenium/geckodriver");
        System.setProperty("webdriver.gecko.driver", "C:\\Java-Training\\Selenium\\geckodriver.exe");
        prohlizec = new FirefoxDriver();
        prohlizec.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    @Test
    public void ExistingParentUserIsAbleToLogged() {
        prohlizec.navigate().to("https://cz-test-jedna.herokuapp.com/");
        WebElement LoginMainPageBtn = prohlizec.findElement(By.xpath("//div/a[contains(@href,'https://cz-test-jedna.herokuapp.com/prihlaseni')]"));
        LoginMainPageBtn.click();

        WebElement emailValue = prohlizec.findElement(By.id("email"));
        emailValue.click();
        emailValue.sendKeys("beznyemail@seznam.cz");
        WebElement passwordValue = prohlizec.findElement(By.id("password"));
        passwordValue.sendKeys("Karelctvrty4");

        WebElement loginFormBtn = prohlizec.findElement(By.xpath("//button[@type='submit']"));
        loginFormBtn.click();

        WebElement isLoggedIn = prohlizec.findElement(By.xpath("//*[contains(@class,'dropdown')]/span"));
        String isLoggedInText = isLoggedIn.getText();

        Assertions.assertEquals("Přihlášen", isLoggedInText);

    }

    @Test
    public void prvniZvireVTabulceMusiBytKocka() {
        prohlizec.navigate().to("https://automation3.shinekamil.repl.co/");
        WebElement zalozkaTabulka = prohlizec.findElement(By.linkText("Table"));
        zalozkaTabulka.click();

        List<WebElement> seznamZvirat = prohlizec.findElements(By.xpath("//table/tbody/tr/td[1]"));
        WebElement elementPrvnihoZvirete = seznamZvirat.get(0);
        String prvniZvire = elementPrvnihoZvirete.getText();
        Assertions.assertEquals("Kočka", prvniZvire);
    }

    @AfterEach
    public void tearDown() {
        prohlizec.close();
    }
}
