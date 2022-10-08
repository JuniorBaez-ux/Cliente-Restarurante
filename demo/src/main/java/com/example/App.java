package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import mdlaf.components.textarea.MaterialTextAreaUI;

/**
 * JavaFX App
 */
public class App {

    private static Scene scene;
    public static Socket cliente;
    public static int  port = 5000;
    public static String ip = "localhost";//"127.0.0.1"
    public static PrintStream salida;
    public static BufferedReader entrada, teclado;

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        createWindow();
        //launch();
    }

	static void createWindow(){
		try {


        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

		JFrame ventana = new JFrame( "Reservaciones para restaurante Pepetoño");
        ventana.setLayout(null);
        ventana.setSize(700, 800);
        ventana.setResizable(true);
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(3);
        //Make ventana max size by default
        
        
        //MaterialTextAreaUI() mta = new MaterialTextAreaUI();
        JTextArea area = new JTextArea();
        area.setBounds(10, 10,200,200);
        area.setVisible(true);
        area.setLineWrap(true);
        area.setEditable(false);
        ventana.add(area);

        JLabel label = new JLabel("Nombre: ");
        label.setBounds(10, 240, 90, 20);
        label.setVisible(true);
        ventana.add(label);

        JTextField nombreTextField = new JTextField();
        nombreTextField.setBounds(10, 260, 90, 20);
        nombreTextField.setVisible(true);
        ventana.add(nombreTextField);

        JLabel label2 = new JLabel("Cedula: ");
        label2.setBounds(10, 280, 90, 20);
        label2.setVisible(true);
        ventana.add(label2);

        JTextField cedulaTextField = new JTextField();
        cedulaTextField.setBounds(10, 300, 90, 20);
        cedulaTextField.setVisible(true);
        ventana.add(cedulaTextField);

        JLabel label3 = new JLabel("Fecha de reservacion: ");
        label3.setBounds(10, 320, 150, 20);
        label3.setVisible(true);
        ventana.add(label3);

        JTextField fechaTextField = new JTextField();
        fechaTextField.setBounds(10, 340, 90, 20);
        fechaTextField.setVisible(true);
        ventana.add(fechaTextField);

        String[] Horas = {"8:00 am", "9:00 am", "10:00 am", "11:00 am", "12:00 pm", "1:00 pm", "2:00 pm", "3:00 pm", "4:00 pm", "5:00 pm", "6:00 pm", "7:00 pm", "8:00 pm", "9:00 pm", "10:00 pm", "11:00 pm", "12:00 am"};
        JComboBox horaComboBox = new JComboBox(Horas);
        horaComboBox.setBounds(10, 360, 90, 20);
        horaComboBox.setVisible(true);
        ventana.add(horaComboBox);
        
        String[] Mesas = { "Mesa 1", "Mesa 2", "Mesa 3", "Mesa 4", "Mesa 5" };
        JComboBox  combo_box = new JComboBox (Mesas);
        combo_box.setBounds(10, 210, 90, 20);
        combo_box.setVisible(true);
        ventana.add(combo_box);
        
        JButton button = new JButton("Reservar");
        button.setBounds(10, 380, 90, 20);
        button.setVisible(true);
        ventana.add(button);

        // JButton button2 = new JButton("Traer lista de reservaciones");
        // button2.setBounds(10, 410, 200, 20);
        // button2.setVisible(true);
        // ventana.add(button2);
        	
        button.addActionListener(e -> {
            area.setText("");
            String reservacion = "@reservacionH@";
            String listadoMesas = "@reservacionG@";
            String nombre = nombreTextField.getText();
            String cedula = cedulaTextField.getText();
            String fecha = fechaTextField.getText();
            String mesa = combo_box.getSelectedItem().toString();
            String hora = horaComboBox.getSelectedItem().toString();
            
            try {
                cliente = new Socket(ip, port);
                entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                salida = new PrintStream(cliente.getOutputStream());
                String mensajeParaServer = reservacion + "," + nombre + "," + cedula + "," + fecha + ", " + mesa + "," + hora;
                salida.println(mensajeParaServer);
                String msg2 = entrada.readLine();
                JOptionPane.showMessageDialog(null, msg2);
                //area.setText(msg2);
            } catch (Exception i) {
                // TODO: handle exception
            }
            nombreTextField.setText("");
            cedulaTextField.setText("");
            fechaTextField.setText("");
            ventana.repaint();
        });
		} catch (Exception e) {
			System.out.print(e);
		}
		
	}

}