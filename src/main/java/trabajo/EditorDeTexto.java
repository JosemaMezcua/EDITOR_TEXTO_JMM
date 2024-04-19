package trabajo;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

public class EditorDeTexto extends JFrame {
    //Atributos
    private JTextPane areaTexto;
    private JButton botonCursiva;
    private JButton botonNegrita;
    private JButton botonSubrayado;
    private JButton botonColor;
    private JButton botonGuardar;
    private JComboBox<String> comboFuentes;
    private JComboBox<String> comboTamanios;

    public EditorDeTexto() {
        super("Editor de Texto"); // Establece el título de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra la aplicación al cerrar la ventana
        setSize(900, 700); // Establece el tamaño de la ventana
        setLocationRelativeTo(null); // Centra la ventana en la pantalla

        // Crear JTextPane para el texto con JscrollPane
        areaTexto = new JTextPane(); // Crea un JTextPane para editar texto
        areaTexto.setFont(new Font("Arial", Font.PLAIN, 14)); // Establece la fuente inicial del texto
        JScrollPane scrollPane = new JScrollPane(areaTexto); // Crea un JScrollPane
        add(scrollPane, BorderLayout.CENTER); // Agrega el JTextPane al centro de la ventana

        // Crear panel para los controles
        JPanel panelControles = new JPanel(); // Crea un panel que contenga los botones
        panelControles.setLayout(new FlowLayout()); // Establece el diseño del panel como FlowLayout

        // Botones de estilos
        botonCursiva = new JButton("Cursiva"); // Crea un botón para aplicar o quitar cursiva
        botonCursiva.addActionListener(e -> alternarEstilo("italic"));// Escucha los clics en el botón y aplica cursiva
        panelControles.add(botonCursiva);   // Agrega el botón de cursiva al panel

        botonNegrita = new JButton("Negrita"); // Crea un botón para aplicar o quitar negrita
        botonNegrita.addActionListener(e -> alternarEstilo("bold")); // Escucha los clics en el botón y aplica negrita
        panelControles.add(botonNegrita); // Agrega el botón de negrita al panel

        botonSubrayado = new JButton("Subrayado"); // Crea un botón para aplicar o quitar subrayado
        botonSubrayado.addActionListener(e -> alternarEstilo("underline")); // Escucha los clics en el botón y aplica subrayado
        panelControles.add(botonSubrayado); // Agrega el botón de subrayado al panel

        // Combo box de fuentes
        comboFuentes = new JComboBox<>(); //Creamos un nuevo JcomboBox
        comboFuentes.addItem("Arial");
        comboFuentes.addItem("Verdana");             // Añadimos las fuentes
        comboFuentes.addItem("Times New Roman");
        comboFuentes.addItem("Georgia");
        comboFuentes.addItem("Courier New");
        comboFuentes.addItem("Tahoma");
        comboFuentes.addItem("Helvetica");
        comboFuentes.addItem("Impact");
        comboFuentes.addItem("Comic Sans MS");
        comboFuentes.addActionListener(e -> areaTexto.setFont(new Font((String) comboFuentes.getSelectedItem(), areaTexto.getFont().getStyle(), areaTexto.getFont().getSize()))); // Escucha los cambios en la selección de la fuente y actualiza la fuente del JTextPane
        panelControles.add(comboFuentes); // Agrega el combo box de fuentes al panel

        // Combo box de tamaños de letra
        // Crea un JComboBox con los tamaños de letra
        comboTamanios = new JComboBox<>(); // Crea un JComboBox con los tamaños de letra
        comboTamanios.addItem("8");
        comboTamanios.addItem("11");
        comboTamanios.addItem("14");
        comboTamanios.addItem("20");
        comboTamanios.addItem("24");
        comboTamanios.addItem("32");
        comboTamanios.addItem("72");
        comboTamanios.addActionListener(e -> areaTexto.setFont(areaTexto.getFont().deriveFont(Float.parseFloat((String) comboTamanios.getSelectedItem())))); // Escucha los cambios en la selección de tamaño y actualiza el tamaño de la fuente del JTextPane
        panelControles.add(comboTamanios); // Agrega el combo box de tamaños de letra al panel

        // Botón para cambiar color de texto
        botonColor = new JButton("Color"); // Crea un botón para cambiar el color de texto
        botonColor.addActionListener(e -> {
            Color color = JColorChooser.showDialog(this, "Selecciona un color", areaTexto.getForeground()); // Abre un JColorChooser para seleccionar un color
            if (color != null) { // Si se selecciona un color
                areaTexto.setForeground(color); // Actualiza el color de texto del JTextPane

            }

        });
            panelControles.add(botonColor); // Agrega el botón de color al panel

        // Botón para guardar
        botonGuardar = new JButton("Guardar"); // Crea un botón para guardar el documento
        botonGuardar.addActionListener(e -> guardarDocumento()); // Escucha los clics en el botón y llama al método guardarDocumento()
        panelControles.add(botonGuardar); // Agrega el botón de guardar al panel

        add(panelControles, BorderLayout.NORTH); // Agrega el panel de controles en la parte superior de la ventana
    }

    // Método para aplicar o quitar estilos (negrita, cursiva, subrayado)
    private void alternarEstilo(String nombreEstilo) {
        StyledDocument doc = areaTexto.getStyledDocument(); // Obtiene el documento de estilo del JTextPane
        int inicioSeleccion = areaTexto.getSelectionStart(); // Obtiene el inicio de la selección
        int finSeleccion = areaTexto.getSelectionEnd(); // Obtiene el fin de la selección
        MutableAttributeSet attrs = new SimpleAttributeSet(); // Crea un conjunto de atributos para aplicar estilos
        switch (nombreEstilo) {
            case "italic":
                StyleConstants.setItalic(attrs, !esEstiloAplicado(doc, inicioSeleccion, finSeleccion, StyleConstants.Italic)); // Alterna la cursiva
                break;
            case "bold":
                StyleConstants.setBold(attrs, !esEstiloAplicado(doc, inicioSeleccion, finSeleccion, StyleConstants.Bold)); // Alterna la negrita
                break;
            case "underline":
                StyleConstants.setUnderline(attrs, !esEstiloAplicado(doc, inicioSeleccion, finSeleccion, StyleConstants.Underline)); // Alterna el subrayado
                break;
        }
        doc.setCharacterAttributes(inicioSeleccion, finSeleccion - inicioSeleccion, attrs, false); // Aplica los atributos al texto seleccionado
    }

    // Método para verificar si un estilo está aplicado en el texto seleccionado
    private boolean esEstiloAplicado(StyledDocument doc, int inicio, int fin, Object estilo) {
        AttributeSet attrs = doc.getCharacterElement(inicio).getAttributes(); // Obtiene los atributos del elemento de texto
        return attrs.containsAttribute(estilo, Boolean.TRUE); // Verifica si el estilo está presente en los atributos
    }

    // Método para simular guardar el documento
    private void guardarDocumento() {
        // Simulamos el proceso de guardar mostrando un mensaje
        JOptionPane.showMessageDialog(this, "Documento guardado correctamente", "Guardar", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
            EditorDeTexto editor = new EditorDeTexto(); // Crea una instancia del editor de texto
            editor.setVisible(true); // Hace visible la ventana del editor
        
    }
}

