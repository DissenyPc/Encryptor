
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

// Clase encargada de descifrar el contenido cifrado utilizando la matriz reconstruida
public class Decryptor {

    // Nombre del archivo de entrada que contiene el texto cifrado
    private static final String FILE_NAME = "output.txt";
    // Nombre del archivo de salida que contendrá el texto descifrado
    private static final String OUTPUT_FILE = "decrypted.txt";

    // Método principal para ejecutar el proceso de descifrado con los archivos por defecto
    public void execute() {
        execute(FILE_NAME, OUTPUT_FILE);
    }

    // Sobrecarga del método execute que permite especificar los archivos de entrada y salida
    public void execute(String inputPath, String outputPath) {
        // Llamamos a la clase CrearMatriz para crear la matriz de cifrado
        CrearMatriz creador = new CrearMatriz();
        char[][] matrix = creador.empezarMatriz();
        System.out.println(
                "Matriz creada con exito con " + matrix.length + " filas y " + matrix[0].length + " columnas.");

        // Variable para acumular el texto descifrado
        String textoDescifrado = "";
        try {
            // Read the entire text file into a String
            String content = Files.readString(Path.of(inputPath));

            System.out.println("Texto encriptado: " + content);

            // empezamos a desencriptar

            // Empezamos el bucle para recorrer cada caracter del contenido del archivo
            // Avanzamos de 6 en 6 por defecto (tamaño de un bloque normal: control + num1 + num2)
            for (int i = 0; i < content.length(); i += 6) {

                // Si encontramos un bloque que empieza por un número mayor a 90, es un dígito fantasma
                if (Integer.parseInt(content.substring(i, i + 2)) > 90) {
                    // Saltamos los 2 dígitos fantasmas
                    i += 2;
                }
                
                // Extraemos las 3 partes del bloque de cifrado: control (2 dígitos), num1 (2 dígitos) y num2 (2 dígitos)
                int control = Integer.parseInt(content.substring(i, i + 2));
                int num1 = Integer.parseInt(content.substring(i + 2, i + 4));
                int num2 = Integer.parseInt(content.substring(i + 4, i + 6));
                int row, col;

                // El dígito de control nos indica el orden en el que se guardaron la fila y columna
                // Si es par, el orden es fila y columna
                if (control % 2 == 0) {
                    row = num1;
                    col = num2;
                // Si es impar, el orden es columna y fila
                } else {
                    row = num2;
                    col = num1;
                }

                // Add the decrypted character
                // Obtenemos el carácter original usando las coordenadas descifradas de la matriz
                textoDescifrado += (matrix[row][col]);

            }
            System.out.println("Decrypted text: " + textoDescifrado.toString());

            // Save the original text to a new file
            // Escribimos el resultado descifrado final en el archivo de salida
            Files.writeString(Path.of(outputPath), textoDescifrado.toString());

        } catch (IOException e) {
            System.out.println("Error leyendo el archivo: " + e.getMessage());
            throw new RuntimeException("Error al desencriptar el archivo: " + e.getMessage(), e);
        }

    }
}
