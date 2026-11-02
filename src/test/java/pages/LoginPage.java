package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginPage {
    Page page;
    String base_url;
   private static final  String email_placeholder="Email";
   // private static final means nobody can modify this string
    // static means sharing the same memory everythime by following multiple copies
    //final means nobody can alter this by using inner method

   private static final  String password_placeholder="Password";

public LoginPage(Page page,String baseUrl){

    this.page=page;
    this.base_url=baseUrl;

}

    public DashboardPage loginToApplication(){
        return loginToApplication("karthiikb0229@gmail.com", "K513322104308@a");
    }

    public DashboardPage loginToApplication(String email, String password){
        page.navigate(base_url);
        System.out.println(page.title());
        assertThat(page).hasTitle("EventHub — Discover & Book Events");


        // locators


        page.getByLabel(email_placeholder).fill(email);
        page.getByLabel(password_placeholder).fill(password);
        page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Sign In")).click();

        // validation the browser events lik visible after login
        assertThat(page.getByRole(AriaRole.LINK,
                new Page.GetByRoleOptions().setName("Browse Events →"))).isVisible();
        DashboardPage dashboardPage=new DashboardPage(page);
        return dashboardPage;
    }



}
