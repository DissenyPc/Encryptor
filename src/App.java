import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class App extends JFrame {

    private JTextField txtEncryptInput;
    private JTextField txtEncryptOutput;
    private JTextField txtDecryptInput;
    private JTextField txtDecryptOutput;

    private JButton btnSelectEncryptInput;
    private JButton btnSelectEncryptOutput;
    private JButton btnSelectDecryptInput;
    private JButton btnSelectDecryptOutput;

    private JButton btnEncrypt;
    private JButton btnDecrypt;

    private JTextArea logArea;

    public App() {
        // Configuración básica de la ventana principal
        setTitle("Encriptador & Desencriptador por Matriz");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setMinimumSize(new Dimension(650, 450));
        setLocationRelativeTo(null);

        // Intentar usar el estilo visual del sistema operativo (Windows)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("No se pudo cargar el Look & Feel del sistema. Usando por defecto.");
        }

        // Layout y espaciados generales
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(245, 246, 248));
        setContentPane(mainPanel);

        // Título de la cabecera
        JLabel lblHeader = new JLabel("Encriptador & Desencriptador por Matriz", JLabel.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblHeader.setForeground(new Color(33, 37, 41));
        lblHeader.setBorder(new EmptyBorder(10, 0, 15, 0));
        mainPanel.add(lblHeader, BorderLayout.NORTH);

        // Panel de pestañas para Encriptar y Desencriptar
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        tabbedPane.addTab(" Encriptar ", crearPanelEncriptar());
        tabbedPane.addTab(" Desencriptar ", crearPanelDesencriptar());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Panel inferior para consola/mensajes de estado
        JPanel footerPanel = new JPanel(new BorderLayout(5, 5));
        footerPanel.setBackground(new Color(245, 246, 248));
        
        JLabel lblLog = new JLabel("Consola de Estado:");
        lblLog.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblLog.setForeground(new Color(108, 117, 125));
        footerPanel.add(lblLog, BorderLayout.NORTH);

        logArea = new JTextArea(4, 50);
        logArea.setEditable(false);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        logArea.setBackground(new Color(255, 255, 255));
        logArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        JScrollPane scrollPane = new JScrollPane(logArea);
        footerPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        log("Aplicación iniciada. Lista para procesar archivos.");
    }

    private JPanel crearPanelEncriptar() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Fila 0: Entrada
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.0;
        JLabel lblInput = new JLabel("Archivo a encriptar (txt):");
        lblInput.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(lblInput, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        txtEncryptInput = new JTextField();
        txtEncryptInput.setEditable(false);
        txtEncryptInput.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(txtEncryptInput, gbc);

        gbc.gridx = 2; gbc.weightx = 0.0;
        btnSelectEncryptInput = new JButton("Buscar...");
        btnSelectEncryptInput.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnSelectEncryptInput.addActionListener(this::seleccionarArchivoEntradaEncriptar);
        panel.add(btnSelectEncryptInput, gbc);

        // Fila 1: Salida
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
        JLabel lblOutput = new JLabel("Archivo cifrado de salida:");
        lblOutput.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(lblOutput, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        txtEncryptOutput = new JTextField();
        txtEncryptOutput.setEditable(false);
        txtEncryptOutput.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(txtEncryptOutput, gbc);

        gbc.gridx = 2; gbc.weightx = 0.0;
        btnSelectEncryptOutput = new JButton("Buscar...");
        btnSelectEncryptOutput.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnSelectEncryptOutput.addActionListener(this::seleccionarArchivoSalidaEncriptar);
        panel.add(btnSelectEncryptOutput, gbc);

        // Fila 2: Botón de acción
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.CENTER; gbc.fill = GridBagConstraints.NONE;
        btnEncrypt = new JButton("Comenzar Encriptación");
        btnEncrypt.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEncrypt.setFocusPainted(false);
        btnEncrypt.setPreferredSize(new Dimension(200, 40));
        btnEncrypt.addActionListener(this::ejecutarEncriptacion);
        panel.add(btnEncrypt, gbc);

        return panel;
    }

    private JPanel crearPanelDesencriptar() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Fila 0: Entrada
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.0;
        JLabel lblInput = new JLabel("Archivo a desencriptar:");
        lblInput.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(lblInput, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        txtDecryptInput = new JTextField();
        txtDecryptInput.setEditable(false);
        txtDecryptInput.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(txtDecryptInput, gbc);

        gbc.gridx = 2; gbc.weightx = 0.0;
        btnSelectDecryptInput = new JButton("Buscar...");
        btnSelectDecryptInput.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnSelectDecryptInput.addActionListener(this::seleccionarArchivoEntradaDesencriptar);
        panel.add(btnSelectDecryptInput, gbc);

        // Fila 1: Salida
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
        JLabel lblOutput = new JLabel("Archivo descifrado de salida:");
        lblOutput.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(lblOutput, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        txtDecryptOutput = new JTextField();
        txtDecryptOutput.setEditable(false);
        txtDecryptOutput.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(txtDecryptOutput, gbc);

        gbc.gridx = 2; gbc.weightx = 0.0;
        btnSelectDecryptOutput = new JButton("Buscar...");
        btnSelectDecryptOutput.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnSelectDecryptOutput.addActionListener(this::seleccionarArchivoSalidaDesencriptar);
        panel.add(btnSelectDecryptOutput, gbc);

        // Fila 2: Botón de acción
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.CENTER; gbc.fill = GridBagConstraints.NONE;
        btnDecrypt = new JButton("Comenzar Desencriptación");
        btnDecrypt.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDecrypt.setFocusPainted(false);
        btnDecrypt.setPreferredSize(new Dimension(220, 40));
        btnDecrypt.addActionListener(this::ejecutarDesencriptacion);
        panel.add(btnDecrypt, gbc);

        return panel;
    }

    private void seleccionarArchivoEntradaEncriptar(ActionEvent e) {
        JFileChooser chooser = new JFileChooser(new File("."));
        chooser.setDialogTitle("Seleccionar archivo de texto a encriptar");
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            txtEncryptInput.setText(selectedFile.getAbsolutePath());
            
            // Sugerir nombre de archivo de salida
            String parent = selectedFile.getParent();
            String name = selectedFile.getName();
            int dotIndex = name.lastIndexOf('.');
            String baseName = (dotIndex == -1) ? name : name.substring(0, dotIndex);
            txtEncryptOutput.setText(new File(parent, baseName + "_cifrado.txt").getAbsolutePath());
            
            log("Seleccionado archivo para encriptar: " + selectedFile.getName());
        }
    }

    private void seleccionarArchivoSalidaEncriptar(ActionEvent e) {
        JFileChooser chooser = new JFileChooser(new File("."));
        chooser.setDialogTitle("Seleccionar ruta para guardar archivo cifrado");
        int returnVal = chooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            txtEncryptOutput.setText(selectedFile.getAbsolutePath());
            log("Ruta de salida de encriptación: " + selectedFile.getName());
        }
    }

    private void seleccionarArchivoEntradaDesencriptar(ActionEvent e) {
        JFileChooser chooser = new JFileChooser(new File("."));
        chooser.setDialogTitle("Seleccionar archivo cifrado a desencriptar");
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            txtDecryptInput.setText(selectedFile.getAbsolutePath());
            
            // Sugerir nombre de archivo descifrado de salida
            String parent = selectedFile.getParent();
            String name = selectedFile.getName();
            int dotIndex = name.lastIndexOf('.');
            String baseName = (dotIndex == -1) ? name : name.substring(0, dotIndex);
            if (baseName.endsWith("_cifrado")) {
                baseName = baseName.substring(0, baseName.length() - 8);
            }
            txtDecryptOutput.setText(new File(parent, baseName + "_descifrado.txt").getAbsolutePath());
            
            log("Seleccionado archivo para desencriptar: " + selectedFile.getName());
        }
    }

    private void seleccionarArchivoSalidaDesencriptar(ActionEvent e) {
        JFileChooser chooser = new JFileChooser(new File("."));
        chooser.setDialogTitle("Seleccionar ruta para guardar archivo descifrado");
        int returnVal = chooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            txtDecryptOutput.setText(selectedFile.getAbsolutePath());
            log("Ruta de salida de desencriptación: " + selectedFile.getName());
        }
    }

    private void ejecutarEncriptacion(ActionEvent e) {
        String input = txtEncryptInput.getText();
        String output = txtEncryptOutput.getText();

        if (input.isEmpty() || output.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                    "Por favor, selecciona los archivos de entrada y salida primero.", 
                    "Archivos no seleccionados", 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        btnEncrypt.setEnabled(false);
        log("Cifrando archivo: " + input + " -> " + output);

        // Usar SwingWorker para no bloquear la interfaz gráfica
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                Encryptor encryptor = new Encryptor();
                encryptor.execute(input, output);
                return null;
            }

            @Override
            protected void done() {
                try {
                    get(); // Verificar si ocurrió alguna excepción
                    log("¡Encriptación completada con éxito!");
                    JOptionPane.showMessageDialog(App.this, 
                            "Archivo encriptado y guardado correctamente.", 
                            "Éxito", 
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    log("Error: " + ex.getCause().getMessage());
                    JOptionPane.showMessageDialog(App.this, 
                            "Ocurrió un error al encriptar:\n" + ex.getCause().getMessage(), 
                            "Error de encriptación", 
                            JOptionPane.ERROR_MESSAGE);
                } finally {
                    btnEncrypt.setEnabled(true);
                }
            }
        };
        worker.execute();
    }

    private void ejecutarDesencriptacion(ActionEvent e) {
        String input = txtDecryptInput.getText();
        String output = txtDecryptOutput.getText();

        if (input.isEmpty() || output.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                    "Por favor, selecciona los archivos de entrada y de salida primero.", 
                    "Archivos no seleccionados", 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        btnDecrypt.setEnabled(false);
        log("Descifrando archivo: " + input + " -> " + output);

        // Usar SwingWorker para no bloquear la interfaz gráfica
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                Decryptor decryptor = new Decryptor();
                decryptor.execute(input, output);
                return null;
            }

            @Override
            protected void done() {
                try {
                    get(); // Verificar si ocurrió alguna excepción
                    log("¡Desencriptación completada con éxito!");
                    JOptionPane.showMessageDialog(App.this, 
                            "Archivo desencriptado y guardado correctamente.", 
                            "Éxito", 
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    log("Error: " + ex.getCause().getMessage());
                    JOptionPane.showMessageDialog(App.this, 
                            "Ocurrió un error al desencriptar:\n" + ex.getCause().getMessage(), 
                            "Error de desencriptación", 
                            JOptionPane.ERROR_MESSAGE);
                } finally {
                    btnDecrypt.setEnabled(true);
                }
            }
        };
        worker.execute();
    }

    private void log(String message) {
        logArea.append("[" + java.time.LocalTime.now().toString().substring(0, 8) + "] " + message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    public static void main(String[] args) {
        // Lanzar la aplicación en el hilo de despacho de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.setVisible(true);
        });
    }
}
