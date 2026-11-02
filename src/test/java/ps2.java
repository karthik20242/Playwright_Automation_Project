public class ps2 extends ps3 {
    int a;

    public ps2(int a) {
        super(a);// parent class constructor starts executing
        this.a=a;
    }

    public int increment(){
        a=a+1;
        return a;
    }



    public int decrement(){
        a=a-1;
        return a;
    }
}
