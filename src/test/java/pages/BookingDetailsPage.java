package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class BookingDetailsPage {
    private final Page page;

    public BookingDetailsPage(Page page) {
        this.page = page;
    }

    public void verifyBookingDetailsPage() {
        assertThat(page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setName("Event Details"))).isVisible();
        assertThat(page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Cancel Booking"))).isVisible();
    }

    public void cancelBooking() {
        page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Cancel Booking")).click();
        assertThat(page.getByRole(AriaRole.DIALOG)).isVisible();
        page.getByTestId("confirm-dialog-yes").click();
        assertThat(page.getByText("Booking cancelled successfully")).isVisible();
    }
}
