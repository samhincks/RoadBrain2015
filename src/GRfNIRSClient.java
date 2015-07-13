import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class GRfNIRSClient implements Runnable{

  public interface OnMessageListener{
    public void onPredictionMessageReceived(String message);
  }
 
  private static  String SERVER_IP = "127.0.0.1";
  private static final int SERVER_PORT  = 50003;
  // ^ for local test
  // |
  // v for real experiment (fNIRS data relay server info)
  
 // private static final String SERVER_IP = "130.64.22.24";
 // private static final int SERVER_PORT  = 1001;
  
  private GRfNIRSClient.OnMessageListener onMessageListener;
  private GRMessageBoard messageBoard;
  private boolean isDone;
  private Socket socket;
  private BufferedReader in;

  public GRfNIRSClient(GRfNIRSClient.OnMessageListener onMessageListener, GRMessageBoard messageBoard){
    this.onMessageListener = onMessageListener;
    this.messageBoard = messageBoard;
    this.isDone = false;
      System.out.println("Inititalizing fNIRS client, listening to " + SERVER_PORT);
  }

  private boolean connect(){
    try{
      //SERVER_IP =  InetAddress.getLocalHost().getHostAddress() ;
      this.socket = new Socket(SERVER_IP, SERVER_PORT);
      this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
      this.messageBoard.put("--- GRfNIRSClient --- connected to the server (IP: " + SERVER_IP + ", PORT: " + SERVER_PORT + ")");
      return true;
    }catch(UnknownHostException e){
      this.messageBoard.put("--- GRfNIRSClient --- ERROR: Failed to create socket");
      return false;
    }catch(IOException e){
      this.messageBoard.put("--- GRfNIRSClient --- ERROR: Failed to get I/O for the socket");
      return false;
    }
  }

  public void disconnect(){
    try{
      this.isDone = true;
      if(this.in != null)
        this.in.close();
      if(this.socket != null)
        this.socket.close();
      this.messageBoard.put("--- GRfNIRSClient --- disconnected from the server (IP: " + SERVER_IP + ", PORT: " + SERVER_PORT + ")");
    }catch(IOException e){
      e.printStackTrace();
    }
  }


  public void run(){
int assertion = 0;
    if(this.connect()){
      while(!this.isDone){
        try{
          String message = this.in.readLine();
//System.out.println("GRfNIRSClient received: " + message);
          if(message != null){
assertion = 0;
            this.onMessageListener.onPredictionMessageReceived(message);
          }else{
System.out.println("ERROR: Invalid data received from the data relay server: " + message);
if(assertion++ > 10){
  System.out.println("ERROR: Probably the connection lost");
  System.out.println("Client is closing the connection...");
  this.disconnect();
}
          }
        }catch(IOException e){
          e.printStackTrace();
System.out.println("ERROR: Failed to receive data from the data relay server");
if(assertion++ > 10){
  System.out.println("ERROR: Probably the connection lost");
  System.out.println("Client is closing the connection...");
  this.disconnect();
}
        }
      }
      this.messageBoard.put("--- GRfNIRSClient --- done");
    }else{
      this.disconnect();
    }
  }
}
