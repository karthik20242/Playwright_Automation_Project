import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class Practice {
Playwright playwright;
Browser browser;
Page page;
Locator locator;


    @BeforeMethod
    public void setup(){
        playwright=Playwright.create();
        browser= playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
       page= browser.newPage();
       page.setDefaultTimeout(5000);
       page.navigate("https://www.saucedemo.com/");

    }
    @Test(description = "testing the login page")
    public void loginpage(){
        page.getByPlaceholder("Username").fill("standard_user");
       page.getByPlaceholder("Password").fill("secret_sauce");
       page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Login")).click();
       assertThat(page).hasURL("https://www.saucedemo.com/inventory.html");

       page.locator(".product_sort_container").selectOption("Price (low to high)");  //drop down
       Locator products=page.locator(".inventory_item"); // storing all the products to product  variable
       Locator targetproduct=products.filter(new Locator.FilterOptions().setHasText("Test.allTheThings() T-Shirt (Red)")); //filtering the product to take specific product

        targetproduct.getByRole(AriaRole.BUTTON,new Locator.GetByRoleOptions().setName("Add to cart")).click(); // accessing the targeted product
        page.locator(".shopping_cart_link").click();
        page.locator("#checkout").click();
        page.getByPlaceholder("First Name").fill("karthik");
        page.getByPlaceholder("Last Name").fill("B");
        page.getByPlaceholder("Zip/Postal Code").fill("632104");
        page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Continue")).click();
        page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Finish")).click();
        assertThat(page.getByText("Thank you for your order!")).isVisible();  // checking the  text visible in page or not
        page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Back Home")).click();
        page.waitForTimeout(5000);


    }

    }

