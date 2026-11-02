package Tests;

import com.jayway.jsonpath.JsonPath;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;


public class TicketBookingAPI {
    @Test()
    public void TicketBooking() {
        //=========LOGIN================//
        // we use hashmap to store the json value properly because it stores like key value pair
        HashMap<Object,Object> loginpayload=new HashMap<>();// key ,value
        loginpayload.put("email","karthiikb0229@gmail.com");
        loginpayload.put("password","K513322104308@a");


        Playwright playwright = Playwright.create();
        // this is will return api context
        APIRequestContext apiRequest= playwright.request().newContext();
        APIResponse loginresponse = apiRequest.post(
                "https://api.eventhub.rahulshettyacademy.com/api/auth/login",
                RequestOptions.create().setData(loginpayload)
        );

        Assert.assertTrue(loginresponse.ok());
        // shows the json data in output
        //extracting the text from response
        System.out.println(loginresponse.text());


        String token= JsonPath.read(loginresponse.text(),"$.token");
        System.out.println("login sucess:"+token);

        HashMap<Object,Object>BookingPlayload=new HashMap<>();
        BookingPlayload.put("customerName", "Admin");
        BookingPlayload.put("customerEmail", "admin.eventhub@gmail.com");
        BookingPlayload.put("customerPhone", "9876501234");
        BookingPlayload.put("eventId", 21678);
        BookingPlayload.put("quantity", 1);
        APIResponse bookingdetails=apiRequest.post("https://api.eventhub.rahulshettyacademy.com/api/bookings",
                RequestOptions.create().setHeader("Authorization","Bearer "+token)
                        .setData(BookingPlayload));

        Assert.assertTrue(bookingdetails.ok());
       int bookingid= JsonPath.read(bookingdetails.text(),"$.data.id");
        System.out.println("Ticket booking id=====> "+bookingid);


        //=============GETTING TICKET BOOKING DETAILS======

       APIResponse gettingbookingDetails= apiRequest.get("https://api.eventhub.rahulshettyacademy.com/api/bookings",

               RequestOptions.create()
                       .setQueryParam("page",1)
                       .setQueryParam("limit",10)
                       .setHeader("Authorization","Bearer "+token) );
                Assert.assertTrue(gettingbookingDetails.ok(),"booking data is retrived");
                List<Integer> ticketbookingIds=JsonPath.read(gettingbookingDetails.text(),"$.data[*].id");
        System.out.println(ticketbookingIds);



        //====== deleting booked id ======

        APIResponse deleteresponseTicket = apiRequest.delete(
                "https://api.eventhub.rahulshettyacademy.com/api/bookings/" + bookingid,
                RequestOptions.create()
                        .setHeader("Authorization", "Bearer " + token)
        );

        Assert.assertTrue(
                deleteresponseTicket.ok(),
                "The booking ticket ID was not successfully deleted"
        );


//======== verifying deletion after deleted ========

        APIResponse deletionresponse = apiRequest.get(
                "https://api.eventhub.rahulshettyacademy.com/api/bookings",
                RequestOptions.create()
                        .setQueryParam("page", 1)
                        .setQueryParam("limit", 10)
                        .setHeader("Authorization", "Bearer " + token)
        );

        Assert.assertTrue(deletionresponse.ok(),
                "Could not retrieve bookings for verification");

        List<Integer> remainingBookingIds = JsonPath.read(
                deletionresponse.text(), "$.data[*].id"
        );

        Assert.assertFalse(
                remainingBookingIds.contains(bookingid),
                "Booking ID still exists: " + bookingid
        );


    }


}
