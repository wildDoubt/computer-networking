package Assignment1;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Duration;
import java.time.Instant;

class Parent2 {
    int i = 7;

    public int get() {
        return i;
    }
}

class Child2 extends Parent2 {
    int i = 5;

    public int get() {
        return i;
    }
}

class Parent {
    public String read() {
        return "This is Parent";
    }
}

class FirstChild extends Parent {
    public String read() {
        return super.read() + " + firstChild";
    }
}

class SecondChild extends Parent {
    public String read() {
        return super.read() + " + secondChild";
    }
}

class ThirdChild extends Parent {
    Parent parent;

    public ThirdChild(Parent parent) {
        this.parent = parent;
    }

    public String read() {
        return parent.read() + " + thirdChild";
    }
}

public class Assignment1 {
    static int COUNT = 3000;
    interface Func {
        void func();
    }

    static class TestService {
        public long getElapsedTime(Func f) {
            Instant start = Instant.now();
            f.func();
            Instant end = Instant.now();
            return Duration.between(start, end).toMillis();
        }
    }

    public static void generateCharacterArray(OutputStream out) throws IOException {
        int firstPrintableCharacter = 33;
        int numberOfPrintableCharacters = 94;
        int numberOfCharactersPerLine = 72;
        int start = firstPrintableCharacter;
        int count = 0;
        int size = numberOfCharactersPerLine + 2;
        byte[] bytes = new byte[size];

        while (count < COUNT) {
            for (int i = start; i < start + numberOfCharactersPerLine; i++) {
                bytes[i - start] = (byte) ((i - firstPrintableCharacter) % numberOfPrintableCharacters + firstPrintableCharacter);
            }
            bytes[size - 2] = '\r';
            bytes[size - 1] = '\n';
            out.write(bytes);
            start = (start + 1 - firstPrintableCharacter) % numberOfPrintableCharacters + firstPrintableCharacter;
            count++;
        }
    }

    public static void generateCharacters(OutputStream out) throws IOException {
        int firstPrintableCharacter = 33;
        int numberOfPrintableCharacters = 94;
        int numberOfCharactersPerLine = 72;
        int start = firstPrintableCharacter;
        int count = 0;

        while (count < COUNT) {
            for (int i = start; i < start + numberOfCharactersPerLine; i++) {
                out.write(((i - firstPrintableCharacter) % numberOfPrintableCharacters + firstPrintableCharacter));
            }
            out.write('\r');
            out.write('\n');
            start = (start + 1 - firstPrintableCharacter) % numberOfPrintableCharacters + firstPrintableCharacter;
            count++;
        }
    }

    public static void print(Parent2 parent2) {
        System.out.println(parent2.i);
        System.out.println(parent2.get());
        System.out.println();
    }

    public static void InheritanceTest() {
        FirstChild firstChild = new FirstChild();
        System.out.println(firstChild.read());

        SecondChild secondChild = new SecondChild();
        System.out.println(secondChild.read());

        ThirdChild thirdChild1 = new ThirdChild(firstChild);
        System.out.println(thirdChild1.read());

        ThirdChild thirdChild2 = new ThirdChild(secondChild);
        System.out.println(thirdChild2.read());
    }

    public static void ChildTest() {
        Parent2 p = new Parent2();
        System.out.println(p.i);
        System.out.println(p.get());
        System.out.println();

        Child2 c = new Child2();
        System.out.println(c.i);
        System.out.println(c.get());
        System.out.println();

        Parent2 p1 = new Child2();
        System.out.println(p1.i);
        System.out.println(p1.get());
        System.out.println();

        print(c);
        print(p1);
    }

    public static void OutputStreamWriteTest() {
        for (int i = 0; i < 200; i++) {
            System.out.write(i);
            System.out.println(i);
            System.out.flush();
        }
    }

    public static void GenerateCharactersSingleByte() {
        try {
            generateCharacters(System.out);
        } catch (IOException ioException) {

        }
    }

    public static void GenerateCharacterByteArray() {
        try {
            generateCharacterArray(System.out);
        } catch (IOException ioException) {

        }
    }

    public static void main(String[] args) {
        TestService testService = new TestService();

        System.out.println("1. Assignment1.InheritanceTest");
        InheritanceTest();

        System.out.println("2. Assignment1.ChildTest");
        ChildTest();

        System.out.println("3. Assignment1.OutputStreamWriteTest");
        OutputStreamWriteTest();

        System.out.println("4. Assignment1.GenerateCharactersSingleByte");
        long elapsedTime1 = testService.getElapsedTime(() -> {
            try {
                generateCharacters(System.out);
            } catch (IOException ignored) {
            }
        });

        System.out.println("5. Assignment1.GenerateCharacterByteArray");
        long elapsedTime2 = testService.getElapsedTime(() -> {
            try {
                generateCharacterArray(System.out);
            } catch (IOException ignored) {
            }
        });

        System.out.println("SingleByte on System.out        : " + elapsedTime1 + "ms");
        System.out.println("ByteArray on System.out         : " + elapsedTime2 + "ms");

        long elapsedTime3 = testService.getElapsedTime(() -> {
            try (OutputStream outputStream = new FileOutputStream("testSingleByte.txt")) {
                generateCharacters(outputStream);
            } catch (IOException ignored) {
            }
        });

        long elapsedTime4 = testService.getElapsedTime(() -> {
            try (OutputStream outputStream = new FileOutputStream("testArray.txt")) {
                generateCharacterArray(outputStream);
            } catch (IOException ignored) {
            }
        });

        System.out.println("SingleByte on FileOutputStream  : " + elapsedTime3 + "ms");
        System.out.println("ByteArray on FileOutputStream   : " + elapsedTime4 + "ms");
    }
}
