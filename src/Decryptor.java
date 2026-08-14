import java.util.Random;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Decryptor {

    public void execute() {
        // Llamamos a la clase CrearMatriz para crear la matriz de cifrado
        CrearMatriz creador = new CrearMatriz();
        char[][] matrix = creador.empezarMatriz();
        System.out.println("Matriz creada con exito con " + matrix.length + " filas y " + matrix[0].length + " columnas.");
    }
}
