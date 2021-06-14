package cz.czechitas.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestyPrihlasovaniNaKurzy {

    private static final String URL_APP = "https://cz-test-dva.herokuapp.com/";

    WebDriver browserFox;

    @BeforeEach
    public void setUp() {
//      System.setProperty("webdriver.gecko.driver", System.getProperty("user.home") + "/Java-Training/Selenium/geckodriver");
        System.setProperty("webdriver.gecko.driver", "C:\\Java-Training\\Selenium\\geckodriver.exe");
        browserFox = new FirefoxDriver();
        browserFox.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    @Test
    public void existingParentShouldLog() {
        browserFox.navigate().to(URL_APP);

        this.tapLoginUserBtn();
        this.login("beznyemail@seznam.cz", "Karelctvrty4");


        WebElement isLoggedIn = browserFox.findElement(By.xpath("//*[contains(@class,'dropdown')]/span"));
        Assertions.assertNotNull(isLoggedIn);
        String isLoggedInText = isLoggedIn.getText();
        Assertions.assertEquals("Přihlášen", isLoggedInText);
    }

    @Test
    public void ParentShouldChooseCourseLogInCreateApplication() {
        browserFox.navigate().to(URL_APP);

        this.chooseCourseCathegory(0);
        this.chooseCourse();
        this.login("beznyemail@seznam.cz", "Karelctvrty4");
        this.fillApplication();

        this.confirmApplication();

        WebElement loadConfirmationLink = browserFox.findElement(By.xpath("//a[contains(@title,'Stáhnout p')]"));
        Assertions.assertNotNull(loadConfirmationLink);

    }

    @Test
    public void ParentShouldLoginChooseCourseAndApply() {
        browserFox.navigate().to(URL_APP);

        this.tapLoginUserBtn();
        this.login("beznyemail@seznam.cz", "Karelctvrty4");

        WebElement createNewApplicationBtn = browserFox.findElement(By.xpath("//a[contains(text(), 'Vytvořit novou')]"));
        createNewApplicationBtn.click();

        List<WebElement> moreInfoBtns = browserFox.findElements(By.xpath("//a[text()='Více informací']"));
        WebElement moreInfoDABtn = moreInfoBtns.get(0);
        moreInfoDABtn.click();

        List<WebElement> createApplicationBtns = browserFox.findElements(By.xpath("//div/a[text()='Vytvořit přihlášku']"));
        WebElement createApplicationBtn1 = createApplicationBtns.get(1);
        createApplicationBtn1.click();

        this.fillApplication();

        WebElement createApplicationBtn = browserFox.findElement(By.xpath("//input[@type = 'submit']"));
        createApplicationBtn.click();

        WebElement loadConfirmationLink = browserFox.findElement(By.xpath("//a[contains(@title,'Stáhnout p')]"));
        Assertions.assertNotNull(loadConfirmationLink);
    }

    @Test
    public void parentShouldLoginAndEditApplication() {
        browserFox.navigate().to("https://cz-test-jedna.herokuapp.com/");
        WebElement LoginMainPageBtn = browserFox.findElement(By.xpath("//a[text()='Přihlásit                ']"));
        LoginMainPageBtn.click();
        this.login("beznyemail@seznam.cz", "Karelctvrty4");

        WebElement EditBtn = browserFox.findElement(By.xpath("//a[contains(text(), 'Upravit')][1]"));
        EditBtn.click();

        WebElement restrictionField = browserFox.findElement(By.xpath("//textarea[@name = 'restrictions']"));
        restrictionField.clear();

        WebElement restrictionCheckBox = browserFox.findElement(By.xpath("//label[contains(@for, 'restrictions_yes')]"));
        restrictionCheckBox.click();

        WebElement noteField = browserFox.findElement(By.xpath("//textarea[@name = 'note']"));
        noteField.sendKeys("Už nemá spalničky.");

        WebElement createApplicationBtn = browserFox.findElement(By.xpath("//input[@type = 'submit']"));
        createApplicationBtn.click();

        Assertions.assertNotNull(EditBtn);
    }

    private void tapLoginUserBtn() {
        WebElement LoginMainPageBtn = browserFox.findElement(By.linkText("Přihlásit"));
        LoginMainPageBtn.click();
    }

    private void confirmLogin() {
        WebElement loginFormBtn = browserFox.findElement(By.xpath("//button[@type='submit']"));
        loginFormBtn.click();
    }

    private void fillEmailField(String email) {
        WebElement emailValue = browserFox.findElement(By.id("email"));
        emailValue.sendKeys(email);
    }
    private void fillPasswordField(String password) {
        WebElement emailValue = browserFox.findElement(By.id("password"));
        emailValue.sendKeys(password);
    }

    private void login(String email, String password) {

        this.fillEmailField(email);

        this.fillPasswordField(password);

        this.confirmLogin();
    }

    private void chooseCourseCathegory(int courseOrder) {
        List<WebElement> moreInfoBtns = browserFox.findElements(By.xpath("//a[text()='Více informací']"));
        WebElement moreInfoDABtn = moreInfoBtns.get(courseOrder);
        moreInfoDABtn.click();
    }

    private void chooseCourse() {
        List<WebElement> createApplicationBtns = browserFox.findElements(By.xpath("//div/a[text()='Vytvořit přihlášku']"));
        WebElement createApplicationBtn1 = createApplicationBtns.get(1);
        createApplicationBtn1.click();
    }

    private void confirmApplication() {
        WebElement createApplicationBtn = browserFox.findElement(By.xpath("//input[@type = 'submit']"));
        createApplicationBtn.click();
    }

    private void fillApplication() {
        WebElement chooseTerm = browserFox.findElement(By.xpath("//*[contains(text(), 'Vyberte termín')]"));
        chooseTerm.click();
        WebElement chooseTermInput = browserFox.findElement(By.xpath("//input[@type='search']"));
        chooseTermInput.sendKeys("05." + Keys.ENTER);

        WebElement forenameField = browserFox.findElement(By.xpath("//input[@name='forename']"));
        forenameField.sendKeys("Jan");

        WebElement surnameField = browserFox.findElement(By.xpath("//input[@name='surname']"));
        String randomSurname = "Lucemburský" + System.currentTimeMillis();

        surnameField.sendKeys(randomSurname);

        WebElement BirthdayField = browserFox.findElement(By.xpath("//input[@name='birthday']"));
        BirthdayField.sendKeys("1.1.2010");

        WebElement choosePayment = browserFox.findElement(By.xpath("//label[contains(@for, 'payment_transfer')]"));
        choosePayment.click();

        WebElement restrictionCheckBox = browserFox.findElement(By.xpath("//label[contains(@for, 'restrictions_yes')]"));
        restrictionCheckBox.click();

        WebElement restrictionField = browserFox.findElement(By.xpath("//textarea[@name = 'restrictions']"));
        restrictionField.sendKeys("Spalničky");

        WebElement termsConditionsCheckBox = browserFox.findElement(By.xpath("//label[contains(@for, 'terms_conditions')]"));
        termsConditionsCheckBox.click();
    }

    @AfterEach
    public void tearDown() {
        browserFox.close();
    }
}
