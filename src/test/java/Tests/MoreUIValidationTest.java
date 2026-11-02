package Tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Paths;

public class MoreUIValidationTest {

    Playwright playwright;
    Browser browser;
    Page page;
    BrowserContext context;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));

        context = browser.newContext(); //new incoginto mode 1st tab
        // always use this tracing in context level not in page leve
        context.tracing().start(new Tracing.StartOptions()//tracing the prcoess
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
        page = context.newPage();//in incognito mode 2nd new tab
        page.navigate("https://rahulshettyacademy.com/loginpagePractise/");
    }

    @AfterMethod
    public void tearDwon() {
        // stopping the tracing after all the test is completed
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("trace.zip")));
    }


    @Test
    public void childWindowHandle() {
        Locator blinkingTexts = page.locator(".blinkingText");
        Page newpage = context.waitForPage(() -> blinkingTexts.first().click());
        newpage.waitForLoadState(); // waiting for the page fully loaded
        String childText = newpage.locator(".red").textContent();
        // Please email us at mentor@rahulshettyacademy.com with below template to receive response
        // childtxt.split("at)---> splitting using "at"
        //so it become [0] Please email us at   [1]  mentor@rahulshettyacademy.com with below template to receive response
        //aplit("at")[1].split(" ")[0] it seperates using space " "
        //  it become  mentor@rahulshettyacademy.com [0]  with [1] below[2]template[3] to [4]receive response
        String emaildid = childText.split("at ")[1].split(" ")[0];
        page.getByLabel("Username:").fill(emaildid);
        page.waitForTimeout(3000);
        System.out.println(page.getByLabel("Username:").inputValue()); // getting the dynamic value present in the page
        page.waitForTimeout(3000);


    }


    @Test(groups = {"smoke"})
    public void  UIControls() {

        // we should use this method when we compulsory know the type which is
        // it is shown in the webpage inspect the radio button
        // when it is compulsory type=radio then we should use this method
        Locator userRdbtn= page.getByRole(AriaRole.RADIO,new Page.GetByRoleOptions().setName("User"));
        userRdbtn.click(); //clikinng the radio button

        page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Okay")).click();// handling the popup
        Assert.assertTrue(userRdbtn.isChecked());// checkint the radio button is cliked

        Locator checkboxTerms=page.getByRole(AriaRole.CHECKBOX,new Page.GetByRoleOptions().setName("I Agree to the terms and conditions"));//cliking the terms and conditions
        checkboxTerms.check();
        Assert.assertTrue(checkboxTerms.isChecked());

        //HANDLING drop down  using  combobox
        //while there is no select optin on dom use this method
        page.getByRole(AriaRole.COMBOBOX).selectOption("Teacher");
       // page.waitForTimeout(3000);


    }
}

