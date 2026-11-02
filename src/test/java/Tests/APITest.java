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

public class APITest {
    @Test
    public void e2eApiTest() {
        //=========LOGIN================//
        // we use hashmap to store the json value properly because it stores like key value pair
        HashMap<Object,Object>loginpayload=new HashMap<>();// key ,value
        loginpayload.put("email","karthiikb0229@gmail.com");
        loginpayload.put("password","K513322104308@a");


        Playwright playwright = Playwright.create();
        // this is will return api context
       APIRequestContext apiRequest= playwright.request().newContext();
       APIResponse loginresponse=apiRequest.post("https://api.eventhub.rahulshettyacademy.com/api/auth/login",
               RequestOptions.create().setData(loginpayload));
        Assert.assertTrue(loginresponse.ok());
        // shows the json data in output
        //extracting the text from response
        System.out.println(loginresponse.text());


        String token=JsonPath.read(loginresponse.text(),"$.token");
        System.out.println("login sucess:"+token);


        //============CREATE EVENT=============
        String eventTitle="Playwright API Testing";
        HashMap<Object,Object>createEventPlayload=new HashMap<>();
        createEventPlayload.put("description", "api");
        createEventPlayload.put("title", eventTitle);
        createEventPlayload.put("description", "api");
        createEventPlayload.put("category", "Sports");
        createEventPlayload.put("venue", "5th venue");
        createEventPlayload.put("city", "newyork");
        createEventPlayload.put("eventDate", "2026-11-19T04:29:00.000Z");
        createEventPlayload.put("price", 100);
      createEventPlayload.put("totalSeats", 400);

        APIResponse eventresponse=apiRequest.post("https://api.eventhub.rahulshettyacademy.com/api/events",
                RequestOptions.create().setHeader("Authorization","Bearer "+token)
                        .setData(createEventPlayload));
        Assert.assertTrue(eventresponse.ok());
        int eventid=JsonPath.read(eventresponse.text(),"$.data.id");
        System.out.println("event id is created :"+eventid);

        //============GET EVET=======
       APIResponse retriveEvents= apiRequest.get("https://api.eventhub.rahulshettyacademy.com/api/events",
                RequestOptions.create().setQueryParam("page","1")
                        .setQueryParam("limit",12)
                        .setHeader("Authorization","Bearer "+token));
       Assert.assertTrue(retriveEvents.ok(),"Event retrival API successed");
        //System.out.println(retriveEvents.text());


        // returns in list of strings type
        //"$.data[*].id" returns all the id in the event
       List<Integer> allEventIds= JsonPath.read(retriveEvents.text(),"$.data[*].id");
        Assert.assertTrue(allEventIds.contains(eventid),"created event should appear in event list");

        //=======DELETE EVENT==========
        APIResponse deleteresonse=apiRequest.delete("https://api.eventhub.rahulshettyacademy.com/api/events"+eventid,
                RequestOptions.create().setHeader("Authorization","Bearer "+token));

        List<Integer> deletedEventIds= JsonPath.read(retriveEvents.text(),"$.data[*].id");
        Assert.assertTrue(deleteresonse.ok());



            // ======verify the event after deletion is not present in the get event ======
            // verify deletion is success.-> getevents and confirm that event id does not exits anymore

        APIResponse verifyResponse= apiRequest.get("https://api.eventhub.rahulshettyacademy.com/api/events",
                RequestOptions.create().setQueryParam("page","1")
                        .setQueryParam("limit",12)
                        .setHeader("Authorization","Bearer "+token));

        List<Integer> afterDeleteEventIds= JsonPath.read(retriveEvents.text(),"$.data[*].id");
        Assert.assertFalse(afterDeleteEventIds.contains(eventid),"eventid after delete");


    }
}
