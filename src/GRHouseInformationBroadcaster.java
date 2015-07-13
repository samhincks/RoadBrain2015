
public class GRHouseInformationBroadcaster{

  private static final String SHELL_SCRIPT_FILE_PATH = "/Users/shincks/Box Sync/Development/RoadBrain2015/send_house_information.sh";

  private GRMessageBoard messageBoard;

  public GRHouseInformationBroadcaster(GRMessageBoard messageBoard){
    this.messageBoard = messageBoard;
    this.messageBoard.put("--- GRHouseInformationBroadcaster --- Ready");
  }

  public void broadcast(String averageOfBranchPredictions, String roadId, String xPos, String turn){
     String [] values = turn.split("x");   
     
     String [] command = null;
     if (values.length >=1) 
          command = new String[]{SHELL_SCRIPT_FILE_PATH, values[0]};
     if (values.length >=2) 
          command = new String[]{SHELL_SCRIPT_FILE_PATH, values[0],values[1]};
     if (values.length >=3) 
          command = new String[]{SHELL_SCRIPT_FILE_PATH, values[0],values[1],values[2]};
     if (values.length >=4) 
          command = new String[]{SHELL_SCRIPT_FILE_PATH, values[0],values[1],values[2],values[3]};
     
     new GRShellCommandExecutor(command, this.messageBoard).execute();
  }
  
  public void broadcast(String message) {
      String[] command = {SHELL_SCRIPT_FILE_PATH, message};
      new GRShellCommandExecutor(command, this.messageBoard).execute();
  }

}
