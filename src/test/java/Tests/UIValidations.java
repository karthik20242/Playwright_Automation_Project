package Tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class UIValidations {

    Page page;
    Browser browser;
    Playwright playwright;

    @BeforeMethod(alwaysRun = true)
    public void setup(){
        playwright= Playwright.create();
        browser=playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
        //Browser browser=playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
        //  Browser browser=playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page=browser.newPage();
      //  page.setDefaultTimeout(5000);// this timeout only cares on locators actions
        page.navigate("https://rahulshettyacademy.com/AutomationPractice");
       // PlaywrightAssertions.setDefaultAssertionTimeout(7000);// this is used for only assertions

    }

    @Test(groups = {"smoke","RegressionTests"})
    public void popupvalidations() {
        assertThat(page.getByPlaceholder("Hide/Show Example")).isVisible();
        page.locator("#hide-textbox").click();
        // to check is hidden
        assertThat(page.getByPlaceholder("Hide/Show Example")).isHidden();
        //handling popup alert message
        page.onDialog(dialog -> dialog.accept());// handling the popup
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Alert")).click();// clicking alert button
        page.waitForTimeout(3000);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Mouse Hover")).hover();

        //page.waitForTimeout(3000);
        //   page.getByRole(AriaRole.LINK,new Page.GetByRoleOptions().setName("Top")).click();


        // handling i frames
        // handling i frames
        // handling i frames
        FrameLocator framepage = page.frameLocator("#courses-iframe");

        framepage.getByRole(
                AriaRole.LINK,
                new FrameLocator.GetByRoleOptions().setName("Learning paths")
        ).click();

        String textchek = framepage.locator(".inner-box h1").textContent();

        System.out.println(textchek);

    }
    @Test
    public void ScreenShotsTest(){
     //   page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("pagescreenshot.png")));

        Locator displayedEditBox=page.getByPlaceholder("Hide/Show Example");
        displayedEditBox.screenshot(new Locator.ScreenshotOptions().setPath(Paths.get("editboxscreenshot.png")));
        page.getByPlaceholder("Hide/Show Example").click();
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("pagepostscreenshot.png")));


    }
}
