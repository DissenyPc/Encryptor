
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Decryptor {

    private static final String FILE_NAME = "output.txt";
    private static final String OUTPUT_FILE = "decrypted.txt";

    public void execute() {
        // Llamamos a la clase CrearMatriz para crear la matriz de cifrado
        CrearMatriz creador = new CrearMatriz();
        char[][] matrix = creador.empezarMatriz();
        System.out.println("Matriz creada con exito con " + matrix.length + " filas y " + matrix[0].length + " columnas.");
    
    
        String textoDescifrado = "";
        try {
            // Read the entire text file into a String
            String content = Files.readString(Path.of(FILE_NAME));
            
            System.out.println("Texto encriptado: " + content);

            // empezamos a desencriptar
                      
            // Empezamos el bucle para recorrer cada caracter del contenido del archivo
            for ( int i = 0; i < content.length(); i += 6) {

                // extraemos 6 digitos para los tres numeros
                int control = Integer.parseInt(content.substring(i, i + 2));
                int num1 = Integer.parseInt(content.substring(i + 2, i + 4));
                int num2 = Integer.parseInt(content.substring(i + 4, i + 6));
                int row, col;

                if (control % 2 == 0) {
                    row = num1;
                    col = num2;
                } else {
                    row = num2;
                    col = num1;
                }
                
                // Add the decrypted character
                textoDescifrado += (matrix[row][col]);
            }
            System.out.println("Decrypted text: " + textoDescifrado.toString());

            // Save the original text to a new file
            Files.writeString(Path.of(OUTPUT_FILE), textoDescifrado.toString());
                

        } catch (IOException e) {
            System.out.println("Error leyendo el archivo: " + e.getMessage());
        }

        
        }
}
