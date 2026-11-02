package Tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestBase {
    Page page;
    Browser browser;
    Playwright playwright;
    String base_url;

    @BeforeMethod
    public void setup() throws IOException {
        Properties prop=new Properties();
        FileInputStream fis=new FileInputStream("src/main/resources/config.properties");
        prop.load(fis);
        //mvn test-PRegression_Dbrowser=chrome-Denv=dev
        //ternary operator
        //variable ?true:false
        String browsername=System.getProperty("browser")!=null?System.getProperty("browser"):prop.getProperty("browser");
        String envname=System.getProperty("env")!=null?System.getProperty("env"):prop.getProperty("env");
       // String browsername=prop.getProperty("browser");
        playwright= Playwright.create();
        if("firefox".equals(browsername)){
            browser=playwright.firefox().launch();
        } else if ("safari".equals(browsername)) {
           browser= playwright.webkit().launch();
        }
        else{

            browser=playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));

        }



       // browser=playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
        //Browser browser=playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
        //  Browser browser=playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page=browser.newPage();

       base_url= prop.getProperty(envname+"qa.baseurl");
        page.setDefaultTimeout(5000);// this timeout only cares on locators actions
       // PlaywrightAssertions.setDefaultAssertionTimeout(7000);// this is used for only assertions

    }
}
