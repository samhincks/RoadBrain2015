import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;


public class GRShellCommandExecutor extends SwingWorker<Process, Void>{

  private String[] command;
  private GRMessageBoard messageBoard;

  public GRShellCommandExecutor(String[] command, GRMessageBoard messageBoard){
    this.command = command;
    this.messageBoard = messageBoard;
  }

  @Override
  protected Process doInBackground() throws Exception{
    return Runtime.getRuntime().exec(this.command);
  }
 
  @Override
  protected void done(){
    try{
      Process process = this.get();
      if(process != null)
        this.messageBoard.put("GRShellCommandExecutor: Succeeded to issue: " + this.getCommandToString());
      else
        this.messageBoard.put("GRShellCommandExecutor: Failed to issue: " + this.getCommandToString());
    }catch(InterruptedException e){
      e.printStackTrace();
    }catch(ExecutionException e){
      e.printStackTrace();
    }
  }

  private String getCommandToString(){
    String commandString = "";
    for(int i = 0; i < this.command.length; i++)
      commandString += this.command[i] + " ";
    return commandString;
  }
}
