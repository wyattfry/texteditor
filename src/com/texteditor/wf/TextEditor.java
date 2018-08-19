package com.texteditor.wf;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TextEditor {

  private static JFrame frame;
  private static File textFile;
  private static JTextField field;
  private static JTextArea textArea;
  private static JButton writeButton;
  private static String originalContent;
  private static boolean unsavedChanges = false;

  public static void main(String[] args) {
    // TODO Add a JFileChooser as alternative way to open a file
    initiateGui();
    if (args.length == 1) {
      field.setText(args[0]);
      readFromFileToTextArea();
    }
    // TODO Keyboard shortcuts
    // TODO Open recent
    // TODO Menu structure
  }

  public static void initiateGui() {
    frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        handleClosing();
      }
    });

    JPanel northPanel = new JPanel();
    field = new JTextField(30);
    field.setText("/path/to/file");
    field.addActionListener(new ReadButtonListener());
    JButton readButton = new JButton("Open");
    readButton.addActionListener(new ReadButtonListener());
    writeButton = new JButton("Saved");
    writeButton.setEnabled(false);
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
      String line = "";
      while (fileScanner.hasNextLine()) {
        line = fileScanner.nextLine() + (fileScanner.hasNextLine() ? "\n" : "");
        textArea.append(line);
        originalContent += line;
      }
      fileScanner.close();
    } catch (FileNotFoundException e) {
      System.err.println("File '" + textFile + "' not found.");
      e.printStackTrace();
    }
    frame.setTitle(textFile.toString());
    textArea.requestFocus();
    textArea.getDocument().addDocumentListener(new TextAreaChangeListener());
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
    originalContent = textArea.getText();
    updateWriteButton();
  }

  public static boolean hasUnsavedChanges() {
    unsavedChanges = !originalContent.equals(textArea.getText());
    return unsavedChanges;
  }

  public static void updateWriteButton() {
    System.out.println("Unsaved changes: " + hasUnsavedChanges());
    writeButton.setEnabled(hasUnsavedChanges());
    writeButton.setText(hasUnsavedChanges() ? "Save" : "Saved");
  }

  public static void handleClosing() {
    if (hasUnsavedChanges()) {
      int result = showWarningMessage();
      switch (result) {
        case JOptionPane.YES_OPTION:
          writeTextAreaToFile();
          System.exit(0);
          break;
        case JOptionPane.NO_OPTION:
          System.exit(0);
          break;
        case JOptionPane.CANCEL_OPTION:
          break;
        default:
          break;
      }
    }
  }

  public static int showWarningMessage() {
    String[] labels = new String[] {"Yes", "No", "Cancel"};
    String defaultOption = labels[0];
    return JOptionPane.showOptionDialog(
        frame,
        "There are unsaved changes. Would you like to save?",
        "Warning",
        JOptionPane.YES_NO_CANCEL_OPTION,
        JOptionPane.WARNING_MESSAGE,
        null,
        labels,
        defaultOption);
  }

} // public class

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

class TextAreaChangeListener implements DocumentListener {

  @Override
  public void changedUpdate(DocumentEvent e) {
    TextEditor.updateWriteButton();
  }

  @Override
  public void removeUpdate(DocumentEvent e) {
    changedUpdate(e);
  }

  @Override
  public void insertUpdate(DocumentEvent e) {
    changedUpdate(e);
  }
}