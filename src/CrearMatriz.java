
import java.util.Random;

// Clase encargada de generar la matriz bidimensional de cifrado a partir de un alfabeto base
public class CrearMatriz {

    // Alfabeto base con letras (incluyendo Ñ/ñ), números y signos de puntuación comunes
    private static final char[] ALPHABET = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'Ñ',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'ñ',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        '(', ')', '%', ',', '.', '-', ';', ':', '_', '?', '¿', '¡', '!', 'ª', 'º', '@', '$', '&', '/', '+', '*', '{', '}', '[', ']', '=', ' '
    };
    
    // Semilla secreta para inicializar el generador aleatorio y asegurar que la matriz sea la misma al encriptar y desencriptar
    private static final long SECRET_SEED = 123456789L;

    // Crea y devuelve una matriz cuadrada donde cada fila es una permutación del alfabeto base
    public char[][] empezarMatriz() {
        int n = ALPHABET.length;
        char[][] matrix = new char[n][n];

        // La primera fila contiene el alfabeto sin alterar
        matrix[0] = ALPHABET.clone();
        
        // Inicializamos el generador con la semilla secreta compartida
        Random random = new Random(SECRET_SEED);

        // Generamos las filas restantes mezclando el alfabeto para cada una
        for (int i = 1; i < n; i++) {
            char[] shuffledRow = ALPHABET.clone();
            
            // Algoritmo de Fisher-Yates para barajar el array de forma aleatoria
            for (int j = shuffledRow.length - 1; j > 0; j--) {
                int randomIndex = random.nextInt(j + 1);
                char temp = shuffledRow[randomIndex];
                shuffledRow[randomIndex] = shuffledRow[j];
                shuffledRow[j] = temp;
            }
            
            // Asignamos la fila mezclada a la matriz
            matrix[i] = shuffledRow;
        }

        return matrix;
    }
}