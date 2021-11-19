package Assignment9;

import java.io.*;
import java.net.Socket;

public class EchoClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 7);

            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("서버에 보낼 메시지를 입력해주세요.");
            String message = input.readLine();

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write(message+'\n');
            bufferedWriter.flush();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("서버: "+bufferedReader.readLine());
        } catch (IOException ioException) {
            System.out.println("Client Error");
            System.out.println(ioException.toString());
        }
    }
}
