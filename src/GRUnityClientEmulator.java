import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class GRUnityClientEmulator implements Runnable{

  private static final String SERVER_IP = "127.0.0.1";
  private static final int SERVER_PORT  = 1327;

  private static final int INFORMATION_DELIVERY_INTERVAL_IN_MILLISEC = 1000; //suppose data is sent one per 4 [sec]
  private static final int CONDITION_CHEANGE_COUNT_MAX               = 20;

  private static final String[] TASK_CONDITIONS = {"easy", "hard", "adaptive"};
  private static final String[] ROAD_IDS = {"A", "B", "C"};//, "D","E","F","G","H", "I","J","K"} ;
 
  @Override
  public void run(){
      System.out.println("--- GRUnityClientEmulator --- connecting to Server IP " + SERVER_IP + ", Port " + SERVER_PORT);
      try(
         Socket socket = new Socket(SERVER_IP, SERVER_PORT);
         PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
      ){
      int count = 0;
      int taskConditionIndex = 0;
      int index = 0;
     
      ///.. while there are no errors
      while(/*!out.checkError()*/ index < 20){
        String condition = TASK_CONDITIONS[taskConditionIndex];
        String roadId = "A";//ROAD_IDS[index];
        String xPos = getFakeXPos(index);
        String zPos = getFakeZPos(index);
        String message = condition + ";" + roadId + ";" + xPos + ";" + zPos;
        message = "hard";
        out.println(message);
        System.out.println("--- GRUnityClientEmulator --- send: " + message);
        
        Thread.sleep(INFORMATION_DELIVERY_INTERVAL_IN_MILLISEC);
        if(++count >= CONDITION_CHEANGE_COUNT_MAX){
          taskConditionIndex = (taskConditionIndex + 1) % TASK_CONDITIONS.length;
          count = 0;
        }
        index++;
      }
      System.out.println("--- GRUnityClientEmulator --- done");
     }catch(UnknownHostException e){
        e.printStackTrace();
        System.out.println("--- GRUnityClientEmulator --- ERROR: Failed to create socket");
     }catch(IOException e){
        e.printStackTrace();
        System.out.println("--- GRUnityClientEmulator --- ERROR: Failed to get I/O for the socket");
    }catch(InterruptedException e){
      e.printStackTrace();
    }
  }

  public String getFakeXPos(int index) {
      return String.valueOf(50);
  }
  
  public String getFakeZPos(int index) {
      return String.valueOf(index *14 +50);
  }
  public static void main(String[] args){
    new Thread(new GRUnityClientEmulator()).start();
  }
}
