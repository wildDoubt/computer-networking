package Assignment1.OutputStreamWriteTest;

public class OutputStreamWriteTest {
    public static void main(String[] args) {
        for (int i = 0;i<200;i++){
            System.out.write(i); // 아스키 코드 출력
            System.out.println(i);
            System.out.flush();
        }
    }
}