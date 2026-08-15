import java.util.Random;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

// Clase encargada de encriptar el contenido de un archivo usando sustitución por matriz y dígitos de control y fantasmas
public class Encryptor {

    // Nombre del archivo de entrada que contiene el texto en texto plano
    private static final String FILE_NAME = "input.txt";

    // Método principal para ejecutar el proceso de cifrado
    public void execute() {
         // Llamamos a la clase CrearMatriz para crear la matriz de cifrado
        CrearMatriz creador = new CrearMatriz();
        char[][] matrix = creador.empezarMatriz();
        System.out.println("Matriz creada con exito con " + matrix.length + " filas y " + matrix[0].length + " columnas.");

        // Variable donde acumularemos la cadena de dígitos encriptados resultante
        String textoCifrado = "";
        try {
            // Read the entire text file into a String
            String content = Files.readString(Path.of(FILE_NAME));
            
            System.out.println("Texto original: " + content);

            // Empezamos a encriptar
            Random rand = new Random();
            
            // Empezamos el bucle para recorrer cada caracter del contenido del archivo
            for ( int i = 0; i < content.length(); i++) {
                // Generamos una fila aleatoria para iniciar la búsqueda en una de las permutaciones
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
                    // Insertamos de manera aleatoria un número fantasma entre 91 y 99 para ofuscar el cifrado
                    if (rand.nextInt(10)%2 == 0) {
                        textoCifrado += rand.nextInt(91, 100);
                    }
                    int control = rand.nextInt(91); // digito de contron para par o impar

                    // Damos formato de 2 dígitos al número de control
                    textoCifrado = comprobarTamañoNumero(control, textoCifrado);
                                    
                    // Si el número de control es par, guardamos en formato [fila][columna]
                    if (control%2 == 0) {
                        textoCifrado = comprobarTamañoNumero(fila, textoCifrado);
                        textoCifrado = comprobarTamañoNumero(columna, textoCifrado);
                    // Si es impar, guardamos en formato inversio [columna][fila]
                    } else {
                        textoCifrado = comprobarTamañoNumero(columna, textoCifrado);
                        textoCifrado = comprobarTamañoNumero(fila, textoCifrado);
                    }
                }
            }
            System.out.println("Texto cifrado: " + textoCifrado);

        } catch (IOException e) {
            System.out.println("Error leyendo el archivo: " + e.getMessage());
        }

        // Nombre del archivo de salida donde escribiremos el resultado cifrado
        String outputFileName = "output.txt";

        try {
            // Escribimos la cadena final cifrada en el archivo destino
            Files.writeString(Path.of(outputFileName), textoCifrado);
            System.out.println("File saved successfully as " + outputFileName);
        } catch (IOException e) {
            System.out.println("Error saving the file: " + e.getMessage());
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
