package Assignment1.GenerateCharacterByteArray;

import java.io.IOException;
import java.io.OutputStream;

public class GenerateCharacterByteArray {
    public static void main(String[] args) {
        try {
            generateCharacterArray(System.out);
        } catch (IOException ioException) {

        }
    }
    public static void generateCharacterArray(OutputStream out) throws IOException {
        // [33, 127] 범위에서 순환하면서 72개씩 출력
        int firstPrintableCharacter = 33;
        int numberOfPrintableCharacters = 94;
        int numberOfCharactersPerLine = 72;
        int start = firstPrintableCharacter;
        int count = 0;
        int size = numberOfCharactersPerLine+2;
        byte[] bytes = new byte[size];
        while (count < 1000) {
            for (int i = start; i < start + numberOfCharactersPerLine; i++){
                bytes[i-start] = (byte)((i - firstPrintableCharacter) % numberOfPrintableCharacters + firstPrintableCharacter);
            }
            bytes[size-2] = '\r';
            bytes[size-1] = '\n';
            out.write(bytes);
            start = (start + 1 - firstPrintableCharacter) % numberOfPrintableCharacters + firstPrintableCharacter;
            count++;
        }
    }
}
