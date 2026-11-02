package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class MyBookingsPage {
    private final Page page;

    public MyBookingsPage(Page page) {
        this.page = page;
    }

    public void open() {
        page.getByTestId("nav-bookings").click();
        assertThat(page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setName("My Bookings"))).isVisible();
    }

    public Locator getFirstBookingCard() {
        Locator firstBooking = page.getByTestId("booking-card").first();
        assertThat(firstBooking).isVisible();
        return firstBooking;
    }

    public void verifyBookingIsAbsent(String bookingReference) {
        Locator matchingBooking = page.getByTestId("booking-card")
                .filter(new Locator.FilterOptions().setHasText(bookingReference));
        assertThat(matchingBooking).hasCount(0);
    }
}
