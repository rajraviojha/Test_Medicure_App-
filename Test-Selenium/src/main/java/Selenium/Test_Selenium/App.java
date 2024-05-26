package Selenium.Test_Selenium;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;

public class App 
{
	private WebDriver driver;

    public void setUp() {
        //System.setProperty("webdriver.chrome.driver", "D:\\Software\\chromedriver-win64\\chromedriver.exe");

        // Set Chrome options for headless mode
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Add headless argument
        driver = new ChromeDriver(options);

        driver.get(" http://localhost:8082/");
    }

    public void testFormSubmission() {
        // Valid input test
        testForm(driver, "John Doe", "1234567890", "john.doe@example.com", "This is a test message.");

        // Invalid input tests
        testForm(driver, "John123", "1234567890", "john.doe@example.com", "This is a test message."); // Invalid name
        testForm(driver, "John Doe", "12345abc", "john.doe@example.com", "This is a test message."); // Invalid mobile number
        testForm(driver, "John Doe", "1234567890", "john.doe@example", "This is a test message."); // Invalid email
        testForm(driver, "John Doe", "1234567890", "john.doe@example.com", ""); // Invalid message
    }

    public void testForm(WebDriver driver, String name, String mobile, String email, String message) {
        driver.navigate().refresh(); // Refresh the page before each test

        WebElement nameField = driver.findElement(By.xpath("//input[@placeholder='Your Name']"));
        WebElement mobileField = driver.findElement(By.xpath("//input[@placeholder='Phone Number']"));
        WebElement emailField = driver.findElement(By.xpath("//input[@placeholder='Email']"));
        WebElement messageField = driver.findElement(By.xpath("//input[@placeholder='Message']"));
        WebElement sendButton = driver.findElement(By.xpath("//button[normalize-space()='SEND']"));

        nameField.clear();
        mobileField.clear();
        emailField.clear();
        messageField.clear();

        StringBuilder errorDescription = new StringBuilder("No_Errors");

        if (isValidName(name)) {
            nameField.sendKeys(name);
        } else {
            errorDescription.append("Invalid_Name").append(name);
            System.out.println("Invalid name: " + name);
        }

        if (isValidMobile(mobile)) {
            mobileField.sendKeys(mobile);
        } else {
            errorDescription.append("Invalid_Mobile").append(mobile);
            System.out.println("Invalid mobile number: " + mobile);
        }

        if (isValidEmail(email)) {
            emailField.sendKeys(email);
        } else {
            errorDescription.append("Invalid_Email").append(email);
            System.out.println("Invalid email: " + email);
        }

        if (isValidMessage(message)) {
            messageField.sendKeys(message);
        } else {
            errorDescription.append("Invalid_Message").append(message);
            System.out.println("Invalid message: " + message);
        }

        // Take a screenshot after filling the form fields
        takeScreenshot(errorDescription.toString());

        sendButton.click();

        // Optionally, verify if the page scrolled to the top
        long scrollPosition = (Long) ((JavascriptExecutor) driver).executeScript("return window.pageYOffset;");
        if (scrollPosition == 0) {
            System.out.println("Page scrolled to the top successfully.");
        } else {
            System.out.println("Page did not scroll to the top.");
        }
    }

    public void takeScreenshot(String errorDescription) {
        // Format the timestamp
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        // Create the filename with the error description
        String filename = "screenshot_" + errorDescription + "_" + timestamp + ".png";
        // Take the screenshot
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            // Save the screenshot
            FileUtils.copyFile(srcFile, new File("screenshots/" + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Validate that the name contains only alphabetic characters and spaces
    public boolean isValidName(String name) {
        return name.matches("[a-zA-Z\\s]+");
    }

    // Validate that the mobile number contains only numeric characters
    public boolean isValidMobile(String mobile) {
        return mobile.matches("\\d+");
    }

    // Validate that the email has a valid format
    public boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    // Validate that the message is not empty
    public boolean isValidMessage(String message) {
        return !message.trim().isEmpty();
    }

    public void tearDown() throws  IOException {
    
   //System.out.println("Screenshot is stored at" +desFile.getAbsolutePath());
    	
    	if (driver != null) {
            driver.quit();
        }
        
    }
    
    public static void main( String[] args ) throws IOException
    {
    	App app=new App();
    	app.setUp();   
    	app.testFormSubmission();
    	app.tearDown();
    }
}
