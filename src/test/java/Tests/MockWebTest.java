package Tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class MockWebTest {

    Page page;
    Browser browser;
    Playwright playwright;

    @BeforeMethod
    public void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
        //Browser browser=playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
        //  Browser browser=playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();
        page.setDefaultTimeout(5000);// this timeout only cares on locators actions
        page.navigate("https://eventhub.rahulshettyacademy.com/");
        PlaywrightAssertions.setDefaultAssertionTimeout(7000);// this is used for only assertions

    }


    @Test(description = "sandbox banner is shown when 6 events are returned")
    public void DemoTest() {


        // locators


        page.getByLabel("Email").fill("karthiikb0229@gmail.com");
        page.getByLabel("Password").fill("K513322104308@a");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign In")).click();

        // validation the browser events lik visible after login
        assertThat(page.getByRole(AriaRole.LINK,
                new Page.GetByRoleOptions().setName("Browse Events →"))).isVisible();
        //Notes----->
        // locators
        // step1- creating event from admin page
        // for actions default waiting time is 10 sec
        //for assertion default wiating time is 5 sec


        //=========route.fullfill() modyfiying the response======

        //=========checking the banner is visible while 6 events only there ===========
        page.route("**api/events**", route -> route.fulfill(
                new Route.FulfillOptions().setPath(Paths.get("src/main/resources/events_6.json"))
        ));
        page.navigate("https://eventhub.rahulshettyacademy.com/events");

        Locator eventCard = page.getByTestId("event-card");
        assertThat(eventCard.first()).isVisible();  //5 seconds wait
        Assert.assertEquals(eventCard.count(), 6);
        assertThat(page.locator(".mx-1").first()).isVisible();


        //==========checking the banner is not-visible while 4 events only there ===========


        page.route("**api/events**", route -> route.fulfill(
                new Route.FulfillOptions().setPath(Paths.get("src/main/resources/events_4.json"))
        ));

        page.navigate("https://eventhub.rahulshettyacademy.com/events");

        Locator eventCard1 = page.getByTestId("event-card");
        assertThat(eventCard.first()).isVisible();  //5 seconds wait
        Assert.assertEquals(eventCard1.count(), 4);
        assertThat(page.locator(".mx-1").first()).isHidden();
    }



        @Test(description = "page.resume() modifying the request  before response")
        public void routeResumeTest(){

            page.getByLabel("Email").fill("karthiikb0229@gmail.com");
            page.getByLabel("Password").fill("K513322104308@a");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign In")).click();

            // validation the browser events lik visible after login
            assertThat(page.getByRole(AriaRole.LINK,
                    new Page.GetByRoleOptions().setName("Browse Events →"))).isVisible();

            //=======page.resume() modifying the request =====
            page.getByTestId("nav-bookings");

            // trying to login of other account with my cookies
            page.route("**/api/bookings**", route -> route.resume(
                    new Route.ResumeOptions().setUrl("https://api.eventhub.rahulshettyacademy.com/api/bookings/36190")// modifying the url this is another url
            ));
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("View Details")).first().click();
            assertThat(page.getByText("Access Denied")).isVisible();
        }
    }

