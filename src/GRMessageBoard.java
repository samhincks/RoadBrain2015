import java.awt.Color;
import java.awt.Dimension;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

 
public class GRMessageBoard extends JPanel{

  private static final int WIDTH  = 400;
  private static final int HEIGHT = 800;

  private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy MM dd HH mm ss SSS");
  private static final String NEWLINE = "\n";

  private JTextArea textArea;

  public GRMessageBoard(String title){
    this.setBorder(BorderFactory.createTitledBorder(title));
    this.textArea = this.makeTextArea();
    JScrollPane scrollPane = this.makeScrollPaneFor(this.textArea); 
    this.add(scrollPane);
    
    //.. Detect when this thing closes, add Window listener
  }

  private JTextArea makeTextArea(){
    JTextArea textArea = new JTextArea();
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);
    textArea.setBackground(Color.BLACK);
    textArea.setForeground(Color.GREEN);
    textArea.setEditable(false);
    return textArea;
  }
  private JScrollPane makeScrollPaneFor(JTextArea textArea){
    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    return scrollPane;
  }

  public void put(String message){
    String now = DATE_FORMATTER.format(System.currentTimeMillis());
    this.textArea.append("[" + now + "]" + message + NEWLINE);
    this.textArea.setCaretPosition(this.textArea.getDocument().getLength());
  }
}
