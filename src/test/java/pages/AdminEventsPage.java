package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AdminEventsPage {

    Page page;

    private static final String event_title = "#event-title-input";
    private static final String admineventname = "#admin-event-form textarea";
    private static final String event_catagory = "Category";
    private static final String city = "City";
    private static final String venue = "Venue";
    private static final String date_and_Time = "Event Date & Time";
    private static final String price = "Price ($)";
    private static final String seats = "Total Seats";
    private static final String add_event_btn = "#add-event-btn";
    private static final String finalToast = "Event created";

    public AdminEventsPage(Page page) {
        this.page = page;
    }

    public void goTo() {
        page.navigate("https://eventhub.rahulshettyacademy.com/admin/events");
    }

    public void createEvent(
            String title,
            String eventname,
            String catagory,
            String cityname,
            String venue_name,
            String dateTime,
            String cost,
            String seat) {

        page.locator(event_title).fill(title);

        page.locator(admineventname).fill(
                eventname,
                new Locator.FillOptions().setTimeout(1000)
        );

        page.getByLabel(event_catagory).selectOption(catagory);

        page.getByLabel(city).fill(cityname);
        page.getByLabel(venue).fill(venue_name);
        page.getByLabel(date_and_Time).fill(dateTime);
        page.getByLabel(price).fill(cost);
        page.getByLabel(seats).fill(seat);

        page.locator(add_event_btn).click(
                new Locator.ClickOptions().setTimeout(12000)
        );

        assertThat(page.getByText(finalToast)).isVisible();
    }
}