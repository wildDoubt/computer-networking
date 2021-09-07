package Assignment2.SystemInReadTest;

public class SystemInReadTest {
    public static void main(String[] args) {
        System.out.println("Enter a Character");

        try {
            int inChar = System.in.read();
            System.out.write(inChar);
            System.out.println(inChar);
            System.out.flush();
        } catch (Exception ignored) {
        }
    }
}
