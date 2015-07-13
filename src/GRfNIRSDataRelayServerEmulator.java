import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DecimalFormat;


//This class emulates the Python data relay server that relays MATLAB data to applications.
public class GRfNIRSDataRelayServerEmulator implements Runnable{
 
  private static final int PORT = 50001;

  private int count;
  private DecimalFormat dFormat;

  public GRfNIRSDataRelayServerEmulator(){
    this.count = 0;
    this.dFormat = new DecimalFormat("0.00");
  }

  @Override
  public void run(){
    try{
        System.out.println("--- GRfNIRSDataRelayServer --- IP: " + InetAddress.getLocalHost().getHostAddress() + ", Port: " + PORT);
    }catch(UnknownHostException e){
        e.printStackTrace();
    }
    
    try {
        System.out.println("A");
        ServerSocket serverSocket = new ServerSocket(PORT);
        Socket clientSocket = serverSocket.accept();
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);
        System.out.println("B");

        serverSocket.setReuseAddress(true);
        System.out.println("--- GRfNIRSDataRelayServer --- Client (IP: " + clientSocket.getInetAddress().getHostAddress() + ") connected.");
        
        //.. while there is no error
        while(!out.checkError()){
            System.out.println("C");
            String message = this.makeFakeData();
            out.println(message);
            System.out.println("making + " + message);
            System.out.println("--- GRfNIRSDataRelayServer --- send: " + message);
            Thread.sleep(500); //suppose data is sent twice per second
        }
        System.out.println("--- GRfNIRSDataRelayServer --- done");
    }catch(IOException e){
        e.printStackTrace();
        System.out.println("ERROR: Failed to create server socket");
        System.out.println("System closing...");
        System.exit(-1);
    }catch(InterruptedException e){
      e.printStackTrace();
    }
  }

  private String makeFakeData(){
    this.count += 2; //Speed: increasing 2[degree] per 500[msec] = 4[degree/sec]
    double signal = Math.abs(Math.sin(Math.toRadians(this.count)));
    if(count >= 360)
      count = 0;
    String classification;
    if(signal >= 0.5)
      classification = GRPrediction.CLASSIFICATION_BRANCHING;
    else
      classification = GRPrediction.CLASSIFICATION_NOBRANCH;
    String high = this.dFormat.format(signal * 100);
    String low = this.dFormat.format(100 - (signal * 100));
    return classification + ";" + high + ";" + low;
  }


  public static void main(String[] args){
    new Thread(new GRfNIRSDataRelayServerEmulator()).start();
  }
}
