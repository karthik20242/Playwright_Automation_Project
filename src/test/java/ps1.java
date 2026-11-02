import net.minidev.json.JSONUtil;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ps1 extends ps {

  @Test
    public void testrun(){

        ps2 obj=new ps2(3);  // parameterized constructor
        int a=3;
      System.out.println(obj.increment());
      System.out.println(obj.decrement());


     // System.out.println(obj3.multiplywithTwo());
      System.out.println(obj.multiplywithThree());

      dothis();
  }

}
