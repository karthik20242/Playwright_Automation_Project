package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class DashboardPage {

    Page page;  // local variable
    public  DashboardPage(Page page){

        this.page=page;

    }


    public void WaitForEventsToLoad(){

        // validation the browser events lik visible after login
        assertThat(page.getByRole(AriaRole.LINK,
                new Page.GetByRoleOptions().setName("Browse Events →"))).isVisible();


    }




}
