
import java.util.Random;

public class CrearMatriz {

    private static final char[] ALPHABET = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'Ñ',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'ñ',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        '(', ')', '%', ',', '.', '-', ';', ':', '_', '?', '¿', '¡', '!', 'ª', 'º', '@', '$', '&', '/', '+', '*', '{', '}', '[', ']', '=', ' '
    };
    
    private static final long SECRET_SEED = 123456789L;

    public char[][] empezarMatriz() {
        int n = ALPHABET.length;
        char[][] matrix = new char[n][n];

        matrix[0] = ALPHABET.clone();
        Random random = new Random(SECRET_SEED);

        for (int i = 1; i < n; i++) {
            char[] shuffledRow = ALPHABET.clone();
            
            for (int j = shuffledRow.length - 1; j > 0; j--) {
                int randomIndex = random.nextInt(j + 1);
                char temp = shuffledRow[randomIndex];
                shuffledRow[randomIndex] = shuffledRow[j];
                shuffledRow[j] = temp;
            }
            
            matrix[i] = shuffledRow;
        }

        return matrix;
    }
}