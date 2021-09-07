package GenerateCharactersSingleByte;

import java.io.IOException;
import java.io.OutputStream;

public class GenerateCharactersSingleByte {
    public static void main(String[] args) {
        try {
            generateCharacters(System.out);
        } catch (IOException ioException) {

        }
    }

    public static void generateCharacters(OutputStream out) throws IOException {
        // [33, 127] 범위에서 순환하면서 72개씩 출력
        int firstPrintableCharacter = 33;
        int numberOfPrintableCharacters = 94;
        int numberOfCharactersPerLine = 72;
        int start = firstPrintableCharacter;
        int count = 0;

        while (count < 1000) {
            for (int i = start; i < start + numberOfCharactersPerLine; i++) {
                out.write(((i - firstPrintableCharacter) % numberOfPrintableCharacters + firstPrintableCharacter));
            }
            out.write('\r');
            out.write('\n');
            start = (start + 1 - firstPrintableCharacter) % numberOfPrintableCharacters + firstPrintableCharacter;
            count++;
        }
    }
}
