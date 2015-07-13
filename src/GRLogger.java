import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Vector;


public class GRLogger{

  private static final String DIRECTORY   = "./logs/";
  private static final String FILE_PREFIX = "GR_Log-";
  private static final String FILE_SUFFIX = ".csv";

  private File logFile;
  private long creattionTimeInMillesec;

  public GRLogger(){
    this.createLogDirectory();
    this.logFile = this.createLogFile();
    this.write(new GRLog(GRLog.TYPE_CREATION_TIME, this.creattionTimeInMillesec, "File created"));
  }

  private void createLogDirectory(){
    File logDirectory = new File(DIRECTORY);
    if(logDirectory.exists()){
      if(!logDirectory.isDirectory()){
System.out.println(logDirectory + " exists but not directory...");
System.out.println("System closing...");
System.exit(-1);
      }
    }else{
      boolean isSucceeded = logDirectory.mkdir();
      if(!isSucceeded){
System.out.println("Failed to create log file...");
System.out.println("System closing...");
System.exit(-1);
      }
    }
  }
  private File createLogFile(){
      System.err.println("creating file");
    this.creattionTimeInMillesec = System.currentTimeMillis();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
    String creationDate = dateFormat.format(System.currentTimeMillis());
    String path = DIRECTORY;
    String name = FILE_PREFIX + creationDate + FILE_SUFFIX;
    File file = new File(path + name);
    try{
      if(!file.createNewFile()){
System.out.println("Failed to create log file...");
System.out.println("System closing...");
System.exit(-1);
      }
    }catch(IOException e){
      e.printStackTrace();
System.exit(-1);
    }
    return file;
  }

  private void write(GRLog log){
      System.err.println("writing");
    try(
      BufferedWriter bWriter = new BufferedWriter(new FileWriter(this.logFile, true));
    ){
      bWriter.write(log.toString());
      bWriter.newLine();
    }catch(IOException e){
      e.printStackTrace();
    }
  }
  public void write(Vector<GRLog> logs){
    try(
      BufferedWriter bWriter = new BufferedWriter(new FileWriter(this.logFile, true));
    ){
      for(int i = 0; i < logs.size(); i++){
        bWriter.write(logs.get(i).toString());
        bWriter.newLine();
      }
    }catch(IOException e){
      e.printStackTrace();
    }
  }
}
