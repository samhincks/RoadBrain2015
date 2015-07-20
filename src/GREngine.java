import java.util.Hashtable;
import java.util.Timer;
import java.util.Vector;


public class GREngine implements GRfNIRSClient.OnMessageListener, GRUnityServer.OnMessageListener{

  private GRMessageBoard brainMessageBoard;
  private GRMessageBoard unityMessageBoard;
  private GRMessageBoard glassMessageBoard;

  private GRPredictionManager predictionManager;
  private GRfNIRSClient fNIRSClient;
  private GRUnityServer unityServer;
  private NeuracleLabelingTask taskClient;
  private NeuracleLabelingTask eventClient;


  private GRHouseInformationBroadcaster houseInformationBroadcaster;
  private GRLogger logger;
  private PositionLogger psLogger;
  public Timer tm;
  
  //.. sam's stuff
  private Hashtable<String, Road> roads; //.. hardcoded roads which determine, based on position, what turn is sent
  public String condition;
  public boolean breakDown = false;
  public SamSessionInformation session;
  

  public GREngine(GRMessageBoard brainMessageBoard, GRMessageBoard unityMessageBoard, GRMessageBoard glassMessageBoard){
    this.brainMessageBoard = brainMessageBoard;
    this.unityMessageBoard = unityMessageBoard;
    this.glassMessageBoard = glassMessageBoard;
    this.predictionManager = new GRPredictionManager();
    
    //.. ways of sending and receiving messages
    this.fNIRSClient = new GRfNIRSClient(this, this.brainMessageBoard);
    this.unityServer = new GRUnityServer(this, this.unityMessageBoard);
    this.eventClient = new NeuracleLabelingTask(1444);
    this.taskClient = new NeuracleLabelingTask(1327);

    
    this.houseInformationBroadcaster = new GRHouseInformationBroadcaster(this.glassMessageBoard);
    this.logger = new GRLogger();
    this.psLogger = new PositionLogger();
    roads = RoadGenerator.generate();
  
    //.. record data
    session = new SamSessionInformation("sam-testoutput");
  }

  public void start(){
    new Thread(this.fNIRSClient).start();
    new Thread(this.unityServer).start();     
    new Thread(this.eventClient).start();
    new Thread(this.taskClient).start();

    
    //.. if this is heatmap, then take picture every 5 seconds
    if (condition.equals("picture")) {
       startPictureTimer();
    }
  }
  
  public void sendToEvent(String message) {
      this.eventClient.setCurrentCondition((message));
  }
   public void sendToTask(String message) {
      this.taskClient.setCurrentCondition((message));
  } 
  
  public boolean closeUnity() {
      return this.unityServer.close();
  }
  public void openUnity() {
    new Thread(this.unityServer).start();
  }
  
  public void startPictureTimer() {
      tm = new Timer();
      tm.schedule(new HeatMap(houseInformationBroadcaster), 0, 3000);
  }
  
  public void cancelPictureTimer() {
      if (tm != null) {
          tm.cancel();
          tm.purge();
      }
  }
  public void stop(){
    this.fNIRSClient.disconnect();
    session.close();
  }
  
  /**Write session data*/
  public void write() {
      session.write();
  }

  @Override
  public void onPredictionMessageReceived(String message){
    if(message != null){
        // GRPrediction prediction = new GRPrediction(classification, Double.parseDouble(data[1]), Double.parseDouble(data[2]));
        //this.predictionManager.addPrediction(prediction);
        this.brainMessageBoard.put("A prediction added: " + message);
        session.setState(message);
    }
  }

  @Override
  public void onPositionReceived(String message){
      //.. broadcast message to Phylter
    if(message != null){
       //.. log data and display to Phylter
       double averageWorkload = this.predictionManager.getAverageOfBranchPredictions();
       psLogger.addMessage(message, averageWorkload);
       String[] data = message.split(";");

       //.. verify there are exactly lon-delimated values
       if(data.length == 3){
            String roadId = data[0];
            String xPos = data[1];
            String zPos = data[2];
            if (breakDown) roadId = roadId +2;
            Road road = roads.get(roadId);
            this.unityMessageBoard.put(message);

            //.. there is indeed a road with received id
            if (road != null){
                Direction direction = road.getDirection(xPos, zPos);
                if (direction != null) {
                    //.. does this position mean destination has been reached?
                    if (direction.easyTurn == Direction.Turn.End) {
                        psLogger.writeThenClose();
                    }
                    else { 
                        Direction.Turn turn;
                        this.unityMessageBoard.put(" , " + direction.hardTurn);

                        //.. if this is adaptive mode, decide whether or not to give directions based on workload
                        if(condition.equals("adaptive")) {
                            if (averageWorkload < 50)  //.. this may well be the other way around!
                                turn = direction.hardTurn;
                            else
                                turn = direction.easyTurn;
                        }

                        else if(condition.equals("easy")) 
                            turn = direction.easyTurn;

                        else //if (condition.equals("hard")) 
                            turn = direction.hardTurn;

                        if (turn != null) {
                            //.. broadcast the turn to Glass and make a not of what was sent
                            //..  3 parameters completely irrelevant, but for now we shouldnt mess with magic number 4
                            this.houseInformationBroadcaster.broadcast(condition, xPos, zPos, turn.name());
                            this.unityMessageBoard.put("Attempted to send: " + message + ", condition: " 
                                    + roadId + ", averageOfBranchPredictions: " + averageWorkload);
                            
                            //.. and also send something to Neuracle, which is still collecting brain data we should analyze
                            String []numTurns = turn.name().split("x");
                            this.sendToTask("T"+numTurns.length);
                            
                        }
                        else {
                            //this.unityMessageBoard.put("This direction ahs already been delivered");
                        }
                    }
                }
                else {
                    //this.unityMessageBoard.put(xPos+ " , " + zPos);
                }
            }
              
             //.. An unrecognized road
            else {
                this.unityMessageBoard.put(roadId + " has not been hardcoded into RoadGenerater");
                System.err.println(roadId + " has not been hardcoded into RoadGenerater");
            }
      }
      if (data.length ==1) {
            //.. broadcast the turn to Glass and make a note of what was sent
            //..  3 parameters completely irrelevant, but for now we shouldnt mess with magic number 4
            this.houseInformationBroadcaster.broadcast(data[0]);
            this.unityMessageBoard.put("Attempted to send: " + data[0]);
      }
      
      //.. save the data
      session.addLine(message);
      
    }
    
    else{
    //  this.unityMessageBoard.put("ERROR: Unrecognizable data recieved: null");
    }
  }

  
}
