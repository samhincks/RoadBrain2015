
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**Stores timestamp, unity information, and fnirs information in a common locaiton,
 * then prints it
 * 
 * Specifically, each time we get a new unity data piece, we write to a file that information
 * as well as the current workload. So when we get a workload prediction, we alternate current
 * state.
 * @author shincks
 */
public class SamSessionInformation {
    
    private String state ="unknown";
    int numWritten =0;
    private BufferedWriter bw;
    private String filename;
    private int linesWritten =0;
    public SamSessionInformation(String filename) {
        this.filename = filename;
        try {
            this.filename = filename;
            System.out.println(this.filename);
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)));
        }
        catch(Exception e) {e.printStackTrace();}
    }
    public void setState(String state) {
        this.state=state;
    }
    
    public void write() {
        close();
        numWritten++;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename +numWritten)));
        } catch (Exception e) {
            e.printStackTrace();
        }
   
    }
    
    //.. write to file as time,x,y,workload. unity is a;220.2;181.24
    public void addLine(String unity) {
        try{
            if (linesWritten == 0) bw.write("time,x,y,workload\n");
            String [] vals = unity.split(";");
            String x = vals[1];
            String y = vals[2];
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            String time = dateFormat.format(date); //2014/08/06 15:59:48
            bw.write(time + "," +x + "," +y+","+ state+"\n");
            linesWritten++;
        }
        catch(Exception e) {e.printStackTrace();}
    }
    public void close() {
        try {
            System.out.println("closing");
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
