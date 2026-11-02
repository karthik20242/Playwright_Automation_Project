package Tests;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.BookingDetailsPage;
import pages.DashboardPage;
import pages.LoginPage;
import pages.MyBookingsPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class EventHubBookingPageObjectTest extends TestBase {

    @Test(groups = {"framework"}, description = "Cancel the first booking from its details page")
    public void cancelFirstBooking() {
        String email = requiredEnvironmentVariable("EVENTHUB_EMAIL");
        String password = requiredEnvironmentVariable("EVENTHUB_PASSWORD");

        LoginPage loginPage = new LoginPage(page, base_url);
        DashboardPage dashboardPage = loginPage.loginToApplication(email, password);
        dashboardPage.WaitForEventsToLoad();

        MyBookingsPage myBookingsPage = new MyBookingsPage(page);
        myBookingsPage.open();

        Locator firstBooking = myBookingsPage.getFirstBookingCard();
        String bookingReference = firstBooking.locator(".booking-ref").innerText();
        firstBooking.getByRole(AriaRole.BUTTON,
                new Locator.GetByRoleOptions().setName("View Details")).click();

        Assert.assertTrue(page.url().matches(".*/bookings/[^/?#]+$"),
                "Expected to be on a booking details URL, but was: " + page.url());
        BookingDetailsPage bookingDetailsPage = new BookingDetailsPage(page);
        bookingDetailsPage.verifyBookingDetailsPage();
        bookingDetailsPage.cancelBooking();

        assertThat(page).hasURL(base_url + "bookings");
        myBookingsPage.verifyBookingIsAbsent(bookingReference);
    }

    private String requiredEnvironmentVariable(String name) {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Set the " + name + " environment variable to run this test.");
        }
        return value;
    }
}
