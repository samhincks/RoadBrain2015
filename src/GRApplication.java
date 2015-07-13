import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


//Open 3 sockets for:
// 1) receiving data from Python data relay server (brain data from MATLAB)
// 2) receiving data from Desktop application (Unity)
// 3) sending data to Google Glass application


//Order of app invocations
// 1) Python data relay server
// 2) this app
// 3) Google Glass app
// 4) Unity app 

public class GRApplication{
  public static GREngine engine;
  private static void createAndShowGUI(String condition, boolean breakDown){
    GRMessageBoard brainMessageBoard = new GRMessageBoard("Python Relay Server");
    GRMessageBoard desktopAppMessageBoard = new GRMessageBoard("Unity");
    GRMessageBoard glassMessageBoard = new GRMessageBoard("Google Glass");
    engine = new GREngine(brainMessageBoard, desktopAppMessageBoard, glassMessageBoard);
    
    
    JFrame frame = new JFrame("RoadBrain");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
    frame.add(brainMessageBoard);
    frame.add(desktopAppMessageBoard);
    frame.add(glassMessageBoard);
    Button b = new Button("write");
  
    b.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              GRApplication.engine.write();
          }
      });
    frame.add(b);   

    frame.pack();
    frame.setVisible(true);
    engine.condition = condition;
    engine.breakDown = breakDown;
    engine.start();
  }

  public static void main(String[] args){
      if(args.length == 1) {
          createAndShowGUI(args[0], false);
      }
      else {
        createAndShowGUI("a", false);
      }
   /* SwingUtilities.invokeLater(new Runnable(){
      public void run(){
        GRApplication.createAndShowGUI();
      }
    });*/ 
  }
}
