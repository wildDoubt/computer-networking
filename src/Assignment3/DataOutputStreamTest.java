package Assignment3;

import java.io.*;

public class DataOutputStreamTest {
    public static void main(String[] args) throws IOException {
        FileOutputStream fileOutputStream = null;
        DataOutputStream dataOutputStream = null;

        boolean addTab = false;

        fileOutputStream = new FileOutputStream("data.bin");
        dataOutputStream = new DataOutputStream(fileOutputStream);

        dataOutputStream.writeBoolean(false);
        if(addTab) dataOutputStream.writeChar('\n');

        dataOutputStream.writeByte(127);
        // writeByte는 1바이트를 끊어서 저장한다.
        // 256: 0001 0000 0000
        // 255: 0000 1111 1111
        // 158: 0000 1001 1110 -> MSB가 1이므로 2의 보수에 1을 더한 값에 -를 취한 음수가 나온다.
        if(addTab) dataOutputStream.writeChar('\n');

        dataOutputStream.writeInt(10);
        if(addTab) dataOutputStream.writeChar('\n');

        dataOutputStream.writeDouble(200.5);
        if(addTab) dataOutputStream.writeChar('\n');

        dataOutputStream.writeUTF("Hello World!");

        System.out.println("File saved");

        fileOutputStream.close();
        dataOutputStream.close();


        FileInputStream fileInputStream = null;
        DataInputStream dataInputStream = null;

        fileInputStream = new FileInputStream("data.bin");
        dataInputStream = new DataInputStream(fileInputStream);

        boolean boolVar = dataInputStream.readBoolean();
        if(addTab) dataInputStream.readChar();
        byte byteVar = dataInputStream.readByte();
        if(addTab) dataInputStream.readChar();
        int intVar = dataInputStream.readInt();
        if(addTab) dataInputStream.readChar();
        double doubleVar = dataInputStream.readDouble();
        if(addTab) dataInputStream.readChar();
        String stringVar = dataInputStream.readUTF();

        System.out.println(boolVar);
        System.out.println(byteVar);
        System.out.println(intVar);
        System.out.println(doubleVar);
        System.out.println(stringVar);

        fileInputStream.close();
        dataInputStream.close();
    }
}
