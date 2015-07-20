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
                Road roadB = new Road("b"); 
		direction = new Direction(Direction.Turn.Left, Direction.Turn.Left, 1560, 13260.0f);
		roadB.directions.add(direction); 
		
                direction = new Direction(Direction.Turn.RightxRightxLeft, Direction.Turn.RightxRightxLeft, 680, 13029.0f);
		roadB.directions.add(direction); 

		ht.put("b", roadB); 
                                
                Road roadC = new Road("c"); 
                direction = new Direction(Direction.Turn.Right, Direction.Turn.Right, 1773, 19722.0f);
                roadC.directions.add(direction);
               
                direction = new Direction(Direction.Turn.LeftxRightxRightxLeft, Direction.Turn.LeftxRightxRightxLeft, 688, 20000.0f);
                roadC.directions.add(direction);
		ht.put("c", roadC); 
                
                Road roadH = new Road("h");
                direction = new Direction(Direction.Turn.Left, Direction.Turn.Left,9500, 13300);  
                roadH.directions.add(direction);

                direction = new Direction(Direction.Turn.RightxRightxStraight , Direction.Turn.RightxRightxStraight , 8649, 13004.0f);
                roadH.directions.add(direction);
                ht.put("h", roadH);

                
                Road roadD = new Road("d");
                direction = new Direction(Direction.Turn.Right, Direction.Turn.Right, 8091, 19317.0f);
                roadD.directions.add(direction);

                direction = new Direction(Direction.Turn.LeftxRightxRightxLeft , Direction.Turn.LeftxRightxRightxLeft , 7612, 19486);
                roadD.directions.add(direction);
                ht.put("d", roadD);

                
                Road roadE = new Road("e");
                direction = new Direction(Direction.Turn.Right, Direction.Turn.Right, -7861, 19764.0f);
                roadE.directions.add(direction);

                direction = new Direction(Direction.Turn.LeftxRightxRightxLeft, Direction.Turn.LeftxRightxRightxLeft, -8795, 20048);
                roadE.directions.add(direction);
                ht.put("e", roadE);

                Road roadG = new Road("g");
                direction = new Direction(Direction.Turn.Right, Direction.Turn.Right, -7928, 12795.0f);
                roadG.directions.add(direction);

                direction = new Direction(Direction.Turn.LeftxLeftxRight, Direction.Turn.LeftxLeftxRight, -8447, 12919);
                roadG.directions.add(direction);
                ht.put("g", roadG);
                return ht;
	}

 
}