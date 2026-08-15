# Encriptador y Desencriptador por Matriz en Java

Esta aplicación es una herramienta de cifrado simétrico en Java que codifica y decodifica archivos de texto utilizando una matriz de sustitución dinámica y técnicas de ofuscación ("dígitos fantasmas").

## ¿Cómo funciona?

1. **Generación de la Matriz**: Se genera una matriz de 91x91 a partir de un alfabeto de 91 caracteres (letras, números y símbolos comunes) barajado aleatoriamente usando una semilla secreta compartida (`SECRET_SEED` en [CrearMatriz.java](file:///e:/Mis%20Documentos/Documentos/PROGRAMACION/Java%20encriptador/encryptor/src/CrearMatriz.java)).
2. **Proceso de Cifrado ([Encryptor.java](file:///e:/Mis%20Documentos/Documentos/PROGRAMACION/Java%20encriptador/encryptor/src/Encryptor.java))**:
   - Cada carácter de `input.txt` se busca en la matriz para obtener su posición de fila y columna.
   - Se genera un dígito de control aleatorio. Si el dígito es par, se guarda en el orden `[fila][columna]`; si es impar, se guarda como `[columna][fila]`.
   - Todos los números (control, fila y columna) se formatean a 2 dígitos (ej. `05` en lugar de `5`), formando un bloque de 6 dígitos por carácter.
   - De manera aleatoria, se insertan "dígitos fantasmas" (números entre 91 y 99) antes de los bloques reales para ofuscar el patrón del texto cifrado y despistar a posibles atacantes.
   - El resultado se guarda en `output.txt`.
3. **Proceso de Descifrado ([Decryptor.java](file:///e:/Mis%20Documentos/Documentos/PROGRAMACION/Java%20encriptador/encryptor/src/Decryptor.java))**:
   - Se lee el texto cifrado de `output.txt`.
   - Se identifican y saltan los dígitos fantasmas (cualquier par de dígitos superior a 90).
   - Se lee el bloque de 6 dígitos. El dígito de control determina el orden de las coordenadas para recuperar la fila y columna exactas en la matriz.
   - El texto descifrado se guarda en `decrypted.txt`.

---

# Java Matrix Encryptor & Decryptor

This application is a Java-based symmetric encryption tool that encodes and decodes text files using a dynamic substitution matrix and obfuscation techniques ("ghost digits").

## How it works

1. **Matrix Generation**: A 91x91 matrix is generated using a 91-character alphabet (letters, numbers, and common symbols) shuffled pseudo-randomly with a shared secret seed (`SECRET_SEED` in [CrearMatriz.java](file:///e:/Mis%20Documentos/Documentos/PROGRAMACION/Java%20encriptador/encryptor/src/CrearMatriz.java)).
2. **Encryption Process ([Encryptor.java](file:///e:/Mis%20Documentos/Documentos/PROGRAMACION/Java%20encriptador/encryptor/src/Encryptor.java))**:
   - Each character in `input.txt` is located within the matrix to determine its row and column coordinates.
   - A random control digit is generated. If it is even, the coordinates are appended in `[row][column]` order; if odd, they are appended in `[column][row]` order.
   - Every number (control, row, and column) is padded to 2 digits (e.g., `05` instead of `5`), creating a 6-digit block per character.
   - "Ghost digits" (numbers between 91 and 99) are randomly inserted before actual character blocks to obfuscate the ciphertext pattern and deter potential attackers.
   - The result is written to `output.txt`.
3. **Decryption Process ([Decryptor.java](file:///e:/Mis%20Documentos/Documentos/PROGRAMACION/Java%20encriptador/encryptor/src/Decryptor.java))**:
   - The encrypted text is read from `output.txt`.
   - The program scans for and skips any ghost digits (any pair of digits greater than 90).
   - The 6-digit block is read. The control digit dictates the coordinate order to retrieve the exact character from the matrix.
   - The decrypted text is saved to `decrypted.txt`.
