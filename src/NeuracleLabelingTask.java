import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class NeuracleLabelingTask implements Runnable{

  private static final String SERVER_IP = "127.0.0.1";
  private int port  = 1980;
  private static final int REST =1000;
  public   String currentCondition = "junk";
  public boolean NEW = true;
  
  public NeuracleLabelingTask(int port) {
      this.port = port;
  }

  @Override
  public void run(){
    try(
       Socket socket = new Socket(SERVER_IP, port);
       PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
    ){
    int index = 0;

    ///.. while there are no errors
    while(!out.checkError()){
        if (NEW) {
            System.out.println("sending " + currentCondition + " to " + port);
            out.println(currentCondition);
            NEW = false;
        }
        Thread.sleep(REST);
        index++;
    }
    }catch(Exception e) {
        System.err.println("Moving on with, life... We didn't manage to connect to " + port);
    }
  }

  public void setCurrentCondition(String condition) {
       this.currentCondition = condition;
       NEW = true;
  }

  public static void main(String[] args) throws InterruptedException{
     NeuracleLabelingTask n = new NeuracleLabelingTask(2212);
     new Thread(n).start();
     Thread.sleep(500);  
     n.setCurrentCondition("df");
    
  }
}
