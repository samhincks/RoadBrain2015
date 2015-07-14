import java.awt.Button;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
    
    JPanel buttonarea = new JPanel(new GridLayout(4, 5, 4, 4));

    Button b = new Button("Save");
  
    b.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              GRApplication.engine.write();
          }
      });
    buttonarea.add(b);  
    
    Button b2 = new Button("Heatmap");
  
    b2.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              GRApplication.engine.startTimer();
              GRApplication.engine.session = new SamSessionInformation("HeatmapSession");
              GRApplication.engine.close();
              engine.sendToEvent("Heatmap");
              GRApplication.engine.openUnity();
          }
      });
    buttonarea.add(b2);   
    
    Button b3 = new Button("Roadbrain");

    b3.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            //.. reset unity logs, change condition
             GRApplication.engine.cancelTimer();
             GRApplication.engine.session = new SamSessionInformation("RoadBrainSession");
             GRApplication.engine.close();
             engine.sendToEvent("Roadbrain");
             GRApplication.engine.openUnity();
        }
    });
    buttonarea.add(b3);
    
    AudioNBack nBack  = new AudioNBack(-1, 200000); //.. 5000 actually lasts 12 second
       
    Button b4 = new Button("0back");  
    b4.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            try{
                nBack.kBack = 0;
                Thread t = new Thread(nBack);
                t.start();
                engine.sendToEvent("ZeroBack");
            }
            catch(Exception ex) {ex.printStackTrace();}
        }  
    });
    buttonarea.add(b4);
    
    Button b6 = new Button("1back");
    b6.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            try {
                Thread t = new Thread(nBack);
                t.start();
                engine.sendToEvent("OneBack");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    });
    buttonarea.add(b6);
    
    Button b5 = new Button("EndBack");
    b5.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            nBack.interrupt(-1);   
            engine.sendToEvent("EndBack");

        }
    });
    buttonarea.add(b5);

    frame.add(buttonarea);
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
