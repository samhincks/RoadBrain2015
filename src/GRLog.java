
public class GRLog{

  public static String TYPE_CREATION_TIME                 = "CREATION TIME";
  public static String TYPE_INFORMATION_RECEIVED          = "INFORMATION RECEIVED";
  public static String TYPE_INFORMATION_SENT_TO_GLASS     = "INFORMATION SENT TO GLASS";
  public static String TYPE_INFORMATION_NOT_SENT_TO_GLASS = "INFORMATION NOT SENT TO GLASS";
  public static String TYPE_INFORMATION_ERROR             = "INFORMATION ERROR";

  private String type;
  private long timeInMillisec;
  private String message;

  public GRLog(String type, long timeInMillisec, String message){
    this.type = type; 
    this.timeInMillisec = timeInMillisec;
    this.message = message;
  }

  @Override
  public String toString(){
    return "[" + this.type + "]," + String.valueOf(this.timeInMillisec) + "," + this.message;
  }
}
