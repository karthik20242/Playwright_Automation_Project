package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class EventsPage {
    Page page;   //2


    public EventsPage(Page page){   //1

        this.page=page;

    }


    public void goTo(){
        page.locator("#nav-events").click();

    }

    public Locator waitforEventstoLoad(){
        Locator eventCards = page.locator("#event-card");
        // matching locators with event card will be returned
        assertThat(eventCards.first()).isVisible();
        // waiting for a single card to visible
        return eventCards;
    }


    public Locator findEventCard(String titlecard){


        Locator eventCards=waitforEventstoLoad();
        // calling the above method to find the card by using return keyword
        // and then filteration is done

       Locator targetCard= eventCards.filter(new Locator.FilterOptions().setHasText(titlecard));
       assertThat(targetCard).isVisible();
       return targetCard;

    }


    public int getSeatsCount(Locator targetcard){
        // making assertions to wait  to find the locator
       // assertThat(targetcard).isVisible();
        //accessing the seats from the specific card



        String seatsText=targetcard.getByText("seats").innerText();
        System.out.println(seatsText);

        //seats number before booking
        return Integer.parseInt(seatsText.split(" ")[0]);
    }




    public BookingFormPage proceedToBookingEvent(Locator targetcard){

        targetcard.getByTestId("book-now-btn").click();

        return new BookingFormPage(page);

    }
}
