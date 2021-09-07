package ChildTest;

class Parent2{
    int i = 7;
    public int get(){
        return i;
    }
}

class Child2 extends Parent2{
    int i = 5;
    public int get(){
        return i;
    }
}

public class ChildTest {
    public static void print(Parent2 parent2){
        System.out.println(parent2.i);
        System.out.println(parent2.get());
        System.out.println();
    }
    public static void main(String[] args) {
        Parent2 p = new Parent2();
        System.out.println(p.i);
        System.out.println(p.get());
        System.out.println();

        Child2 c = new Child2();
        System.out.println(c.i);
        System.out.println(c.get());
        System.out.println();

        Parent2 p1 = new Child2();
        System.out.println(p1.i);       // parent의 i
        System.out.println(p1.get());   // override된 get
        System.out.println();

        // 마찬가지로 Parent2로 참조하기 때문에 위와 같은 결과가 나옴
        print(c);
        print(p1);

        // filed in superclass
        // method in subclass
    }
}
