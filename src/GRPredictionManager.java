import java.util.Iterator;
import java.util.Vector;


public class GRPredictionManager{

  private static final int QUEUE_SIZE = 10; //expecting that data comes twice/[sec] from a server

  private int queueSize;
  private Vector<GRPrediction> predictionQueue;

  public GRPredictionManager(){
    this.queueSize = QUEUE_SIZE;
    this.predictionQueue = new Vector<GRPrediction>();
  } 

  public void addPrediction(GRPrediction prediction){
    synchronized(this.predictionQueue){
      if(this.predictionQueue.size() >= this.queueSize)
        this.predictionQueue.remove(0);
      this.predictionQueue.add(prediction);
    }
  }

  public double getAverageOfNonbranchPredictions(){
    synchronized(this.predictionQueue){
      Iterator<GRPrediction> iterator = this.predictionQueue.iterator();
      double sum = 0.0;
      while(iterator.hasNext())
        sum += iterator.next().getNonbranch();
      return sum / this.predictionQueue.size();
    }
  }

  public double getAverageOfBranchPredictions(){
    synchronized(this.predictionQueue){
      Iterator<GRPrediction> iterator = this.predictionQueue.iterator();
      double sum = 0.0;
      while(iterator.hasNext())
        sum += iterator.next().getBranch();
      return sum / this.predictionQueue.size();
    }
  }

  //for logging purpose
  public int getQueueSize(){
    return this.queueSize;
  }

  //for test
  public static void main(String[] args){
    double v[] = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    int index = 0;
    String classification = GRPrediction.CLASSIFICATION_BRANCHING;
    GRPredictionManager predictionManager = new GRPredictionManager();
    for(double i = 50.0; i <= 100.0; i += 0.5){
      predictionManager.addPrediction(new GRPrediction(classification, 100.0 - i, i));
      int actualQueueSize = predictionManager.predictionQueue.size();
      double aveOfBranch = predictionManager.getAverageOfBranchPredictions();
      System.out.print("i:"+i+" size:"+actualQueueSize+" AveOfBrabch:"+aveOfBranch);
      v[index] = i;
      index = (index + 1) % 10;
      double sum = 0.0;
      for(int j = 0; j < v.length; j++)
        sum += v[j];
      double ave = sum / 10;
      String check = (ave == aveOfBranch ? "OK" : "NG");
      if(actualQueueSize >= 10)
        System.out.println(" expected:"+ave+" check:"+check);
      else
        System.out.println();
    }
  }
}
