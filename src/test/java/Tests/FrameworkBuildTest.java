package Tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.options.AriaRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class FrameworkBuildTest extends TestBase {


    @Test(groups = {"framework"}, description = "create event -book that event and verify if its booked")
    public void  DemoTest(){

        String eventTitle="Java Developer Conference";

        LoginPage loginPage=new LoginPage(page,base_url);
       DashboardPage dashboardPage= loginPage.loginToApplication();
       //returning the dashboard page in login page .java

     //   DashboardPage dashboardPage=new DashboardPage(page);
        dashboardPage.WaitForEventsToLoad();
        AdminEventsPage adminEventsPage=new AdminEventsPage(page);
        adminEventsPage.goTo();
        adminEventsPage.createEvent(
                eventTitle,
                "Automation Testing Workshop",
                "Technology",
                "Chennai",
                "Taj Convention Centre",
                "2027-01-15T10:30",
                "250",
                "100"
        );

//===========================================================================================================



        // step 2- find newly created event in event page

        EventsPage eventspage=new EventsPage(page);
        eventspage.goTo();

       Locator targetcard= eventspage.findEventCard(eventTitle);
        int seatsNumberBeforeBooking=eventspage.getSeatsCount(targetcard);
        BookingFormPage bookingFormPage=eventspage.proceedToBookingEvent(targetcard);
        bookingFormPage.fillandConfirm(
                "Test Student",
                "test.student@gmail.com",
                "9943015486"
        );


        //==============================================================================================================

        //====STEP 3====BOOKING TICKETS FOR EVENT ======//














        // getting the booking refrence and storing into a single variable
        String bookingRef=  page.locator(".booking-ref ").innerText();
        page.getByRole(AriaRole.LINK,new Page.GetByRoleOptions().setName("View My Bookings")).click();

        //verifying in booking history
       Locator bookingcard= page.locator("#booking-card");  //loc1,loc2,loc3
        Locator targertBookingcard=bookingcard.filter(new Locator.FilterOptions().setHasText(bookingRef));
        assertThat(targertBookingcard).isVisible();













//=====================================================================================================================

//        //Step 3=========== seat count reducation check================================
//
//        page.locator("#nav-events").click();
//        page.waitForTimeout(1000);
//
//        Locator eventCardsAfterBookings = page.locator("#event-card"); // matching locators with event card will be returned
//        page.waitForTimeout(3000);
//
//        System.out.println("the total event cards are:" + eventCards.count()); // dispalys the how many cards are visible
//
//        Locator targetcardsAfterBookings = eventCardsAfterBookings.filter(
//                new Locator.FilterOptions().setHasText("Qa automation summit"));
//
//        String seatsTextafterBooking=targetcardsAfterBookings.getByText("seats").innerText();
//        System.out.println("After booking: "+seatsTextafterBooking);
//
//        //afterbookings <BeforeBookings (before booking code is written in 106)
//
//        //converting string into integern using Integer.pareseInt(String)
//        //seat number after booking
//         int seatsNumberABooking= Integer.parseInt(seatsTextafterBooking.split(" ")[0]);
//         Assert.assertTrue(seatsNumberBeforeBooking>seatsNumberABooking);

    }

}