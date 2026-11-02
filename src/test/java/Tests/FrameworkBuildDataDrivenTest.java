package Tests;

import Utils.DataProviderUtil;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;

import java.io.IOException;
import java.util.HashMap;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class FrameworkBuildDataDrivenTest extends TestBase {




    @DataProvider(name = "eventBookingData")
    public Object[][] eventBookingData() throws IOException {
        String filePath = "src/main/resources/eventBookingData.json";
        return DataProviderUtil.getJsonDataToMap(filePath);
    }





    @Test(groups = {"framework"},dataProvider = "eventBookingData" , description = "create event -book that event and verify if its booked")
    public void  DemoTest(HashMap<String,String>data){



        LoginPage loginPage=new LoginPage(page,base_url);
       DashboardPage dashboardPage= loginPage.loginToApplication();
       //returning the dashboard page in login page .java

     //   DashboardPage dashboardPage=new DashboardPage(page);
        dashboardPage.WaitForEventsToLoad();
        AdminEventsPage adminEventsPage=new AdminEventsPage(page);
        adminEventsPage.goTo();
        adminEventsPage.createEvent(
                data.get("titlePrefix"),
                data.get("description"),
                data.get("category"),
                data.get("city"),
                data.get("venue"),
                data.get("dateTime"),
                data.get("price"),
                data.get("totalSeats")


        );

//===========================================================================================================



        // step 2- find newly created event in event page

        EventsPage eventspage=new EventsPage(page);
        eventspage.goTo();

       Locator targetcard= eventspage.findEventCard(  data.get("titlePrefix"));
        int seatsNumberBeforeBooking=eventspage.getSeatsCount(targetcard);
        BookingFormPage bookingFormPage=eventspage.proceedToBookingEvent(targetcard);
        bookingFormPage.fillandConfirm(
                data.get("fullName"),
                data.get("email"),
                data.get("phone")
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



    }

}