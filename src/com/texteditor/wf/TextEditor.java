package com.texteditor.wf;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class TextEditor {

  private static File textFile;
  private static JTextField field;
  private static JTextArea textArea;

  public static void main(String[] args) {
    // TODO Add a JFileChooser as alternative way to open a file
    initiateGui();
    if (args.length == 1) {
      field.setText(args[0]);
      readFromFileToTextArea();
    }
  }

  public static void initiateGui() {
    JFrame frame = new JFrame();

    JPanel northPanel = new JPanel();
    field = new JTextField(30);
    field.setText("/path/to/file");
    field.addActionListener(new ReadButtonListener());
    JButton readButton = new JButton("Read");
    readButton.addActionListener(new ReadButtonListener());
    JButton writeButton = new JButton("Write");
    writeButton.addActionListener(new WriteButtonListener());
    northPanel.add(field);
    northPanel.add(readButton);
    northPanel.add(writeButton);

    textArea = new JTextArea();
    Font monospaceFont = new Font("Monospaced", Font.PLAIN, 12);
    textArea.setFont(monospaceFont);
    JScrollPane scroller = new JScrollPane(textArea);
    scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

    frame.getContentPane().add(BorderLayout.NORTH, northPanel);
    frame.getContentPane().add(BorderLayout.CENTER, scroller);

    frame.setSize(650,400);
    frame.setVisible(true);
  }

  public static void readFromFileToTextArea() {
    // TODO Try to create file if trying to read a file that does not exist
    textFile = new File(field.getText());
    Scanner fileScanner;
    try {
      fileScanner = new Scanner(textFile);
      textArea.setText("");
      while (fileScanner.hasNextLine()) {
        textArea.append(fileScanner.nextLine() + "\n");
      }
      fileScanner.close();
      textArea.requestFocus();
    } catch (FileNotFoundException e) {
      System.err.println("File '" + textFile + "' not found.");
      e.printStackTrace();
    }
  }

  public static void writeTextAreaToFile() {
    // TODO Try to create file if trying to write a file that does not exist
    PrintWriter pwriter;
    try {
      pwriter = new PrintWriter(new FileWriter(textFile));
      pwriter.print(textArea.getText());
      pwriter.close();
    } catch (IOException e) {
      System.err.println("Could not open file '" + textFile + "' for writing.");
    }
    // TODO Notify user whether file wrote successfully
  }

}

class ReadButtonListener implements ActionListener {

  @Override
  public void actionPerformed(ActionEvent e) {
    TextEditor.readFromFileToTextArea();
  }
}

class WriteButtonListener implements ActionListener {

  @Override
  public void actionPerformed(ActionEvent e) {
    TextEditor.writeTextAreaToFile();
  }
}
