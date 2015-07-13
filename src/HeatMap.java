
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author shincks
 */
public class HeatMap extends TimerTask{
    private GRHouseInformationBroadcaster bc;
    public HeatMap(GRHouseInformationBroadcaster bc) {
        this.bc = bc;
    }  
    @Override
    public void run() {
        this.bc.broadcast("picture");
    }
    
    public static void main(String[] args) {
       
    }
    
}
