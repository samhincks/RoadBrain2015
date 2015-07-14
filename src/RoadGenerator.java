import java.util.Hashtable;

public class RoadGenerator {
	
	public static Hashtable<String, Road> generate() {
		Hashtable ht = new Hashtable();
		Road roadA = new Road("a"); 
		Direction direction = new Direction(Direction.Turn.Left, Direction.Turn.Left, 1532, 6059);
		roadA.directions.add(direction); 
                
                //... road a, the double
                direction = new Direction(Direction.Turn.RightxRight, Direction.Turn.RightxRight, 865, 5987.0f);
                roadA.directions.add(direction);
		ht.put("a", roadA); 
                
                //.. road b, the triple
                Road roadA2 = new Road("b"); 
		direction = new Direction(Direction.Turn.Left, Direction.Turn.Left, 1560, 13260.0f);
		roadA2.directions.add(direction); 
		
                direction = new Direction(Direction.Turn.RightxRightxLeft, Direction.Turn.RightxRightxLeft, 680, 13029.0f);
		roadA2.directions.add(direction); 

		ht.put("b", roadA2); 
                                
                Road roadC = new Road("c"); 
                direction = new Direction(Direction.Turn.Right, Direction.Turn.Right, 1773, 19722.0f);
               roadC.directions.add(direction);
               
                direction = new Direction(Direction.Turn.LeftxRightxRightxLeft, Direction.Turn.LeftxRightxRightxLeft, 688, 20000.0f);
                roadC.directions.add(direction);

		ht.put("c", roadC); 
                
                return ht;
	}

 
}