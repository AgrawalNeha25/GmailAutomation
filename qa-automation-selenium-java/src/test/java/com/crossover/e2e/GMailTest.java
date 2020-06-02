package com.crossover.e2e;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;
import junit.framework.TestCase;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class GMailTest extends TestCase {
    private WebDriver driver;
    private Properties properties = new Properties();

    public void setUp() throws Exception {
        
        properties.load(new FileReader(new File("src/test/resources/test.properties")));
        //Dont Change below line. Set this value in test.properties file incase you need to change it..
        System.setProperty("webdriver.chrome.driver",properties.getProperty("webdriver.chrome.driver") );
        driver = new ChromeDriver();
    }

    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void testSendEmail() throws Exception {
        //Open gmail Url
        driver.get("https://mail.google.com/");

        //Provide email username
        WebElement userElement = driver.findElement(By.id("identifierId"));
        userElement.sendKeys(properties.getProperty("username"));

        //Clicking on next element
        driver.findElement(By.id("identifierNext")).click();
        Thread.sleep(5000);	

        //Poviding password input field
        WebElement passwordElement = driver.findElement(By.cssSelector("input[name='password']"));
        passwordElement.sendKeys(properties.getProperty("password"));

        //Clicking on next button
        driver.findElement(By.id("passwordNext")).click();
        Thread.sleep(15000);

        //Clicking on compose button
        WebElement composeElement = driver.findElement(By.xpath("//div[contains(text(), 'Compose')]"));
        composeElement.click();
        Thread.sleep(5000);

        //Providing recepient email address
        driver.findElement(By.name("to")).clear();
        driver.findElement(By.name("to")).sendKeys(String.format("%s@gmail.com", properties.getProperty("username")));
        Thread.sleep(5000);
        
        //Providing email subject
        WebElement subjectElement = driver.findElement(By.name("subjectbox"));
        subjectElement.sendKeys(properties.getProperty("emailsubject"));
        Thread.sleep(5000);
        
        //providing email body
        WebElement bodyElement = driver.findElement(By.xpath("//div[@aria-label='Message Body']"));
        bodyElement.sendKeys(properties.getProperty("emailbody"));
        Thread.sleep(5000);
        
        //Selecting label as social
        WebElement moreOptionsElement = driver.findElement(By.xpath("//div[@data-tooltip='More options']"));
        moreOptionsElement.click();
        Thread.sleep(5000);
        
        WebElement labelElement = driver.findElement(By.xpath("//div[text()='Label']"));
        labelElement.click();
        Thread.sleep(5000);
        
        WebElement socialLabelElement = driver.findElement(By.xpath("//div[@title='Social']"));
        socialLabelElement.click();
        Thread.sleep(5000);
        
        //Send email
        driver.findElement(By.xpath("//*[@role='button' and text()='Send']")).click();
        Thread.sleep(5000);
        
        //Clicking on Inbox mails
        WebElement InboxElement = driver.findElement(By.xpath("//a[text()='Inbox']"));
        InboxElement.click();
        Thread.sleep(5000);

        //Starring email
        WebElement starredElement = driver.findElement(By.xpath("//span[@title='Not starred']"));
        starredElement.click();
        Thread.sleep(5000);
        
        WebElement mailElement = driver.findElement(By.xpath("//table[@class='F cf zt']/tbody/tr"));
        mailElement.click();
        Thread.sleep(5000);
        
        //Verifying if email comes under social label
        WebElement mailLabelElement = driver.findElement(By.xpath("/html/body/div[7]/div[3]/div/div[2]/div[1]/div[2]/div/div/div/div/div[1]/div[2]/div[1]/div/div[4]/div[2]/div[1]"));
        mailLabelElement.click();
        Thread.sleep(5000);
        
        WebElement socialMailLabelElement = driver.findElement(By.cssSelector("div[title='Social']"));
        if (socialMailLabelElement.getAttribute("aria-checked").equals("true"))
            System.out.println("Mail is verified to be labeled as social");
        else
            System.out.println("Mail is not verified to be labeled as social");

         //Verifying subject of an email
        WebElement mailSubjectElement = driver.findElement(By.xpath("//div[@class='ha']/h2"));
         	
         if (mailSubjectElement.getText().equals(properties.getProperty("emailsubject")))
            System.out.println("Mail subject has been verified correctly");
         else
            System.out.println("Mail subject has not been verified correctly");

         //Verifying mail body
         WebElement mailBodyElement = driver.findElement(By.xpath("//div[@class='a3s aXjCH ']/div[@dir='ltr']"));
         if (mailBodyElement.getText().equals(properties.getProperty("emailbody")))
            System.out.println("Mail Body has been verified successfully");
         else
            System.out.println("Mail body has not been verified correctly");
    }
}
