package Tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class BasicsTest {
//    // invoke browser-> Invoke tab/page-> type url
//    // headmode - browser will visible
//    // headless mode - execution faster and no browser will open
//
    //
    //playwirght is an interface from interface we create obj from that obj we can do operations
    //launch returns broswer instance or obj


    Page page;
    Browser browser;
    Playwright playwright;

    @BeforeMethod
    public void setup(){
        playwright= Playwright.create();
        browser=playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
        //Browser browser=playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
        //  Browser browser=playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page=browser.newPage();
        page.setDefaultTimeout(5000);// this timeout only cares on locators actions
        page.navigate("https://eventhub.rahulshettyacademy.com/");
        PlaywrightAssertions.setDefaultAssertionTimeout(7000);// this is used for only assertions

    }





    @Test(description = "create event -book that event and verify if its booked")
    public void  DemoTest(){

        System.out.println(page.title());
        assertThat(page).hasTitle("EventHub — Discover & Book Events");


        // locators


        page.getByLabel("Email").fill("karthiikb0229@gmail.com");
        page.getByLabel("Password").fill("K513322104308@a");
        page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Sign In")).click();

        // validation the browser events lik visible after login
        assertThat(page.getByRole(AriaRole.LINK,
                new Page.GetByRoleOptions().setName("Browse Events →"))).isVisible();
        //Notes----->
        // locators
        // step1- creating event from admin page
        // for actions default waiting time is 10 sec
        //for assertion default wiating time is 5 sec
        page.navigate("https://eventhub.rahulshettyacademy.com/admin/events");
        page.locator("#event-title-input").fill("Qa automation summit");

        //parent to child locator  parent ( #admin-event-form )     child (form textarea)
        page.locator("#admin-event-form textarea").fill("rahul shetty qa meetups",new Locator.FillOptions().setTimeout(1000));

        //handling dorpdowns
        page.getByLabel("Category").selectOption("Concert");

        page.getByLabel("City").fill("Hyderabad");
        page.getByLabel("Venue").fill("test venue");
        page.getByLabel("Event Date & Time").fill("2026-10-23T12:23");
        page.getByLabel("Price ($)").fill("100");
        page.getByLabel("Total Seats").fill("50");
        page.locator("#add-event-btn").click(new Locator.ClickOptions().setTimeout(12000));

        // get text() is a method which is used to find a text in entire page wherever is visible ("event created")
        assertThat(page.getByText("Event created")).isVisible();
//===========================================================================================================



        // step 2- find newly created event in event page
        page.locator("#nav-events").click();

        // storing all the locators in single variable called eventcards
        Locator eventCards = page.locator("#event-card"); // matching locators with event card will be returned
        page.waitForTimeout(3000);

        System.out.println("the total event cards are:" + eventCards.count()); // dispalys the how many cards are visible

        // visiblity of the card which have added
        Locator targetcard = eventCards.filter(
                new Locator.FilterOptions().setHasText("Qa automation summit"));

        // making assertions to wait  to find the locator
        assertThat(targetcard).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(10000));
        //accessing the seats from the specific card
        String seatsText=targetcard.getByText("seats").innerText();
        System.out.println("Before Booking: "+ seatsText);
        //seats number before booking
        int seatsNumberBeforeBooking= Integer.parseInt(seatsText.split(" ")[0]);
        targetcard.getByTestId("book-now-btn").click();

        //==============================================================================================================

        //====STEP 3====BOOKING TICKETS FOR EVENT ======//

        page.getByText("Full Name").fill("karthik");
        page.getByText("Email").fill("karthiikb0229@gmail.com");
        page.getByPlaceholder("+91 98765 43210").fill("9361501403");
        page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Confirm Booking")).click();

        // validating the the booking is confirmed
        assertThat(page.getByText("Your tickets are reserved.")).isVisible();

        // getting the booking refrence and storing into a single variable
        String bookingRef=  page.locator(".booking-ref ").innerText();
        page.getByRole(AriaRole.LINK,new Page.GetByRoleOptions().setName("View My Bookings")).click();

        //verifying in booking history
       Locator bookingcard= page.locator("#booking-card");  //loc1,loc2,loc3
        Locator targertBookingcard=bookingcard.filter(new Locator.FilterOptions().setHasText(bookingRef));
        assertThat(targertBookingcard).isVisible();

//=====================================================================================================================

        //Step 3=========== seat count reducation check================================

        page.locator("#nav-events").click();
        page.waitForTimeout(1000);

        Locator eventCardsAfterBookings = page.locator("#event-card"); // matching locators with event card will be returned
        page.waitForTimeout(3000);

        System.out.println("the total event cards are:" + eventCards.count()); // dispalys the how many cards are visible

        Locator targetcardsAfterBookings = eventCardsAfterBookings.filter(
                new Locator.FilterOptions().setHasText("Qa automation summit"));

        String seatsTextafterBooking=targetcardsAfterBookings.getByText("seats").innerText();
        System.out.println("After booking: "+seatsTextafterBooking);

        //afterbookings <BeforeBookings (before booking code is written in 106)

        //converting string into integern using Integer.pareseInt(String)
        //seat number after booking
         int seatsNumberABooking= Integer.parseInt(seatsTextafterBooking.split(" ")[0]);
         Assert.assertTrue(seatsNumberBeforeBooking>seatsNumberABooking);

    }

}