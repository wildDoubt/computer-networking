package Assignment9;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static int DEFAULT_PORT = 7;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT)) {
            System.out.println(DEFAULT_PORT + "번 포트에서 듣는 중");
            while (true) {
                try (Socket connection = serverSocket.accept()) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));

                    String requestMessage = bufferedReader.readLine();
                    System.out.println("클라이언트: " + requestMessage);


                    bufferedWriter.write(requestMessage + '\n');
                    bufferedWriter.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Server Error");
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
