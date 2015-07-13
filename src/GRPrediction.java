public class GRPrediction{

  public static final String CLASSIFICATION_BRANCHING = "e"; //branching
  public static final String CLASSIFICATION_NOBRANCH  = "h"; //not branching

  private String classification;
  private double branching;
  private double nonbranch;
 
  public GRPrediction(String classification, double value1, double value2){
    this.classification = classification;
    if(classification == CLASSIFICATION_BRANCHING){
      if(value1 >= value2){
        this.branching = value1;
        this.nonbranch = value2;
      }else{
        this.branching = value2;
        this.nonbranch = value1;
      }
    }else if(classification == CLASSIFICATION_NOBRANCH){
      if(value1 < value2){
        this.branching = value1;
        this.nonbranch = value2;
      }else{
        this.branching = value2;
        this.nonbranch = value1;
      }
    }
  }

  public double getBranch(){
    return this.branching;
  }
  public double getNonbranch(){
    return this.nonbranch;
  }
}
