
package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class BookingFormPage {

    Page page;

    private static final String FULL_NAME = "Full Name";
    private static final String EMAIL = "Email";
    private static final String PHONE = "+91 98765 43210";

    public BookingFormPage(Page page) {
        this.page = page;
    }

    public void fillandConfirm(String fullname, String email, String phone) {

        page.getByLabel(FULL_NAME).fill(fullname);
        page.getByLabel(EMAIL).fill(email);
        page.getByPlaceholder(PHONE).fill(phone);

        page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Confirm Booking")
        ).click();

        // Validating that the booking is confirmed
        assertThat(page.getByText("Your tickets are reserved.")).isVisible();
    }
}
