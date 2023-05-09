import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CardApplicationTest {

    WebDriver driver;


    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);

    }

    @AfterEach
    void teardown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void SuccessfulSendingTest() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Смирнов Василий");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79774443322");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();

        assertEquals(expected, actual);

    }

    @Test
    public void SuccessfulSendingWhereLastNameContainsASpaceTest() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Лазарева Вавилова Лариса");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79654267906");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();

        assertEquals(expected, actual);

    }

    @Test
    public void SuccessfulSendingWhereNameContainsAHyphenTest() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Мудрова-Иванова Варвара");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79239764534");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();

        assertEquals(expected, actual);

    }

    @Test
    public void NameValidationTest() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("IVANOV IVAN");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79325573456");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'] .input__sub")).getText().trim();

        assertEquals(expected, actual);

    }

    @Test
    public void PhoneValidationTest() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Дмитриев Павел");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("3-66-55");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'] .input__sub")).getText().trim();

        assertEquals(expected, actual);

    }

    @Test
    public void PhoneValidation2Test() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Григорий Ватрушкин");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7 (989) 445-32-34");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'] .input__sub")).getText().trim();

        assertEquals(expected, actual);

    }

    @Test
    public void CheckboxValidationTest() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Романов Роман");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79096754432");
        driver.findElement(By.tagName("button")).click();
        String expected = "rgba(255, 92, 92, 1)";
        String actual = driver.findElement(By.className("checkbox__text")).getCssValue("color");

        assertEquals(expected, actual);

    }


}
