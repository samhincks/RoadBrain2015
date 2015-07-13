import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;


public class PositionLogger{

  private static final String DIRECTORY   = "./heatmaplogs/";
  private static final String FILE_PREFIX = "HM_";
  private static final String FILE_SUFFIX = ".csv";

  private File logFile;
  private ArrayList<String> messages;

  public PositionLogger(){
    this.createLogDirectory();
    messages = new ArrayList();
    this.logFile = this.createLogFile();
    messages.add("id,x,y,workload,time");
  }

  
  private void createLogDirectory(){
    File logDirectory = new File(DIRECTORY);
    if(logDirectory.exists()){
      if(!logDirectory.isDirectory()){
        System.out.println(logDirectory + " exists but not directory...");
        System.out.println("System closing...");
        System.exit(-1);
      }
    }
    
    else{
      boolean isSucceeded = logDirectory.mkdir();
      if(!isSucceeded){
        System.out.println("Failed to create log file...");
        System.out.println("System closing...");
        System.exit(-1);
      }
    }
  }
  private File createLogFile(){
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
    String creationDate = dateFormat.format(System.currentTimeMillis());
    String path = DIRECTORY;
    String name = FILE_PREFIX + creationDate + FILE_SUFFIX;
    File file = new File(path +name);
    System.out.println("writing " + path + name);
    return file;
  }

 /**Add a log message of the form fullmessage, x, y, workload, time**/
   public void addMessage(String logMessage, double workload) {
        String message = logMessage;
        String [] values = message.split(";");
        String id = values[0];
        String x = values[1];
        String y = values[2];
        
        //.. get time
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH-mm-ss");
        String creationDate = dateFormat.format(System.currentTimeMillis());
        
        message = id+","+x+","+y+","+workload+","+creationDate;
        messages.add(message);
   }
   
   public void writeThenClose() {
       System.err.println("Finishing off");
       try {
           BufferedWriter bWriter = new BufferedWriter(new FileWriter(this.logFile, true));

           for (String message : messages) {
                bWriter.write(message);
                bWriter.newLine();
           }
           bWriter.close();
       }
       catch(Exception e) {
           e.printStackTrace();
       }
   }
    
    public static void main(String[] args) {
        PositionLogger pl = new PositionLogger();
        pl.addMessage("fitta;2;2;2", 2);
        pl.writeThenClose();
    }
}
