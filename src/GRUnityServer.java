import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class GRUnityServer implements Runnable{

  public interface OnMessageListener{
    public void onPositionReceived(String message);
  }
 
  private static final int PORT = 50002;

  private GRUnityServer.OnMessageListener onMessageListener;
  private GRMessageBoard messageBoard;

  public GRUnityServer(GRUnityServer.OnMessageListener onMessageListener, GRMessageBoard messageBoard){
    this.onMessageListener = onMessageListener;
    this.messageBoard = messageBoard;
  }

  @Override
  public void run(){
    try{
      this.messageBoard.put("--- GRUnityServer --- IP: " + InetAddress.getLocalHost().getHostAddress() + ", Port: " + PORT);
    }catch(UnknownHostException e){
      e.printStackTrace();
    }
    try(
      ServerSocket serverSocket = new ServerSocket(PORT);
      Socket clientSocket = serverSocket.accept();
      BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    ){
      serverSocket.setReuseAddress(true);
      this.messageBoard.put("--- GRUnityServer --- Client (IP: " + clientSocket.getInetAddress().getHostAddress() + ") connected.");
      String message;
      while((message = in.readLine()) != null){
//System.out.println("GRUnityServer received: " + message);
        this.onMessageListener.onPositionReceived(message);
      }
      this.messageBoard.put("--- GRUnityServer --- done");
    }catch(IOException e){
      e.printStackTrace();
System.out.println("ERROR: Failed to create server socket");
System.out.println("System closing...");
System.exit(-1);
    }
  }
}
