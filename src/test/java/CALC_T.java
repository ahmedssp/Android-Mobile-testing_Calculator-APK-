import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class CALC_T {
    AppiumDriver driver;

    @BeforeTest // contains configuration and clear
    public void setup() throws MalformedURLException, InterruptedException {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("platformName", "Android");
        cap.setCapability("platformVersion", "11");
        cap.setCapability("deviceName", "realme");
        cap.setCapability("automationName", "Appium");
        cap.setCapability("app", "D:\\testing udacity\\mobile test\\calculator.apk");
        driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), cap);
//        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.findElement(By.id("com.google.android.calculator:id/clr")).click(); //clear
    }

    @AfterTest  // end session
    public void tearDown() {
        if (null != driver) {
            driver.quit();
        }
    }

    @Test // test (+ , - ,* ,/) for all buttons
    public void Calculator_single_function() throws InterruptedException {
//        String[] elements = { "add", "sub","mul","div" };
        String op1 = "com.google.android.calculator:id/op_";
        String opr = null;
        String val = null;

        for (int x = 0; x <= 3; x++) {  //to select operation  + , -, / ,*
            for (int i = 1; i < 4; i++) {
                String s1 = "com.google.android.calculator:id/digit_";
                String s2 = Integer.toString(i);
                String s3 = Integer.toString(1);
                String out1 = s1 + s2, out2 = s1 + s2, out3 = s1 + s3;

                // logic operator and validation out
                if (x == 0) {
                    opr = op1 + "add";
                    val = Integer.toString(i * 2);
                } else if (x == 1) {
                    opr = op1 + "sub";
                    val = Integer.toString(0);
                } else if (x == 2) {
                    opr = op1 + "mul";
                    val = Integer.toString(i * i);
                } else if (x == 3) {
                    opr = op1 + "div";
                    out2 = out3;
                    val = Integer.toString(i);
                }

                driver.findElement(By.id(out1)).click(); // Friset number
                driver.findElement(By.id(opr)).click();  // operation
                driver.findElement(By.id(out2)).click();// second number
                driver.findElement(By.id("com.google.android.calculator:id/eq")).click(); //equal

                Assert.assertEquals(driver.findElement(By.id("com.google.android.calculator:id/result_final")).getText(), val); //validation
            }
        }
    }

    @Test
    public void Combination() {
        driver.findElement(By.id("com.google.android.calculator:id/digit_4")).click();

        driver.findElement(By.id("com.google.android.calculator:id/op_mul")).click();
        driver.findElement(By.id("com.google.android.calculator:id/parens")).click();
        driver.findElement(By.id("com.google.android.calculator:id/digit_5")).click();
        driver.findElement(By.id("com.google.android.calculator:id/op_sub")).click();
        driver.findElement(By.id("com.google.android.calculator:id/digit_4")).click();
        driver.findElement(By.id("com.google.android.calculator:id/parens")).click();
        driver.findElement(By.id("com.google.android.calculator:id/op_add")).click();
        driver.findElement(By.id("com.google.android.calculator:id/digit_2")).click();

        driver.findElement(By.id("com.google.android.calculator:id/eq")).click();
        Assert.assertEquals(driver.findElement(By.id("com.google.android.calculator:id/result_final")).getText(), "6");
        driver.findElement(By.id("com.google.android.calculator:id/clr")).click(); //clear

    }
    @Test
    public void Div_Zero() {
        driver.findElement(By.id("com.google.android.calculator:id/digit_1")).click(); //1
        driver.findElement(By.id("com.google.android.calculator:id/op_div")).click();//div
        driver.findElement(By.id("com.google.android.calculator:id/digit_0")).click();//zero
        driver.findElement(By.id("com.google.android.calculator:id/eq")).click();//=
        Assert.assertEquals(driver.findElement(By.id("com.google.android.calculator:id/result_preview")).getText(),"Can't divide by 0");
        driver.findElement(By.id("com.google.android.calculator:id/clr")).click(); //clear

    }
    @Test
    public void range_out() {
        driver.findElement(By.id("com.google.android.calculator:id/digit_1")).click(); //1
        driver.findElement(By.id("com.google.android.calculator:id/digit_0")).click(); //0
        driver.findElement(By.id("com.google.android.calculator:id/op_pow")).click(); // ^
        driver.findElement(By.id("com.google.android.calculator:id/digit_1")).click(); //1
        driver.findElement(By.id("com.google.android.calculator:id/digit_0")).click(); //0
        driver.findElement(By.id("com.google.android.calculator:id/op_pow")).click(); // ^
        driver.findElement(By.id("com.google.android.calculator:id/digit_5")).click(); //1
        driver.findElement(By.id("com.google.android.calculator:id/eq")).click();//=
        Assert.assertEquals(driver.findElement(By.id("com.google.android.calculator:id/result_preview")).getText(), "Can't calculate");
        driver.findElement(By.id("com.google.android.calculator:id/del")).click(); //1
        driver.findElement(By.id("com.google.android.calculator:id/digit_6")).click(); //1
        driver.findElement(By.id("com.google.android.calculator:id/eq")).click();//=
        Assert.assertEquals(driver.findElement(By.id("com.google.android.calculator:id/result_preview")).getText(), "Value too large");
        driver.findElement(By.id("com.google.android.calculator:id/clr")).click(); //clear

    }
}

