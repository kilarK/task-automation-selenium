package cz.czechitas.selenium;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
    public void existingParentIsAbleToLog() {
        prohlizec.navigate().to("https://cz-test-jedna.herokuapp.com/");
        WebElement LoginMainPageBtn = prohlizec.findElement(By.xpath("//a[text()='Přihlásit                ']"));
        LoginMainPageBtn.click();
        this.login("beznyemail@seznam.cz", "Karelctvrty4");

        WebElement isLoggedIn = prohlizec.findElement(By.xpath("//*[contains(@class,'dropdown')]/span"));
        String isLoggedInText = isLoggedIn.getText();

        Assertions.assertEquals("Přihlášen", isLoggedInText);
    }

    @Test
    public void ParentIsAbleToChooseCourseAndLoggIn() {
        prohlizec.navigate().to("https://cz-test-jedna.herokuapp.com/");

        List<WebElement> moreInfoBtns = prohlizec.findElements(By.xpath("//a[text()='Více informací']"));
        WebElement moreInfoDABtn = moreInfoBtns.get(0);
        moreInfoDABtn.click();

        List<WebElement> createApplicationBtns = prohlizec.findElements(By.xpath("//div/a[text()='Vytvořit přihlášku']"));
        WebElement createApplicationBtn1 = createApplicationBtns.get(1);
        createApplicationBtn1.click();

        this.login("beznyemail@seznam.cz", "Karelctvrty4");
        this.fillApplication("Jan", "Lucemburský");

        WebElement createApplicationBtn = prohlizec.findElement(By.xpath("//input[@type = 'submit']"));
        createApplicationBtn.click();

        WebElement loadConfirmationLink = prohlizec.findElement(By.xpath("//a[contains(@title,'Stáhnout p')]"));
        Assertions.assertNotNull(loadConfirmationLink);

    }



    private void login(String email, String password) {
        WebElement emailValue = prohlizec.findElement(By.id("email"));
        emailValue.click();
        emailValue.sendKeys(email);
        WebElement passwordValue = prohlizec.findElement(By.id("password"));
        passwordValue.sendKeys(password);

        WebElement loginFormBtn = prohlizec.findElement(By.xpath("//button[@type='submit']"));
        loginFormBtn.click();
    }

    private void fillApplication(String forename, String surname) {
        WebElement chooseTerm = prohlizec.findElement(By.xpath("//*[contains(text(), 'Vyberte termín')]"));
        chooseTerm.click();
        WebElement chooseTermInput = prohlizec.findElement(By.xpath("//input[@type='search']"));
        chooseTermInput.sendKeys("05." + Keys.ENTER);

        WebElement parentNameField = prohlizec.findElement(By.id("parent_name"));
        Assertions.assertEquals("Karel Čtvrtý",parentNameField.getAttribute("value"));

        WebElement forenameField = prohlizec.findElement(By.xpath("//input[@name='forename']"));
        forenameField.sendKeys(forename);
        WebElement surnameField = prohlizec.findElement(By.xpath("//input[@name='surname']"));
        surnameField.sendKeys(surname);
        WebElement BirthdayField = prohlizec.findElement(By.xpath("//input[@name='birthday']"));
        BirthdayField.sendKeys("1.1.2010");

        WebElement parentEmail = prohlizec.findElement(By.id("email"));
        Assertions.assertEquals("beznyemail@seznam.cz",parentEmail.getAttribute("value"));

        WebElement choosePayment = prohlizec.findElement(By.xpath("//label[contains(@for, 'payment_transfer')]"));
        choosePayment.click();

        WebElement restrictionCheckBox = prohlizec.findElement(By.xpath("//label[contains(@for, 'restrictions_yes')]"));
        restrictionCheckBox.click();

        WebElement noteField = prohlizec.findElement(By.xpath("//textarea[@name = 'restrictions']"));
        noteField.sendKeys("Spalničky");

        WebElement termsConditionsCheckBox = prohlizec.findElement(By.xpath("//label[contains(@for, 'terms_conditions')]"));
        termsConditionsCheckBox.click();
    }

    @AfterEach
    public void tearDown() {
        prohlizec.close();
    }
}
