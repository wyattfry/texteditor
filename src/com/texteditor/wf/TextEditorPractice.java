package com.texteditor.wf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class TextEditorPractice {

  public static JTextArea text;

  public static void main(String[] args) {
//    goBorderLayout();
//    goFlowLayout();
//    sharpen();
//    goBoxLayout();
//    playing();
//    myTextArea();
    buttonAndText();

  }

  public static void buttonAndText() {
    JPanel panel = new JPanel();
    JButton button = new JButton("Append text to textarea");
    button.addActionListener(new MyListener());
    panel.add(button);

    text = new JTextArea(10,20);
    text.setLineWrap(true);

    JScrollPane scroller = new JScrollPane(text);
    scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    panel.add(scroller);

    defaultFrame(panel);
  }

  public static void myTextArea() {
    JPanel panel = new JPanel();

    JTextArea textarea = new JTextArea(10,20);
    JScrollPane scrollpane = new JScrollPane(textarea);
    textarea.setLineWrap(true);
    scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    panel.add(scrollpane);

    defaultFrame(panel);
  }

  public static void playing() {
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();

    JTextField field = new JTextField(20);
    panel.add(field);
    field.setText("Whatever");
    System.out.println("printing field's text:" + field.getText());
    field.addActionListener(new MyListener());
    frame.getContentPane().add(panel);

    frame.getContentPane().add(panel);
    frame.setSize(300,300);
    frame.setVisible(true);
  }

  public static void defaultFrame(JPanel p) {
    JFrame frame = new JFrame();
    frame.getContentPane().add(p);
    frame.setSize(300,300);
    frame.setVisible(true);
  }


  public static void goBoxLayout() {
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();

    panel.setBackground(Color.darkGray);
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.add(new JButton("one"));
    panel.add(new JButton("two"));

    frame.getContentPane().add(BorderLayout.EAST, panel);
//    frame.setContentPane(panel);
    frame.setSize(250,200);
    frame.setVisible(true);
  }

  public static void sharpen() {
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    panel.add(new JButton("one"));
    panel.add(new JButton("two"));
    panel.add(new JButton("three"));
    frame.getContentPane().add(panel);
    frame.setSize(300,300);
    frame.setVisible(true);
  }

  public static void goFlowLayout() {
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    panel.setBackground(Color.darkGray);
    panel.add(new JButton("click me"));
    panel.add(new JButton("click me too"));
    frame.getContentPane().add(BorderLayout.EAST, panel);
    frame.setSize(300,300);
    frame.setVisible(true);
  }


  public static void goBorderLayout() {
    JFrame frame = new JFrame();
    JButton eastButton = new JButton("east");
    JButton westButton = new JButton("west");
    JButton northButton = new JButton("north");
    Font bigFont = new Font("serif", Font.BOLD, 48);
    northButton.setFont(bigFont);

    JButton southButton = new JButton("south");
    JButton centerButton = new JButton("center");
    JButton centerButton2 = new JButton("center2");
    frame.getContentPane().add(BorderLayout.EAST, eastButton);
    frame.getContentPane().add(BorderLayout.WEST, westButton);
    frame.getContentPane().add(BorderLayout.NORTH, northButton);
    frame.getContentPane().add(BorderLayout.SOUTH, southButton);
    frame.getContentPane().add(BorderLayout.CENTER, centerButton);
    frame.getContentPane().add(BorderLayout.CENTER, centerButton2);

    Dimension dim = new Dimension(300, 300);
    frame.setSize(dim);
    frame.setVisible(true);
  }
}

class MyListener implements ActionListener {

  @Override
  public void actionPerformed(ActionEvent e) {
    TextEditorPractice.text.append("button clicked at " + e.getWhen() + "\n");
  }
}