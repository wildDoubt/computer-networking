package InheritanceTest;

class Parent{
    public String read(){
        return "This is Parent";
    }
}

class FirstChild extends Parent{
    public String read(){
        return super.read() + " + firstChild";
    }
}

class SecondChild extends Parent{
    public String read(){
        return super.read() + " + secondChild";
    }
}

class ThirdChild extends Parent{
    Parent parent;

    public ThirdChild(Parent parent){
        this.parent = parent;
    }

    public String read(){
        return parent.read() + " + thirdChild";
    }
}

public class InheritanceTest {
    public static void main(String[] args) {
        FirstChild firstChild = new FirstChild();
        System.out.println(firstChild.read());

        SecondChild secondChild = new SecondChild();
        System.out.println(secondChild.read());

        ThirdChild thirdChild1 = new ThirdChild(firstChild);
        System.out.println(thirdChild1.read());

        ThirdChild thirdChild2 = new ThirdChild(secondChild);
        System.out.println(thirdChild2.read());
    }
}
