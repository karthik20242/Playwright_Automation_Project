import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class ps {
        public void dothis(){
            System.out.println("iam here");
        }
    @BeforeMethod
    public void beforeRun(){
        System.out.println("run this first");

    }
    @AfterMethod
    public void afterRun(){
        System.out.println("run this last ");
    }

}
