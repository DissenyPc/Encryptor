import java.util.Random;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Encryptor {

    private static final char[] ALPHABET = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'Ñ',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'ñ',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        '(', ')', '%', ',', '.', '-', ';', ':', '_', '?', '¿', '¡', '!', 'ª', 'º', '@', '$', '&', '/', '+', '*', '{', '}', '[', ']', '=', ' '
    };
    private static final long SECRET_SEED = 123456789L;
    private static final String FILE_NAME = "input.txt";

    public static char[][] getStaticMatrix() {
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

    public void execute() {
        char[][] matrix = getStaticMatrix();
        System.out.println("Matriz creada con exito con " + matrix.length + " filas y " + matrix[0].length + " columnas.");

        try {
            // Read the entire text file into a String
            String content = Files.readString(Path.of(FILE_NAME));
            
            System.out.println("Original text: " + content);

            // TODO: Encrypt 'content' character by character using 'matrix'
            Random rand = new Random();
            String textoCifrado = "";
            // Empezamos el bucle para recorrer cada caracter del contenido del archivo
            for ( int i = 0; i < content.length(); i++) {

                int fila = rand.nextInt(91);
                char caracter = content.charAt(i);
                int columna = -1;
                // Empezamos el bucle para buscar el carracter en el array de la matriz
                for (int col = 0; col < matrix[fila].length; col++) {
                    if (matrix[fila][col] == caracter) {
                        columna = col;
                        break;
                    }
                }
                if (columna != -1) { // Comprobamos si el caracter es codificable
                    int control = rand.nextInt(91); // digito de contron para par o impar

                    textoCifrado = comprobarTamañoNumero(control, textoCifrado);
                                    
                    if (control%2 == 0) {
                        textoCifrado = comprobarTamañoNumero(fila, textoCifrado);
                        textoCifrado = comprobarTamañoNumero(columna, textoCifrado);
                    } else {
                        textoCifrado = comprobarTamañoNumero(columna, textoCifrado);
                        textoCifrado = comprobarTamañoNumero(fila, textoCifrado);
                    }
            }

            }

        } catch (IOException e) {
            System.out.println("Error leyendo el archivo: " + e.getMessage());
        }
    }

    // Si el valor es menor a 10 se añade siempre un 0 delante para que siempre tenga dos digitos
    private String comprobarTamañoNumero(int numero, String textoCifrado) {
        // TODO Auto-generated method stub
        if (numero < 10) {
            textoCifrado += 0;
        }   
        textoCifrado += numero;    
        return textoCifrado;
    }
}
